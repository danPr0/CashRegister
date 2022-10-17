package controller.senior_cashier;

import service.CheckService;
import service_impl.CheckServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Cancel product in check
 */
@WebServlet("/senior-cashier/cancel-check")
public class CancelCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/senior-cashier";
        if (!checkService.cancelCheck())
            url += "?error=true";
        else url += "?success=true";

        resp.sendRedirect(url);
    }
}
