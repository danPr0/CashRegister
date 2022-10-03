package controller.senior_cashier;

import service.CheckService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/senior-cashier/cancel-product-in-check")
public class CancelProductInCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/senior-cashier/cancelProductInCheck.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String product = request.getParameter("product");
        String quantity = request.getParameter("quantity");

        boolean result;
        try {
            result = checkService.cancelCheckElementById(Integer.parseInt(product), Integer.parseInt(quantity));
        }
        catch (NumberFormatException e1) {
            try {
                result = checkService.cancelCheckElementByName(product, Integer.parseInt(quantity));
            }
            catch (NumberFormatException e2) {
                result = false;
            }
        }

        String url = "/senior-cashier/cancel-product-in-check";
        if (!result)
            url += "?error=Cannot cancel product from check. Please try again.";
        else url += "?success=Product was canceled from check";

        response.sendRedirect(url);
    }
}
