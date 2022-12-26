package controller.admin;

import dto.UserDTO;
import entity.User;
import garbage.RoleServiceImpl;
import lombok.SneakyThrows;
import org.apache.hc.core5.net.URIBuilder;
import service.UserService;
import service_impl.UserServiceImpl;
import util.GetProperties;
import util.enums.Language;
import util.enums.RoleName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Find and authorize user
 */
@WebServlet("/admin/authorize-user")
public class AuthorizeUser extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        User user = userService.getUser(email);

        if (user == null || !user.isEnabled()) {
            req.setAttribute("error", GetProperties.getMessageByLang("error.admin.badEmail", Language.getLanguage(req)));
        }
        else {
            UserDTO result = new UserDTO(user.getEmail(), user.getFirstName(), user.getSecondName(), user.getRoleId().name());
            req.setAttribute("user", result);
            req.setAttribute("roles", RoleName.getRoles(Language.getLanguage(req)));
        }

        req.getRequestDispatcher("/view/admin/authorizeUser.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        RoleName role = RoleName.valueOf(req.getParameter("role"));

        User user = userService.getUser(email);
        user.setRoleId(role);

        URIBuilder uriBuilder = new URIBuilder("/admin/authorize-user", UTF_8);
        uriBuilder.addParameter("email", email);

        if (userService.updateUser(user))
            uriBuilder.addParameter("success", "true");
        else uriBuilder.addParameter("error", GetProperties.getMessageByLang("error.general", Language.getLanguage(req)));

        resp.sendRedirect(uriBuilder.build().toString());
    }
}
