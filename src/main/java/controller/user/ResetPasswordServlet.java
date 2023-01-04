package controller.user;

import entity.User;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hc.core5.net.URIBuilder;
import service.UserService;
import service_impl.UserServiceImpl;
import util.GetProperties;
import util.SendingEmailService;
import util.captcha.RecaptchaValidation;
import util.enums.Language;
import util.token.PasswordTokenProvider;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet("/user/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");

        if (PasswordTokenProvider.validateToken(token)) {
            try {
                userService.resetPassword(PasswordTokenProvider.getEmail(token), PasswordTokenProvider.getPassword(token));
                resp.sendRedirect("/logout");
            }
            catch (Exception e) {
                resp.sendError(400, "Invalid token");
            }
        }
        else req.getRequestDispatcher("/view/user/resetPassword.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        if (!RecaptchaValidation.verify(req.getParameter("g-recaptcha-response"))) {
            resp.sendError(400, "Strange behavior was noticed");
            return;
        }

        URIBuilder uriBuilder = new URIBuilder("/reset-password", UTF_8);
        User user = userService.getUser(email);
        if (user == null || !user.isEnabled()) {
            uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.user.resetPassword", Language.getLanguage(req)));
            uriBuilder.addParameter("email", email);
        }
        else {
            try {
                String newPassword = RandomStringUtils.randomAlphanumeric(20);
                SendingEmailService.sendResetPasswordEmail(email, newPassword, "http://localhost:8080/reset-password?token=" +
                                PasswordTokenProvider.generateJwtToken(email, newPassword), Language.getLanguage(req));
                uriBuilder.addParameter("success", "true");
            } catch (MessagingException e) {
                uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.general", Language.getLanguage(req)));
                uriBuilder.addParameter("email", email);
            }
        }
        resp.sendRedirect(uriBuilder.build().toString());
    }
}
