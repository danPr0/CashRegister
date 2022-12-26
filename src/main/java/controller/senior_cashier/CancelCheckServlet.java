package controller.senior_cashier;

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

/**
 * Cancel product in check
 */
@WebServlet("/senior-cashier/cancel-check")
public class CancelCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckServiceImpl.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        URIBuilder uriBuilder = new URIBuilder("/senior-cashier");
        if (checkService.cancelCheck())
            uriBuilder.addParameter("success", "true");
        else uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.senior-cashier.cancelCheck", Language.getLanguage(req)));

        resp.sendRedirect(uriBuilder.build().toString());
    }
}
