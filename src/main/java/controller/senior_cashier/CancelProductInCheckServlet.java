package controller.senior_cashier;

import entity.CheckEntity;
import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
import service.CheckService;
import service_impl.CheckServiceImpl;
import util.GetProperties;
import util.enums.Language;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Cancel product in check
 */
@WebServlet("/senior-cashier/cancel-product-in-check")
public class CancelProductInCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/senior-cashier/cancelProductInCheck.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productParam = req.getParameter("product");
        String quantityParam = req.getParameter("quantity");
        Language lang = Language.getLanguage(req);

        CheckEntity checkEntity;
        try {
            checkEntity = checkService.getCheckElement(Integer.parseInt(productParam));
        }
        catch (NumberFormatException e) {
            checkEntity = checkService.getCheckElement(productParam, lang);
        }

        URIBuilder uriBuilder = new URIBuilder("/senior-cashier/cancel-product-in-check", UTF_8);
        if (checkEntity == null) {
            uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.senior-cashier.cancelProduct.noSuchProduct", lang));
            uriBuilder.addParameter("product", productParam);
            uriBuilder.addParameter("quantity", quantityParam);
        }
        else if (!checkService.cancelCheckElement(checkEntity,  new BigDecimal(quantityParam).setScale(3, RoundingMode.UP).doubleValue())) {
            uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.senior-cashier.cancelProduct.overExceededQuantity", lang));
            uriBuilder.addParameter("product", productParam);
            uriBuilder.addParameter("quantity", quantityParam);
        }
        else uriBuilder.addParameter("success", "true");

        resp.sendRedirect(uriBuilder.build().toString());
    }
}
