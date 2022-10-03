package controller.authentication;

import entity.User;
import service.UserService;
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
    private final UserService userService = UserService.getInstance();
    private final JWTProvider jwtProvider = JWTProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/authentication/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userService.authenticate(username, password)) {
            User user = userService.getUser(username);
//            req.getServletContext().setAttribute("username", username);
            req.getSession().setAttribute("username", username);

//            Cookie cookie = new Cookie("token", jwtProvider.generateJwtToken(user.getRole().getName().toString()));
//            cookie.setPath("/");
//            cookie.setHttpOnly(true);
//            resp.addCookie(cookie);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(user.getRole().getName(), "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec, resp);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(user.getRole().getName(), "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, resp);

            resp.sendRedirect("/");
        }
        else {
            resp.sendRedirect("/auth/login?error=Username or password is incorrect");
        }
    }
}
