package controller.admin;

import dto.UserDTO;
import entity.User;
import garbage.RoleServiceImpl;
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

/**
 * Find and authorize user
 */
@WebServlet("/admin/authorize-user")
public class AuthorizeUser extends HttpServlet {
    private final UserServiceImpl userService = UserServiceImpl.getInstance();
    private final RoleServiceImpl roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        User user = userService.getUser(email);

        if (user == null) {
            req.setAttribute("error", GetProperties.getMessageByLang("error.admin.badEmail", Language.getLanguage(req)));
        }
        else {
            UserDTO result = new UserDTO(user.getEmail(), user.getFirstName(), user.getSecondName(), user.getRoleId().name());
            req.setAttribute("user", result);
            req.setAttribute("roles", RoleName.values());
        }

        req.getRequestDispatcher("/view/admin/authorizeUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String role = req.getParameter("role");

        User user = userService.getUser(email);

        String url = "/admin/authorize-user?email=" + email;
        if (user != null && userService.updateUserRole(user.getId(), RoleName.valueOf(role)))
            url += "&success=true";
        else url += "&error=" + GetProperties.getMessageByLang("error.general", Language.getLanguage(req));

        resp.sendRedirect(url);
    }
}
