package controller.senior_cashier;

import service_impl.CheckServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/senior-cashier/cancel-check")
public class CancelCheckServlet extends HttpServlet {
    private final CheckServiceImpl checkServiceImpl = CheckServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/senior-cashier";
        if (!checkServiceImpl.cancelCheck())
            url += "?error=true";
        else url += "?success=true";

        response.sendRedirect(url);
    }
}
