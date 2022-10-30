package controller.commodity_expert;

import exception.ProductTranslationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ProductService;
import service_impl.ProductServiceImpl;
import util.GetProperties;
import util.enums.Language;
import util.enums.ProductMeasure;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *  Add product to store
 */
@WebServlet("/commodity-expert/add-product")
public class AddProductServlet extends HttpServlet {
    private final ProductService productService = ProductServiceImpl.getInstance();
    private final Logger logger = LogManager.getLogger(AddProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/commodity-expert/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String measure = req.getParameter("measure");
        String quantity = req.getParameter("quantity");
        String price = req.getParameter("price");

        Language lang = Language.getLanguage(req);
        Map<Language, String> productNames = new HashMap<>();
        Arrays.stream(Language.values()).toList().forEach(e -> productNames.put(e, req.getParameter("productName_" + e.toString())));

        String url = "/commodity-expert/add-product";
        String errorParam = null;
        try {
            if (productService.addProduct(productNames, ProductMeasure.valueOf(measure),
                    new BigDecimal(quantity).setScale(3, RoundingMode.UP).doubleValue(),
                    new BigDecimal(price).setScale(2, RoundingMode.UP).doubleValue()))
                url += "?success=true";
            else errorParam = GetProperties.getMessageByLang("error.general", lang);
        }
        catch (ProductTranslationException e) {
            logger.error(e.getMessage());
            errorParam = GetProperties.getMessageByLang("error.commodity-expert.addProduct_" + e.getErrorTranslationLang(), lang);
        }
        if (errorParam != null) {
            url += String.format("?error=%s&productName_ua=%s&productName_en=%s&measure=%s&quantity=%s&price=%s",
                    encode(errorParam, UTF_8),
                    encode(productNames.get(Language.ua), UTF_8), productNames.get(Language.en), measure, quantity, price);
        }

        resp.sendRedirect(url);
    }
}