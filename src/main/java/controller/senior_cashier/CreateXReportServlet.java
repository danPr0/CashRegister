package controller.senior_cashier;

import dto.ReportDTO;
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
        int total;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        }
        catch (NumberFormatException e) {
            page = 1;
        }
        try {
            total = Integer.parseInt(req.getServletContext().getAttribute("reportTotalPerPage").toString());
        }
        catch (NumberFormatException e) {
            total = 10;
        }

        List<ReportDTO> report = reportService.getPerPage(page, total, req.getParameter("sortBy"));
        int nOfPages = (reportService.getNumberOfRows() + total - 1) / total;

        if (!report.isEmpty()) {
            req.setAttribute("report", report);
            req.setAttribute("nOfPages", nOfPages);
            req.setAttribute("sort", req.getParameter("sortBy"));
        }
        else req.setAttribute("error", "Something went wrong. Please try again.");

        req.getRequestDispatcher("/view/senior-cashier/createXReport.jsp").forward(req, resp);
    }
}
