package controller.senior_cashier;

import entity.CheckElement;
import service_impl.CheckServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@WebServlet("/senior-cashier/cancel-product-in-check")
public class CancelProductInCheckServlet extends HttpServlet {
    private final CheckServiceImpl checkServiceImpl = CheckServiceImpl.getInstance();

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

        String productParam = request.getParameter("product");
        String quantityParam = request.getParameter("quantity");

        CheckElement checkElement;
        try {
            checkElement = checkServiceImpl.getCheckElementByProductId(Integer.parseInt(productParam));
        }
        catch (NumberFormatException e) {
            checkElement = checkServiceImpl.getCheckElementByProductName(productParam);
        }

        String url = "/senior-cashier/cancel-product-in-check";
        if (checkElement == null)
            url += String.format("?error=noSuchProduct&product=%s&quantity=%s", productParam, quantityParam);
        else if (!checkServiceImpl.cancelCheckElement(checkElement,  new BigDecimal(quantityParam).setScale(3, RoundingMode.UP).doubleValue()))
            url += String.format("?error=overExceededQuantity&product=%s&quantity=%s", productParam, quantityParam);
        else url += "?success=true";

//        String url = "/senior-cashier/cancel-product-in-check";
//        if (!result)
//            url += "?error=true";
//        else url += "?success=true";

        response.sendRedirect(url);
    }
}
