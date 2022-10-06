package controller.cashier;

import service.CheckService;
import service.ReportService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Class is designed to close the current check
 */

@WebServlet("/cashier/close-check")
public class CloseCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckService.getInstance();
    private final ReportService reportService = ReportService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/cashier";
        if (reportService.add((String) request.getSession().getAttribute("username"))) {
            checkService.closeCheck();
            url += "?success=true";
        }
        else url += "?error=true";

        response.sendRedirect(url);
    }
}
