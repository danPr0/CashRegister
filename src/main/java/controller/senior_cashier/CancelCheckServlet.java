package controller.senior_cashier;

import service.CheckService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/senior-cashier/cancel-check")
public class CancelCheckServlet extends HttpServlet {
    private final CheckService checkService = CheckService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/senior-cashier";
        if (!checkService.cancelCheck())
            url += "?error=true";
        else url += "?success=true";

        response.sendRedirect(url);
    }
}
