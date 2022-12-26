package controller;


import util.enums.RoleName;
import util.token.AuthTokenProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Distribute user to the specific page according to his role
 */
@WebServlet(name = "MainServlet", value = "/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accessToken = AuthTokenProvider.resolveToken(req, "accessToken");
        RoleName role = AuthTokenProvider.getRole(accessToken);

        switch (role) {
            case guest -> req.getRequestDispatcher("/view/guest/guestPage.jsp").forward(req, resp);
            case admin -> req.getRequestDispatcher("/view/admin/authorizeUser.jsp").forward(req, resp);
            case cashier -> resp.sendRedirect("/cashier");
            case commodity_expert -> resp.sendRedirect("/commodity-expert");
            case senior_cashier ->  resp.sendRedirect("/senior-cashier");
        }
    }
}
