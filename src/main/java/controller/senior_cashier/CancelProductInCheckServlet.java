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
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String quantity = request.getParameter("quantity");

        boolean result;
        try {
            if (productId != null) {
                result = checkService.cancelCheckElementById(Integer.parseInt(productId), Integer.parseInt(quantity));
            }
            else result = checkService.cancelCheckElementByName(productName, Integer.parseInt(quantity));
        }
        catch (NullPointerException | NumberFormatException ignored) {
            result = false;
        }

        String url = "/senior-cashier/cancel-product-in-check";
        if (!result)
            url += "?error=Cannot cancel product from check. Please try again.";
        else url += "?success=Product was canceled from check";

        response.sendRedirect(url);
    }
}
