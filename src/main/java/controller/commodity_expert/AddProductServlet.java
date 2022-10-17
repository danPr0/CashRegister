package controller.commodity_expert;

import service.ProductService;
import service_impl.ProductServiceImpl;
import util.ProductMeasure;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *  Add product to store
 */
@WebServlet("/commodity-expert/add-product")
public class AddProductServlet extends HttpServlet {
    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/commodity-expert/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("productName");
        String measure = req.getParameter("measure");
        String quantity = req.getParameter("quantity");
        String price = req.getParameter("price");

        String url = "/commodity-expert/add-product";
        if (!productService.addProduct(productName, ProductMeasure.valueOf(measure),
                new BigDecimal(quantity).setScale(3, RoundingMode.UP).doubleValue(),
                new BigDecimal(price).setScale(2, RoundingMode.UP).doubleValue()))
            url += String.format("?error=true&productName=%s&measure=%s&quantity=%s&price=%s", encode(productName, UTF_8), measure, quantity, price);
        else url += "?success=true";

        resp.sendRedirect(url);
    }
}