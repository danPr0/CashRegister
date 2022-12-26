package controller.cashier;

import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
import service.CheckService;
import service.ReportService;
import service_impl.CheckServiceImpl;
import service_impl.ReportServiceImpl;
import util.GetProperties;
import util.enums.Language;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *  Close current check
 */
@WebServlet("/cashier/close-check")
public class CloseCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckServiceImpl.getInstance();
    private final ReportService reportService = ReportServiceImpl.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        URIBuilder uriBuilder = new URIBuilder("/cashier", UTF_8);
        if (reportService.createReport((String) req.getSession().getAttribute("email"))) {
            checkService.closeCheck();
            uriBuilder.addParameter("success", "true");
        }
        else uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.cashier.closeCheck", Language.getLanguage(req)));

        resp.sendRedirect(uriBuilder.build().toString());
    }
}
