package controller.senior_cashier;

import service.CheckService;
import service.ReportService;
import service_impl.CheckServiceImpl;
import service_impl.ReportServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Represent senior cashier main page
 */
@WebServlet("/senior-cashier")
public class MainSeniorCashierServlet extends HttpServlet {
    private final ReportService reportService = ReportServiceImpl.getInstance();
    private final CheckService checkService = CheckServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("checkSize", checkService.getNumberOfRows());
        req.setAttribute("reportSize", reportService.getNoOfRows());
        req.getRequestDispatcher("/view/senior-cashier/seniorCashier.jsp").forward(req, resp);
    }
}
