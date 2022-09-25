package controller.cashier;

import service.CheckService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cashier")
public class MainCashierServlet extends HttpServlet {
    private final CheckService checkService = CheckService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("checkSize", checkService.getNumberOfRows());
        req.getRequestDispatcher("/view/cashier/cashier.jsp").forward(req, resp);
    }
}
