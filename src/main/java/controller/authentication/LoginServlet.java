package controller.authentication;

import entity.User;
import service_impl.UserServiceImpl;
import util.JWTProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Class is designed to process client authentication
 */

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/authentication/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user;
        if ((user = userServiceImpl.getUser(username)) == null)
            resp.sendRedirect(String.format("/auth/login?error=badUsername&username=%s&password=%s", username, password));
        else if (userServiceImpl.authenticate(username, password)) {
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(user.getRole().getName(), "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec, resp);
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(user.getRole().getName(), "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, resp);

            req.getSession().setAttribute("username", username);
            resp.sendRedirect("/");
        }
        else resp.sendRedirect(String.format("/auth/login?error=incorrectPassword&username=%s&password=%s", username, password));
    }
}
