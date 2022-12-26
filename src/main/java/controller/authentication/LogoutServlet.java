package controller.authentication;

import util.token.AuthTokenProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  Process client logout
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    /**
     *  Remove access and refresh tokens from cookies, session attributes
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthTokenProvider.setTokenCookie("", "accessToken", 0, resp);
        AuthTokenProvider.setTokenCookie("", "refreshToken", 0, resp);

        req.getSession().removeAttribute("email");
        req.getSession().removeAttribute("firstName");
        resp.sendRedirect("/");
    }
}
