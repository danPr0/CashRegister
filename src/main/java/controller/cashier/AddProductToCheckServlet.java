package controller.cashier;

import entity.CheckElement;
import service.CheckService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cashier/add-product-to-check")
public class AddProductToCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        }
        catch (NumberFormatException e) {
            page = 1;
        }
        int total = 1;

        List<CheckElement> check = checkService.getPerPage(page, total);
        int nOfPages = (checkService.getNumberOfRows() + total - 1) / total;

        if (!check.isEmpty()) {
            req.setAttribute("check", check);
            req.setAttribute("nOfPages", nOfPages);
        }
        req.getRequestDispatcher("/view/cashier/addProductToCheck.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String quantity = request.getParameter("quantity");

        boolean result;
        try {
            if (productId != null) {
                result = checkService.specifyCheckByProductId(Integer.parseInt(productId), Integer.parseInt(quantity));
            }
            else result = checkService.specifyCheckByProductName(productName, Integer.parseInt(quantity));
        }
        catch (NullPointerException | NumberFormatException ignored) {
            result = false;
        }

        String url = "/cashier/add-product-to-check";
        if (!result)
            url += "?error=Cannot add product to check. Please try again";
        else url += "?success=Product was successfully added to check";

        response.sendRedirect(url);
    }
}
