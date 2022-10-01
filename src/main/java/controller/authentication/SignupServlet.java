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
        req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("passwordConfirm");
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");

        if (!password.equals(passwordConfirm))
            resp.sendRedirect("/auth/signup?error=Password mismatch");
        else if (userService.insertUser(username, password, firstName, secondName, RoleName.cashier)) {
            req.getServletContext().setAttribute("username", username);

//            Cookie cookie = new Cookie("token", jwtProvider.generateJwtToken(RoleName.cashier.toString()));
//            cookie.setHttpOnly(true);
//            cookie.setPath("/");
//            resp.addCookie(cookie);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(RoleName.cashier, "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec, resp);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(RoleName.cashier, "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, resp);

            resp.sendRedirect("/");
        }
        else {
            resp.sendRedirect("/auth/signup?error=Username is already taken");
        }
    }
}
