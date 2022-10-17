package controller.senior_cashier;

import dto.ReportDTO;
import service.ReportService;
import service_impl.ReportServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
        int page;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        else page = 1;

        int total;
        try {
            total = Integer.parseInt(req.getParameter("total"));
            req.getSession().setAttribute("reportTotalPerPage", total);
        }
        catch (NumberFormatException e) {
            total = Integer.parseInt(req.getSession().getAttribute("reportTotalPerPage").toString());
        }

        String sortBy;
        if (req.getParameter("sortBy") != null) {
            sortBy = req.getParameter("sortBy");
            req.getSession().setAttribute("reportSortBy", sortBy);
        }
        else sortBy = req.getSession().getAttribute("reportSortBy").toString();

        String orderBy;
        if (req.getParameter("orderBy") != null) {
            orderBy = req.getParameter("orderBy");
            req.getSession().setAttribute("reportOrderBy", orderBy);
        }
        else orderBy = req.getSession().getAttribute("reportOrderBy").toString();

        List<ReportDTO> report = reportService.convertToDTO(reportService.getPerPage(page, total,
                wrapSortParam(sortBy), orderBy.equals("asc")));
        int nOfPages = (reportService.getNoOfRows() + total - 1) / total;

        if (!report.isEmpty()) {
            req.setAttribute("report", report);
            req.setAttribute("nOfPages", nOfPages);
        }
        else req.setAttribute("error", "true");

        req.getRequestDispatcher("/view/senior-cashier/createXReport.jsp").forward(req, resp);
    }

    private String wrapSortParam(String sortParam) {
        String result = "id";
        if (Objects.equals(sortParam, "createdBy"))
            result = "createdBy";
        else if (Objects.equals(sortParam, "quantity"))
            result = "quantity";
        else if (Objects.equals(sortParam, "price"))
            result = "price";

        return  result;
    }
}
