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
//        String productId = req.getParameter("productId");
//        String productName = req.getParameter("productName");
//
//        Product product;
//        if (productId != null)
//            product = productService.getProductById(Integer.parseInt(productId));
//        else try {
//            product = productService.getProductByName(productName);
//        } catch (NumberFormatException e) {
//            product = null;
//        }

        String product = req.getParameter("product");
        if (product != null) {
            Product productToUpdate;
            try {
                productToUpdate = productService.getProductById(Integer.parseInt(product));
            } catch (NumberFormatException e1) {
                productToUpdate = productService.getProductByName(product);
            }

            if (productToUpdate == null)
                req.setAttribute("error", "true");
            else req.setAttribute("product", productToUpdate);
        }

        req.getRequestDispatcher("/view/commodity-expert/updateProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String quantity = request.getParameter("quantity");
        String price = request.getParameter("price");

        String url = "/commodity-expert/update-product?product=" + productId;
        if (!productService.updateProductById(Integer.parseInt(productId), Integer.parseInt(quantity), Double.parseDouble(price)))
            url += "&error=true";
        else url += "&success=true";

        response.sendRedirect(url);
    }
}