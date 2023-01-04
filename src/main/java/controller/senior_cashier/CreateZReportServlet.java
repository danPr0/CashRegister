package controller.senior_cashier;

import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
import service.ReportService;
import service_impl.ReportServiceImpl;
import util.GetProperties;
import util.SendingEmailService;
import util.enums.Language;
import util.report.ReportEnumFactory;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/senior-cashier/create-z-report")
public class CreateZReportServlet extends HttpServlet {
    private final ReportService reportService = ReportServiceImpl.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userDir = req.getServletContext().getAttribute("FILES_DIR") + File.separator + req.getSession().getAttribute("email");

        String filepath = userDir + File.separator + "z-report." + ReportEnumFactory.pdf;
        ReportEnumFactory.pdf.getInstance().createReport(filepath, Language.getLanguage(req));

        URIBuilder uriBuilder = new URIBuilder("/senior-cashier");
        try {
            SendingEmailService.sendZReport(req.getSession().getAttribute("email").toString(), Language.getLanguage(req), filepath);
        } catch (MessagingException e) {
            uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.general", Language.getLanguage(req)));
        }

        reportService.deleteAll();
        resp.sendRedirect(uriBuilder.build().toString());
    }
}
