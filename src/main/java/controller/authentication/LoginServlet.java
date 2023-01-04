package controller.authentication;

import entity.User;
import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
import service.UserService;
import service_impl.UserServiceImpl;
import util.GetProperties;
import util.enums.Language;
import util.token.AuthTokenProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *  Process user authentication
 */
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/authentication/login.jsp").forward(req, resp);
    }

    /**
     *  If user is authenticated, access and refresh tokens are added to cookies and user's redirected to main page.
     *  There can be 2 errors : "Bad email" and "Incorrect password"
     */
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Language lang = Language.getLanguage(req);

        User user = userService.getUser(email);
        URIBuilder uriBuilder = new URIBuilder("/auth/login", UTF_8);

        if (user == null || !user.isEnabled()) {
            uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.auth.login.badEmail", lang));
            uriBuilder.addParameter("email", email);
            uriBuilder.addParameter("password", password);
        }
        else if (userService.authenticate(email, password)) {
            AuthTokenProvider.setTokenCookie(AuthTokenProvider.generateJwtToken(user.getRoleId(), "accessToken"),
                    "accessToken", AuthTokenProvider.accessTokenExpirationInSec, resp);
            AuthTokenProvider.setTokenCookie(AuthTokenProvider.generateJwtToken(user.getRoleId(), "refreshToken"),
                    "refreshToken", AuthTokenProvider.refreshTokenExpirationInSec, resp);

            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("firstName", user.getFirstName());
            uriBuilder.setPath("/");
        }
        else {
            uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.auth.login.incorrectPassword", lang));
            uriBuilder.addParameter("email", email);
            uriBuilder.addParameter("password", password);
        }

        resp.sendRedirect(uriBuilder.build().toString());
    }
}
