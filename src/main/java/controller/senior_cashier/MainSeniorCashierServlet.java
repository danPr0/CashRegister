package controller.senior_cashier;

import service_impl.CheckServiceImpl;
import service_impl.ReportServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/senior-cashier")
public class MainSeniorCashierServlet extends HttpServlet {
    private final ReportServiceImpl reportServiceImpl = ReportServiceImpl.getInstance();
    private final CheckServiceImpl checkServiceImpl = CheckServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("checkSize", checkServiceImpl.getNumberOfRows());
        req.setAttribute("reportSize", reportServiceImpl.getNumberOfRows());
        req.getRequestDispatcher("/view/senior-cashier/seniorCashier.jsp").forward(req, resp);
    }
}
