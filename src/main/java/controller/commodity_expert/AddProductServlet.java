package controller.commodity_expert;

import service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/commodity-expert/add-product")
public class AddProductServlet extends HttpServlet {
    ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/commodity-expert/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String quantity = request.getParameter("quantity");
        String price = request.getParameter("price");

        boolean result;
        try {
            result = productService.addProduct(productName, Integer.parseInt(quantity), Double.parseDouble(price));
        }
        catch (NumberFormatException | NullPointerException e) {
            result = false;
        }

        String url = "/commodity-expert/add-product";
        if (!result)
            url += "?error=Cannot add product. Please try again";
        else {
            url += "?success=" + "Product was successfully added";
            url += "&productName=" + productName;
        }

        response.sendRedirect(url);
    }
}