package controller.authentication;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import service.UserService;
import service_impl.UserServiceImpl;
import util.GetProperties;
import util.JWTProvider;
import util.enums.Language;
import util.enums.RoleName;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Process user registration
 */
@WebServlet("/auth/signup")
public class SignupServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Color> colors = new ArrayList<>();
        colors.add(Color.BLACK);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);

        List<Font> fonts = new ArrayList<>();
        fonts.add(new Font("Arial", Font.ITALIC, 40));

        Captcha captcha = new Captcha.Builder(120, 50)
                .addText(new DefaultWordRenderer(colors, fonts))
                .addBackground(new GradiatedBackgroundProducer(Color.white, Color.white))
                .gimp()
                .addBorder()
                .build();

        // display the image produced
//        CaptchaServletUtil.writeImage(resp, captcha.getImage());

//        File outputFile = new File(re + "/images/captcha.jpg");
//        ImageIO.write(captcha.getImage(), "jpg", outputFile);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(captcha.getImage(), "png", output);
        String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());
        req.setAttribute("captchaImage", imageAsBase64);

        // save the captcha value on session
        req.getSession().setAttribute("captcha", captcha);

        req.getRequestDispatcher("/view/authentication/signup.jsp").forward(req, resp);
    }

    /**
     * If user is registered, access and refresh tokens are added to cookies and user's redirected to the main page.
     * There can be 2 errors : "Email is already taken" and "Passwords mismatch"
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("passwordConfirm");
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        Language lang = Language.getLanguage(req);
        Captcha validCaptcha = (Captcha) req.getSession().getAttribute("captcha");

        if (!req.getParameter("captcha").equals(validCaptcha.getAnswer()))
            resp.sendRedirect(String.format("/auth/signup?error=%s&email=%s&firstName=%s&secondName=%s&password=%s&passwordConfirm=%s",
                    encode(GetProperties.getMessageByLang("error.auth.signup.badCaptcha", lang), UTF_8), email,
                    encode(firstName, UTF_8), encode(secondName, UTF_8), encode(password, UTF_8), encode(passwordConfirm, UTF_8)));
        else if (userService.addUser(email, password, firstName, secondName, RoleName.guest)) {
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(RoleName.guest, "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec, resp);
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(RoleName.guest, "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, resp);

            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("firstName", firstName);

            //Creating user directory on server
            new File(req.getServletContext().getAttribute("FILES_DIR") + File.separator + email).mkdirs();

            resp.sendRedirect("/");
        } else resp.sendRedirect(String.format("/auth/signup?error=%s&email=%s&firstName=%s&secondName=%s&password=%s&passwordConfirm=%s",
                encode(GetProperties.getMessageByLang("error.auth.signup.badEmail", lang), UTF_8), email,
                encode(firstName, UTF_8), encode(secondName, UTF_8), encode(password, UTF_8), encode(passwordConfirm, UTF_8)));
    }
}
