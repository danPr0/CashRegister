package controller.senior_cashier;

import dto.ReportDTO;
import service.ReportService;
import service_impl.ReportServiceImpl;
import util.GetProperties;
import util.enums.Language;
import util.table.ReportColumnName;
import util.table.TableService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Create X report
 */
@WebServlet("/senior-cashier/create-x-report")
public class CreateXReportServlet extends HttpServlet {
    private final ReportService reportService = ReportServiceImpl.getInstance();

    /**
     * Implement report pagination
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

        int total = TableService.getTotalPerPage(req, "total", "reportTotalPerPage");
        ReportColumnName sortBy = ReportColumnName.valueOf(TableService.getTableSortParam(req, "sortBy", "reportSortBy"));
        String orderBy = TableService.getTableSortParam(req, "orderBy", "reportOrderBy");

        List<ReportDTO> report = reportService.convertToDTO(reportService.getPerPage(page, total,
                sortBy, orderBy.equals("asc")), Language.getLanguage(req));
        int nOfPages = (reportService.getNoOfRows() + total - 1) / total;

        if (!report.isEmpty()) {
            req.setAttribute("report", report);
            req.setAttribute("nOfPages", nOfPages);
        }
        else req.setAttribute("error", GetProperties.getMessageByLang("error.senior-cashier.createXReport", Language.getLanguage(req)));

        req.getRequestDispatcher("/view/senior-cashier/createXReport.jsp").forward(req, resp);
    }
}
