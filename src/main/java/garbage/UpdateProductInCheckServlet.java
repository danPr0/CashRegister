package garbage;

import entity.CheckEntity;
import service_impl.CheckServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cashier/update-product-in-check")
public class UpdateProductInCheckServlet extends HttpServlet {
    private final CheckServiceImpl checkServiceImpl = CheckServiceImpl.getInstance();

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<CheckEntity> check = checkServiceImpl.getAll();
//        if (!check.isEmpty())
//            req.setAttribute("check", check);
//        req.getRequestDispatcher("/view/cashier/update-product-in-check.jsp").forward(req, resp);
//    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String productName = request.getParameter("productName");
//        String newQuantity = request.getParameter("newQuantity");
//
//        boolean result;
//        try {
//            result = checkServiceImpl.updateCheckElement(productName, Integer.parseInt(newQuantity));
//        } catch (NullPointerException | NumberFormatException e) {
//            result = false;
//        }
//
//        String url = "/cashier/update-product-in-check";
//        if (!result)
//            url += "?error=Cannot update product in check. Please try again";
//        else {
//            url += "?success=" + "Check was successfully updated";
//            url += "&productName=" + productName;
//        }
//
//        response.sendRedirect(url);
//    }


}
