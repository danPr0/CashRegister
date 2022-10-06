package controller.authentication;

import util.JWTProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class is designed to process client logout
 */

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private final JWTProvider jwtProvider = JWTProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jwtProvider.setTokenCookie("", "accessToken", 0, resp);
        jwtProvider.setTokenCookie("", "refreshToken", 0, resp);

        req.getSession().removeAttribute("username");
        resp.sendRedirect("/");
    }
}
