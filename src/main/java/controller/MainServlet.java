package controller;


import entity.Role;
import util.JWTProvider;
import util.RoleName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class is designed to distribute user to the specific page according to his role
 */

@WebServlet("/")
public class MainServlet extends HttpServlet {
    private final JWTProvider jwtProvider = JWTProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accessToken = jwtProvider.resolveToken(req, "accessToken");
        RoleName role = jwtProvider.getRole(accessToken);

        if (role.equals(RoleName.cashier))
//            req.getRequestDispatcher("/view/cashier/cashier.jsp").forward(req, resp);
            resp.sendRedirect("/cashier");
        else if (role.equals(RoleName.commodity_expert))
//            req.getRequestDispatcher("/view/commodity-expert/commodityExpert.jsp").forward(req, resp);
            resp.sendRedirect("/commodity-expert");
        else if (role.equals(RoleName.senior_cashier))
            resp.sendRedirect("/senior-cashier");
//            req.getRequestDispatcher("/view/senior-cashier/seniorCashier.jsp").forward(req, resp);
    }
}
