package controller.senior_cashier;

import entity.ReportElement;
import service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/senior-cashier/create-x-report")
public class CreateXReportServlet extends HttpServlet {
    private final ReportService reportService = ReportService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        }
        catch (NumberFormatException e) {
            page = 1;
        }
        int total = 1;

        List<ReportElement> report = reportService.getPerPage(page, total);
        int nOfPages = (reportService.getNumberOfRows() + total - 1) / total;

        if (!report.isEmpty()) {
            req.setAttribute("report", report);
            req.setAttribute("nOfPages", nOfPages);
        }

        req.getRequestDispatcher("/view/senior-cashier/createReport.jsp").forward(req, resp);
    }
}
