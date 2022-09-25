package controller.commodity_expert;


import entity.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/commodity-expert/update-product")
public class UpdateProductServlet extends HttpServlet {
    ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("productName");

        if (productName != null) {
            Product product = productService.getProduct(productName);
            if (product == null)
                req.setAttribute("error", "No such product");
            else {
                req.setAttribute("product", product);
            }
        }

        req.getRequestDispatcher("/view/commodity-expert/updateProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String quantity = request.getParameter("newQuantity");
        String price = request.getParameter("newPrice");

        boolean result;
        try {
            System.out.println(quantity);
            result = productService.updateProduct(productName, Integer.parseInt(quantity), Double.parseDouble(price));
        }
        catch (NumberFormatException | NullPointerException e) {
            result = false;
        }

        String url = "/commodity-expert/update-product";
        if (!result)
            url += "?error=Cannot update product. Please try again";
        else {
            url += "?success=" + "Product was successfully updated";
            url += "&productName=" + productName;
        }

        response.sendRedirect(url);
    }
}