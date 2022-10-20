package controller.commodity_expert;


import entity.Product;
import service.ProductService;
import service_impl.ProductServiceImpl;
import util.enums.Language;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Find and update product in store
 */
@WebServlet("/commodity-expert/update-product")
public class UpdateProductServlet extends HttpServlet {
    private final ProductService productService = ProductServiceImpl.getInstance();

    /**
     * Return product details to client
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String product = req.getParameter("product");
        if (product != null) {
            Product productToUpdate;
            try {
                productToUpdate = productService.getProduct(Integer.parseInt(product));
            } catch (NumberFormatException e1) {
                productToUpdate = productService.getProduct(product, Language.valueOf(req.getSession().getAttribute("lang").toString()));
            }

            if (productToUpdate == null)
                req.setAttribute("error", "true");
            else req.setAttribute("product", productService.convertToDTO(productToUpdate, Language.valueOf(req.getSession().getAttribute("lang").toString())));
        }

        req.getRequestDispatcher("/view/commodity-expert/updateProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");
        String newQuantity = String.format(req.getParameter("newQuantity"), "%.3f");
        String newPrice = String.format(req.getParameter("newPrice"), "%.2f");

        String url = "/commodity-expert/update-product?product=" + productId;
        if (!productService.updateProduct(Integer.parseInt(productId),
                new BigDecimal(newQuantity).setScale(3, RoundingMode.UP).doubleValue(),
                new BigDecimal(newPrice).setScale(2, RoundingMode.UP).doubleValue()))
            url += "&error=true";
        else url += "&success=true";

        resp.sendRedirect(url);
    }
}