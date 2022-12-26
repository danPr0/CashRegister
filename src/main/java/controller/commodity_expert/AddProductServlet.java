package controller.commodity_expert;

import exception.ProductTranslationException;
import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
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
import java.nio.charset.StandardCharsets;
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

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String measure = req.getParameter("measure");
        String quantity = req.getParameter("quantity");
        String price = req.getParameter("price");

        Language lang = Language.getLanguage(req);
        Map<Language, String> productNames = new HashMap<>();
        Arrays.stream(Language.values()).toList().forEach(e -> productNames.put(e, req.getParameter("productName_" + e.toString())));

        URIBuilder uriBuilder = new URIBuilder("/commodity-expert/add-product", UTF_8);
        String errorParam = null;
        try {
            if (productService.addProduct(productNames, ProductMeasure.valueOf(measure),
                    new BigDecimal(quantity).setScale(3, RoundingMode.UP).doubleValue(),
                    new BigDecimal(price).setScale(2, RoundingMode.UP).doubleValue()))
                uriBuilder.addParameter("success", "true");
            else errorParam = GetProperties.getMessageByLang("error.general", lang);
        }
        catch (ProductTranslationException e) {
            logger.error(e.getMessage());
            errorParam = GetProperties.getMessageByLang("error.commodity-expert.addProduct_" + e.getErrorTranslationLang(), lang);
        }
        if (errorParam != null) {
            uriBuilder.addParameter("error", errorParam);
            uriBuilder.addParameter("productName_ua", productNames.get(Language.ua));
            uriBuilder.addParameter("productName_en", productNames.get(Language.en));
            uriBuilder.addParameter("measure", measure);
            uriBuilder.addParameter("quantity", quantity);
            uriBuilder.addParameter("price", price);
        }

        resp.sendRedirect(uriBuilder.build().toString());
    }
}