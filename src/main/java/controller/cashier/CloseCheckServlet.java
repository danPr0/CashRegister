package controller.cashier;

import service.CheckService;
import service.ReportService;
import service_impl.CheckServiceImpl;
import service_impl.ReportServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 *  Close current check
 */
@WebServlet("/cashier/close-check")
public class CloseCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckServiceImpl.getInstance();
    private final ReportService reportService = ReportServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/cashier";
        if (reportService.createReport((String) req.getSession().getAttribute("email"))) {
            checkService.closeCheck();
            url += "?success=true";
        }
        else url += "?error=true";

        resp.sendRedirect(url);
    }
}
