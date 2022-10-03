package controller.cashier;

import entity.CheckElement;
import service.CheckService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * Class is designed to process user's request of adding/updating product to check
 */

@WebServlet("/cashier/add-product-to-check")
public class AddProductToCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckService.getInstance();

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

        List<CheckElement> check = checkService.getPerPage(page, total, req.getParameter("sortBy"));
        int nOfPages = (checkService.getNumberOfRows() + total - 1) / total;

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
//        String productId = request.getParameter("productId");
//        String productName = request.getParameter("productName");
//        String quantity = request.getParameter("quantity");
//
//        boolean result;
//        try {
//            if (productId != null) {
//                result = checkService.addToCheckByProductId(Integer.parseInt(productId), Integer.parseInt(quantity));
//            }
//            else result = checkService.addToCheckByProductName(productName, Integer.parseInt(quantity));
//        }
//        catch (NullPointerException | NumberFormatException ignored) {
//            result = false;
//        }

        String product = request.getParameter("product");
        String quantity = request.getParameter("quantity");
        boolean result;
        try {
            result = checkService.addToCheckByProductId(Integer.parseInt(product), Integer.parseInt(quantity));
        }
        catch (NumberFormatException e1) {
            try {
                result = checkService.addToCheckByProductName(product, Integer.parseInt(quantity));
            }
            catch (NumberFormatException e2) {
                result = false;
            }
        }


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
        String url = request.getRequestURL().toString();
        if (!result)
            url += "?error=Cannot add product to check. Please try again";
        else url += "?success=Product was successfully added to check";
        if (request.getParameter("page") != null)
            url += "&page=" + request.getParameter("page");
//        if (request.getParameter("total") != null)
//            url += "&total=" + request.getParameter("total");

        response.sendRedirect(url);
    }
}
