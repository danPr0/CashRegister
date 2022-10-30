package controller.authentication;

import entity.User;
import garbage.RoleService;
import service.UserService;
import garbage.RoleServiceImpl;
import service_impl.UserServiceImpl;
import util.GetProperties;
import util.JWTProvider;
import util.enums.Language;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *  Process user authentication
 */
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();
    private final RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/authentication/login.jsp").forward(req, resp);
    }

    /**
     *  If user is authenticated, access and refresh tokens are added to cookies and user's redirected to main page.
     *  There can be 2 errors : "Bad email" and "Incorrect password"
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Language lang = Language.getLanguage(req);

        User user;
        if ((user = userService.getUser(email)) == null)
            resp.sendRedirect(String.format("/auth/login?error=%s&email=%s&password=%s",
                    encode(GetProperties.getMessageByLang("error.auth.login.badEmail", lang), UTF_8), email, encode(password, UTF_8)));
        else if (userService.authenticate(email, password)) {
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(user.getRoleId(), "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec, resp);
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(user.getRoleId(), "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, resp);

            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("firstName", user.getFirstName());
            resp.sendRedirect("/");
        }
        else resp.sendRedirect(String.format("/auth/login?error=%s&email=%s&password=%s",
                    encode(GetProperties.getMessageByLang("error.auth.login.incorrectPassword", lang), UTF_8), email, encode(password, UTF_8)));
    }
}
