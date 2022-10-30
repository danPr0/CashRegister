package controller.senior_cashier;

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

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet("/senior-cashier/create-z-report")
public class CreateZReportServlet extends HttpServlet {
    private final ReportServiceImpl reportServiceImpl = ReportServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userDir = req.getServletContext().getAttribute("FILES_DIR") + File.separator + req.getSession().getAttribute("email");
//        File userFolder = new File(userDir);
//        if (!userFolder.exists())
//            userFolder.mkdirs();

        String filepath = userDir + File.separator + "z-report." + ReportEnumFactory.pdf;
        ReportEnumFactory.pdf.getInstance().createReport(filepath, Language.getLanguage(req));

        String redirectUrl = "/senior-cashier";
        try {
            SendingEmailService.sendZReport(req.getSession().getAttribute("email").toString(), filepath);
        } catch (MessagingException e) {
            redirectUrl += String.format("/reset-password?error=%s",
                    encode(GetProperties.getMessageByLang("error.general", Language.getLanguage(req)), UTF_8));
        }

        reportServiceImpl.deleteAll();
        resp.sendRedirect(redirectUrl);
    }
}
