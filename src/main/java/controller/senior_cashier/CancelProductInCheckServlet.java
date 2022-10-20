package controller.senior_cashier;

import entity.CheckEntity;
import service.CheckService;
import service_impl.CheckServiceImpl;
import util.enums.Language;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static util.GetProperties.getMessageByLang;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productParam = req.getParameter("product");
        String quantityParam = req.getParameter("quantity");
        Language lang = Language.valueOf(req.getSession().getAttribute("lang").toString());

        CheckEntity checkEntity;
        try {
            checkEntity = checkService.getCheckElement(Integer.parseInt(productParam));
        }
        catch (NumberFormatException e) {
            checkEntity = checkService.getCheckElement(productParam, lang);
        }

        String url = "/senior-cashier/cancel-product-in-check";
        if (checkEntity == null)
            url += String.format("?error=%s&product=%s&quantity=%s",
                    encode(getMessageByLang("msg.error.senior-cashier.cancelProduct.noSuchProduct", lang), UTF_8),
                    encode(productParam, UTF_8), quantityParam);
        else if (!checkService.cancelCheckElement(checkEntity,  new BigDecimal(quantityParam).setScale(3, RoundingMode.UP).doubleValue()))
            url += String.format("?error=%s&product=%s&quantity=%s",
                    encode(getMessageByLang("msg.error.senior-cashier.cancelProduct.overExceededQuantity", lang), UTF_8),
                    encode(productParam, UTF_8), quantityParam);
        else url += "?success=true";

        resp.sendRedirect(url);
    }
}
