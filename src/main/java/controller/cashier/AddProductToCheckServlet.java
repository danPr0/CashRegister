package controller.cashier;

import dto.CheckDTO;
import entity.Product;
import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
import service.CheckService;
import service.ProductService;
import service_impl.CheckServiceImpl;
import service_impl.ProductServiceImpl;
import util.enums.Language;
import util.table.CheckColumnName;
import util.table.TableService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
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
        int page = 1;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

        int total = TableService.getTotalPerPage(req, "total", "checkTotalPerPage");
        CheckColumnName sortBy = CheckColumnName.valueOf(TableService.getTableSortParam(req, "sortBy", "checkSortBy"));
        String orderBy = TableService.getTableSortParam(req, "orderBy", "checkOrderBy");

        List<CheckDTO> check = checkService.convertToDTO(checkService.getPerPage(page, total,
                sortBy, orderBy.equals("asc")), Language.getLanguage(req));
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
    @SneakyThrows
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

        URIBuilder uriBuilder = new URIBuilder("/cashier/add-product-to-check", UTF_8);

        if (product == null) {
            uriBuilder.addParameter("error", getMessageByLang("error.cashier.noSuchProduct", lang));
            uriBuilder.addParameter("product", productParam);
            uriBuilder.addParameter("quantity", quantityParam);
        }
        else if (!checkService.addToCheck(product,  new BigDecimal(quantityParam).setScale(3, RoundingMode.UP).doubleValue())) {
            uriBuilder.addParameter("error", getMessageByLang("error.cashier.overExceededQuantity", lang));
            uriBuilder.addParameter("product", productParam);
            uriBuilder.addParameter("quantity", quantityParam);
        }
        else uriBuilder.addParameter("success", "true");

        resp.sendRedirect(uriBuilder.build().toString());
    }
}
