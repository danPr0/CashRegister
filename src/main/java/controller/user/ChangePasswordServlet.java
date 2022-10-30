package controller.user;

import service.UserService;
import service_impl.UserServiceImpl;
import util.GetProperties;
import util.enums.Language;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/user/changePassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String newPasswordConfirm = req.getParameter("newPasswordConfirm");
        String email = req.getSession().getAttribute("email").toString();

        String url = "/change-password";
        if (userService.authenticate(email, oldPassword) && userService.resetPassword(email, newPassword))
            url += "?success=true";
        else url += String.format("?error=%s&oldPassword=%s&newPassword=%s&newPasswordConfirm=%s",
                encode(GetProperties.getMessageByLang("error.user.changePassword", Language.getLanguage(req)), UTF_8),
                encode(oldPassword, UTF_8), encode(newPassword, UTF_8), encode(newPasswordConfirm, UTF_8));

        resp.sendRedirect(url);
    }
}
