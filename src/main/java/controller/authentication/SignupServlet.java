package controller.authentication;

import service.UserService;
import util.JWTProvider;
import util.RoleName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class is designed to process client registration
 */

@WebServlet("/auth/signup")
public class SignupServlet extends HttpServlet {
    UserService userService = UserService.getInstance();
    private final JWTProvider jwtProvider = JWTProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/authentication/signup.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("passwordConfirm");
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");

        if (!password.equals(passwordConfirm))
            resp.sendRedirect(String.format("/auth/signup?error=passwordMismatch&username=%s&password=%s&passwordConfirm=%s&firstName=%s&secondName=%s",
                    username, password, passwordConfirm, firstName, secondName));
        else if (userService.insertUser(username, password, firstName, secondName, RoleName.guest)) {
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(RoleName.guest, "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec, resp);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(RoleName.guest, "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, resp);

            req.getSession().setAttribute("username", username);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect(String.format("/auth/signup?error=badUsername&username=%s&password=%s&passwordConfirm=%s&firstName=%s&secondName=%s",
                    username, password, passwordConfirm, firstName, secondName));
        }
    }
}
