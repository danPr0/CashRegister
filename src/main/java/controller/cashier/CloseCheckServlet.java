package controller.cashier;

import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
import service.CheckService;
import service.ReportService;
import service_impl.CheckServiceImpl;
import service_impl.ReportServiceImpl;
import util.GetProperties;
import util.enums.Language;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
