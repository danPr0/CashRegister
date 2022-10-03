package controller.senior_cashier;

import service.ReportService;
import util.ReportFileCreator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/senior-cashier/create-z-report")
public class CreateZReportServlet extends HttpServlet {
    private final ReportService reportService = ReportService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReportFileCreator.createCsv("z-report.csv", req.getServletContext());
        ReportFileCreator.createPdf("z-report.pdf", req.getServletContext());
        ReportFileCreator.createXls("z-report.xls", req.getServletContext());

        reportService.deleteAll();
        resp.sendRedirect("/senior-cashier");
    }
}
