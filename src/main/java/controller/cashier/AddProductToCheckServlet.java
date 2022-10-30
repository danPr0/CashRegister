package controller.cashier;

import dto.CheckDTO;
import entity.Product;
import service.CheckService;
import service.ProductService;
import service_impl.CheckServiceImpl;
import service_impl.ProductServiceImpl;
import util.enums.Language;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static util.GetProperties.getMessageByLang;

/**
 *  Adding/updating product in check
 */
@WebServlet("/cashier/add-product-to-check")
public class AddProductToCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckServiceImpl.getInstance();
    private final ProductService productService = ProductServiceImpl.getInstance();

    /**
     * Implement check pagination
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        else page = 1;

        int total;
        try {
            total = Integer.parseInt(req.getParameter("total"));
            req.getSession().setAttribute("checkTotalPerPage", total);
        }
        catch (NumberFormatException e) {
            total = Integer.parseInt(req.getSession().getAttribute("checkTotalPerPage").toString());
        }

        String sortBy;
        if (req.getParameter("sortBy") != null) {
            sortBy = req.getParameter("sortBy");
            req.getSession().setAttribute("checkSortBy", sortBy);
        }
        else sortBy = req.getSession().getAttribute("checkSortBy").toString();

        String orderBy;
        if (req.getParameter("orderBy") != null) {
            orderBy = req.getParameter("orderBy");
            req.getSession().setAttribute("checkOrderBy", orderBy);
        }
        else orderBy = req.getSession().getAttribute("checkOrderBy").toString();

        List<CheckDTO> check = checkService.convertToDTO(checkService.getPerPage(page, total,
                wrapSortParam(sortBy), orderBy.equals("asc")), Language.getLanguage(req));
        int nOfPages = (checkService.getNumberOfRows() + total - 1) / total;

        if (!check.isEmpty()) {
            req.setAttribute("check", check);
        }
        req.setAttribute("nOfPages", nOfPages);
        req.getRequestDispatcher("/view/cashier/addProductToCheck.jsp").forward(req, resp);
    }

    /**
     * Receive product's details and add it to the check
     * There can be 2 errors : "No such product" and "Over exceeded quantity"
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productParam = req.getParameter("product");
        String quantityParam = req.getParameter("quantity");
        Language lang = Language.getLanguage(req);

        Product product;
        try {
            product = productService.getProduct(Integer.parseInt(productParam));
        }
        catch (NumberFormatException ignored) {
            product = productService.getProduct(productParam, lang);
        }

        String url = "/cashier/add-product-to-check";

        if (product == null)
            url += String.format("?error=%s&product=%s&quantity=%s",
                    encode(getMessageByLang("error.cashier.noSuchProduct", lang), UTF_8),
                    encode(productParam, UTF_8), quantityParam);
        else if (!checkService.addToCheck(product,  new BigDecimal(quantityParam).setScale(3, RoundingMode.UP).doubleValue()))
            url += String.format("?error=%S&product=%s&quantity=%s",
                    encode(getMessageByLang("error.cashier.overExceededQuantity", lang), UTF_8),
                    encode(productParam, UTF_8), quantityParam);
        else url += "?success=true";

        resp.sendRedirect(url);
    }

    private String wrapSortParam(String sortParam) {
        String result;
        if (Objects.equals(sortParam, "productId"))
            result = "product";
        else if (Objects.equals(sortParam, "quantity"))
            result = "quantity";
        else result = "checkId";

        return result;
    }
}
