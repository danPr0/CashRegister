package controller.authentication;

import service_impl.UserServiceImpl;
import util.JWTProvider;
import util.RoleName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Class is designed to process client registration
 */

@WebServlet("/auth/signup")
public class SignupServlet extends HttpServlet {
    UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/authentication/signup.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("passwordConfirm");
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");

        System.out.println(email);

        if (!password.equals(passwordConfirm))
            resp.sendRedirect(String.format("/auth/signup?error=passwordMismatch&email=%s&firstName=%s&secondName=%s&password=%s&passwordConfirm=%s",
                    email, encode(firstName, UTF_8), encode(secondName, UTF_8), encode(password, UTF_8), encode(passwordConfirm, UTF_8)));
        else if (userServiceImpl.insertUser(email, password, firstName, secondName, RoleName.guest)) {
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(RoleName.guest, "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec, resp);
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(RoleName.guest, "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, resp);

            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("firstName", firstName);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect(String.format("/auth/signup?error=badEmail&email=%s&firstName=%s&secondName=%s&password=%s&passwordConfirm=%s",
                    email, encode(firstName, UTF_8), encode(secondName, UTF_8), encode(password, UTF_8), encode(passwordConfirm, UTF_8)));
        }
    }
}
