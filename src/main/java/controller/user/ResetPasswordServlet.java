package controller.user;

import dao.KeyDAO;
import dao_impl.KeyDAOImpl;
import entity.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import service.UserService;
import service_impl.UserServiceImpl;
import util.AESUtil;
import util.GetProperties;
import util.SendingEmailService;
import util.enums.Language;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String passwordInUrl = req.getParameter("newPassword");
        String secretKeyInUrl = req.getParameter("secretKey");

        if (passwordInUrl != null) {
            try {
                SecretKeySpec secretKey = new SecretKeySpec(Base64.decodeBase64(secretKeyInUrl), "AES");
                userService.resetPassword(email, userService.decryptPassword(secretKey, passwordInUrl));
                resp.sendRedirect("/logout");
            }
            catch (Exception e) {
                resp.sendError(400, "Dont play with me :)");
            }
        } else req.getRequestDispatcher("/view/user/resetPassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String email = req.getParameter("email") == null ? req.getSession().getAttribute("email").toString() : req.getParameter("email");
        String email = req.getParameter("email");
        User user = userService.getUser(email);

        String redirectUrl;
        if (user == null)
            redirectUrl = String.format("/reset-password?error=%s&email=%s",
                    encode(GetProperties.getMessageByLang("error.user.resetPassword", Language.getLanguage(req)), UTF_8),
                    encode(email, UTF_8));
        else {
            try {
                SecretKey secretKey = AESUtil.generateSecretKey();
                String newPassword = RandomStringUtils.randomAlphanumeric(20);

                SendingEmailService.sendResetPasswordEmail(email, newPassword,
                        "http://localhost:8080/reset-password?email=" + email + "&newPassword=" +
                                userService.encryptPassword(secretKey, newPassword) + "&secretKey=" + Base64.encodeBase64URLSafeString(secretKey.getEncoded()));
                redirectUrl = "/reset-password?success=true";
            } catch (MessagingException e) {
                redirectUrl = String.format("/reset-password?error=%s&email=%s",
                        encode(GetProperties.getMessageByLang("error.general", Language.getLanguage(req)), UTF_8),
                        encode(email, UTF_8));
            }
        }
        resp.sendRedirect(redirectUrl);
    }
}
