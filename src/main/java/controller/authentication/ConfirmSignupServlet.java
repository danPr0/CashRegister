package controller.authentication;

import entity.User;
import io.jsonwebtoken.JwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service_impl.UserServiceImpl;
import util.enums.RoleName;
import util.token.AuthTokenProvider;
import util.token.ConfirmationTokenProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/auth/confirm-signup")
public class ConfirmSignupServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();
    private final Logger logger = LogManager.getLogger(ConfirmSignupServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        try {
            if (!ConfirmationTokenProvider.validateToken(token))
                throw new JwtException("Invalid token");

            String email = ConfirmationTokenProvider.getEmail(token);
            User user = userService.getUser(email);
            user.setEnabled(true);
            userService.updateUser(user);

            AuthTokenProvider.setTokenCookie(AuthTokenProvider.generateJwtToken(RoleName.guest, "accessToken"),
                    "accessToken", AuthTokenProvider.accessTokenExpirationInSec, resp);
            AuthTokenProvider.setTokenCookie(AuthTokenProvider.generateJwtToken(RoleName.guest, "refreshToken"),
                    "refreshToken", AuthTokenProvider.refreshTokenExpirationInSec, resp);

            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("firstName", user.getFirstName());

            //Creating user directory on server
            new File(req.getServletContext().getAttribute("FILES_DIR") + File.separator + email).mkdirs();
            resp.sendRedirect("/");
        }
        catch (Exception e) {
            logger.error(e);
            resp.sendError(400, "Invalid token");
        }
    }
}
