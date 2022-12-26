package controller.authentication;

import cn.apiclub.captcha.Captcha;
import dao_impl.CheckDAOImpl;
import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service_impl.UserServiceImpl;
import util.GetProperties;
import util.SendingEmailService;
import util.captcha.RecaptchaValidation;
import util.captcha.SimpleCaptchaService;
import util.enums.Language;
import util.enums.RoleName;
import util.token.ConfirmationTokenProvider;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Process user registration
 */
@WebServlet("/auth/signup")
public class SignupServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();
    private final Logger logger = LogManager.getLogger(SignupServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Captcha captcha = SimpleCaptchaService.createCaptcha();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(captcha.getImage(), "png", output);
        String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());
        req.setAttribute("captchaImage", imageAsBase64);

        req.getSession().setAttribute("captcha", captcha);
        req.getRequestDispatcher("/view/authentication/signup.jsp").forward(req, resp);
    }

    /**
     * If user is registered, access and refresh tokens are added to cookies and user's redirected to the main page.
     * There can be 2 errors : "Email is already taken" and "Passwords mismatch"
     */
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("passwordConfirm");
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        Captcha validCaptcha = (Captcha) req.getSession().getAttribute("captcha");

        URIBuilder uriBuilder = new URIBuilder("/auth/signup", UTF_8);
        if (!req.getParameter("captcha").equals(validCaptcha.getAnswer()) || !RecaptchaValidation.verify(req.getParameter("g-recaptcha-response")))
            uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.auth.signup.badCaptcha", Language.getLanguage(req)));
        else if (userService.addUser(email, password, firstName, secondName, RoleName.guest, false)) {
            String confirmUrl = "http://localhost:8080/auth/confirm-signup?token=" + ConfirmationTokenProvider.generateJwtToken(email);
            try {
                SendingEmailService.sendSignupConfirmationEmail(email, confirmUrl, Language.getLanguage(req));
            } catch (MessagingException e) {
                logger.error("Cannot send confirmation email to " + email, e.getCause());
            }
            uriBuilder.addParameter("success", "true");
        } else
            uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.auth.signup.badEmail", Language.getLanguage(req)));

        if (uriBuilder.getQueryParams().get(0).getName().equals("error")) {
            uriBuilder.addParameter("email", email);
            uriBuilder.addParameter("firstName", firstName);
            uriBuilder.addParameter("secondName", secondName);
            uriBuilder.addParameter("password", password);
            uriBuilder.addParameter("passwordConfirm", passwordConfirm);
        }

        resp.sendRedirect(uriBuilder.build().toString());
    }
}
