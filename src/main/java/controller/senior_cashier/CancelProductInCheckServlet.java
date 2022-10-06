package controller.senior_cashier;

import dao_impl.ProductRepository;
import entity.CheckElement;
import entity.Product;
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
//        String product = request.getParameter("product");
//        String quantity = request.getParameter("quantity");
//
//        boolean result;
//        try {
//            result = checkService.cancelCheckElementById(Integer.parseInt(product), Integer.parseInt(quantity));
//        }
//        catch (NumberFormatException e1) {
//            try {
//                result = checkService.cancelCheckElementByName(product, Integer.parseInt(quantity));
//            }
//            catch (NumberFormatException e2) {
//                result = false;
//            }
//        }

        CheckElement checkElement;
        try {
            checkElement = checkService.getCheckElementByProductId(Integer.parseInt(request.getParameter("product")));
        }
        catch (NumberFormatException e) {
            checkElement = checkService.getCheckElementByProductName(request.getParameter("product"));
        }

        String url = "/senior-cashier/cancel-product-in-check";
        if (checkElement == null)
            url += "?error=";
        else if (!checkService.cancelCheckElement(checkElement, Integer.parseInt(request.getParameter("quantity"))))
            url += "?error=";
        else url += "?success=";

//        String url = "/senior-cashier/cancel-product-in-check";
//        if (!result)
//            url += "?error=true";
//        else url += "?success=true";

        response.sendRedirect(url);
    }
}
