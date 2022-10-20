package garbage;

import service_impl.ReportServiceImpl;
import util.ReportFileCreator;
import util.enums.Language;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/senior-cashier/create-z-report")
public class CreateZReportServlet extends HttpServlet {
    private final ReportServiceImpl reportServiceImpl = ReportServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Language lang = Language.valueOf(req.getSession().getAttribute("lang").toString());
        ReportFileCreator.createCsv("z-report.csv", req.getServletContext(), lang);
        ReportFileCreator.createPdf("z-report.pdf", req.getServletContext(), lang);
        ReportFileCreator.createXls("z-report.xls", req.getServletContext(), lang);

        reportServiceImpl.deleteAll();
        resp.sendRedirect("/senior-cashier");
    }
}
