package controller.admin;

import dao.UserDAO;
import dao_impl.RoleRepository;
import dto.UserDTO;
import entity.User;
import service_impl.UserServiceImpl;
import util.RoleName;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/authorize-user")
public class AuthorizeUser extends HttpServlet {
    private final UserServiceImpl userService = UserServiceImpl.getInstance();
    private final RoleRepository roleRepository = RoleRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        User user = userService.getUser(email);

        if (user == null) {
            request.setAttribute("error", "true");
        }
        else {
            UserDTO result = new UserDTO(user.getEmail(), user.getFirstName(), user.getSecondName(), user.getRole().getName().toString());
            request.setAttribute("user", result);
            request.setAttribute("roles", RoleName.values());
        }

        request.getRequestDispatcher("/view/admin/authorizeUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        User user = userService.getUser(email);

        String url = "/admin/authorize-user?email=" + email;
        if (user != null && userService.updateUser(user.getId(), roleRepository.getRoleByName(role)))
            url += "&success=true";
        else url += "&error=true";

        response.sendRedirect(url);
    }
}
