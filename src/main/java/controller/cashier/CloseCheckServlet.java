package controller.cashier;

import service_impl.CheckServiceImpl;
import service_impl.ReportServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Class is designed to close the current check
 */

@WebServlet("/cashier/close-check")
public class CloseCheckServlet extends HttpServlet {
    private final CheckServiceImpl checkServiceImpl = CheckServiceImpl.getInstance();
    private final ReportServiceImpl reportServiceImpl = ReportServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/cashier";
        if (reportServiceImpl.add((String) request.getSession().getAttribute("username"))) {
            checkServiceImpl.closeCheck();
            url += "?success=true";
        }
        else url += "?error=true";

        response.sendRedirect(url);
    }
}
