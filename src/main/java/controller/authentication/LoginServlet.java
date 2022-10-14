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

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

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
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user;
        if ((user = userServiceImpl.getUser(email)) == null)
            resp.sendRedirect(String.format("/auth/login?error=badEmail&email=%s&password=%s", email, encode(password, UTF_8)));
        else if (userServiceImpl.authenticate(email, password)) {
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(user.getRole().getName(), "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec, resp);
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(user.getRole().getName(), "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, resp);

            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("firstName", user.getFirstName());
            resp.sendRedirect("/");
        }
        else resp.sendRedirect(String.format("/auth/login?error=incorrectPassword&email=%s&password=%s", email, encode(password, UTF_8)));
    }
}
