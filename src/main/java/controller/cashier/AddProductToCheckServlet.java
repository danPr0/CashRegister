package controller.cashier;

import dao_impl.ProductRepository;
import dto.CheckDTO;
import entity.Product;
import service_impl.CheckServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Class is designed to process user's request of adding/updating product to check
 */

@WebServlet("/cashier/add-product-to-check")
public class AddProductToCheckServlet extends HttpServlet {
    private final CheckServiceImpl checkServiceImpl = CheckServiceImpl.getInstance();
    private final ProductRepository productRepository = ProductRepository.getInstance();

    /**
     * Method implements check pagination to display table to user
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page;
        int total;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        }
        catch (NumberFormatException e) {
            page = 1;
        }
        try {
            total = Integer.parseInt(req.getServletContext().getAttribute("checkTotalPerPage").toString());
        }
        catch (NumberFormatException e) {
            total = 10;
        }

        List<CheckDTO> check = checkServiceImpl.convertToDTO(checkServiceImpl.getPerPage(page, total, req.getParameter("sortBy")));
        int nOfPages = (checkServiceImpl.getNumberOfRows() + total - 1) / total;

        if (!check.isEmpty()) {
            req.setAttribute("check", check);
        }
        req.setAttribute("nOfPages", nOfPages);
        req.setAttribute("sort", req.getParameter("sortBy"));
        req.getRequestDispatcher("/view/cashier/addProductToCheck.jsp").forward(req, resp);
    }

    /**
     * Method gets product's details and adds it to the check
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productParam = request.getParameter("product");
        String quantityParam = request.getParameter("quantity");

        Product product;
        try {
            product = productRepository.getProductById(Integer.parseInt(productParam));
        }
        catch (NumberFormatException ignored) {
            product = productRepository.getProductByName(productParam);
        }

        String url = "/cashier/add-product-to-check";

        if (product == null)
            url += String.format("?error=noSuchProduct&product=%s&quantity=%s", productParam, quantityParam);
//        else if (product.getMeasure().equals(ProductMeasure.apiece)) {
//            try {
//                Integer.parseInt(quantityParam);
//            }
//            catch (NumberFormatException e) {
//                url += String.format("?error=overExceededQuantity&product=%s&quantity=%s", productParam, quantityParam);
//            }
//        }
        else if (!checkServiceImpl.addToCheck(product,  new BigDecimal(quantityParam).setScale(3, RoundingMode.UP).doubleValue()))
            url += String.format("?error=overExceededQuantity&product=%s&quantity=%s", productParam, quantityParam);
        else url += "?success=true";

        response.sendRedirect(url);



//        String product = request.getParameter("product");
//        String quantity = request.getParameter("quantity");
//        boolean result;
//        try {
//            result = checkService.addToCheckByProductId(Integer.parseInt(product), Integer.parseInt(quantity));
//        }
//        catch (NumberFormatException e1) {
//            try {
//                result = checkService.addToCheckByProductName(product, Integer.parseInt(quantity));
//            }
//            catch (NumberFormatException e2) {
//                result = false;
//            }
//        }


//        try {
//            if (productId != null) {
//                result = checkService.addToCheckByProductId(Integer.parseInt(productId), Integer.parseInt(quantity));
//            }
//            else result = checkService.addToCheckByProductName(productName, Integer.parseInt(quantity));
//        }
//        catch (NullPointerException | NumberFormatException ignored) {
//            result = false;
//        }

//        String url = "/cashier/add-product-to-check?page=1";
//        String url = request.getRequestURL().toString();
//        if (!result)
//            url += "?error=Cannot add product to check. Please try again";
//        else url += "?success=Product was successfully added to check";
//        if (request.getParameter("page") != null)
//            url += "&page=" + request.getParameter("page");
//        if (request.getParameter("total") != null)
//            url += "&total=" + request.getParameter("total");

//        response.sendRedirect(url);
    }
}
