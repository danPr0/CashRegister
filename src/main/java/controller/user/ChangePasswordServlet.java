package controller.user;

import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
import service.UserService;
import service_impl.UserServiceImpl;
import util.GetProperties;
import util.enums.Language;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet(name = "ChangePasswordServlet", value = "/user/change-password")
public class ChangePasswordServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/user/changePassword.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String newPasswordConfirm = req.getParameter("newPasswordConfirm");
        String email = req.getSession().getAttribute("email").toString();

        URIBuilder uriBuilder = new URIBuilder("/change-password", UTF_8);
        if (userService.authenticate(email, oldPassword) && userService.resetPassword(email, newPassword))
            uriBuilder.addParameter("success", "true");
        else {
            uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.user.changePassword", Language.getLanguage(req)));
            uriBuilder.addParameter("oldPassword", oldPassword);
            uriBuilder.addParameter("newPassword", newPassword);
            uriBuilder.addParameter("newPasswordConfirm", newPasswordConfirm);
        }

        resp.sendRedirect(uriBuilder.build().toString());
    }
}
