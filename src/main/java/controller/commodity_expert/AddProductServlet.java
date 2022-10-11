package controller.commodity_expert;

import service_impl.ProductServiceImpl;
import util.ProductMeasure;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@WebServlet("/commodity-expert/add-product")
public class AddProductServlet extends HttpServlet {
    ProductServiceImpl productServiceImpl = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/commodity-expert/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String measure = request.getParameter("measure");
        String quantity = request.getParameter("quantity");
        String price = request.getParameter("price");

//        boolean result;
//        try {
//            result = productService.addProduct(productName, Integer.parseInt(quantity), Double.parseDouble(price));
//        }
//        catch (NumberFormatException | NullPointerException e) {
//            result = false;
//        }

        String url = "/commodity-expert/add-product";
        if (!productServiceImpl.addProduct(productName, ProductMeasure.valueOf(measure),
                new BigDecimal(quantity).setScale(3, RoundingMode.UP).doubleValue(),
                new BigDecimal(price).setScale(2, RoundingMode.UP).doubleValue()))
            url += String.format("?error=true&productName=%s&measure=%s&quantity=%s&price=%s", productName, measure, quantity, price);
        else url += "?success=true";

        response.sendRedirect(url);
    }
}