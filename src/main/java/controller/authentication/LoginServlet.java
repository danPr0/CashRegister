package controller.authentication;

import entity.User;
import dao_impl.UserRepository;
import service.UserService;
import util.JWTProvider;
import util.RoleName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final JWTProvider jwtProvider = JWTProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userService.authenticate(username, password)) {
            User user = userService.getUser(username);
            req.getSession().setAttribute("username", username);

//            Cookie cookie = new Cookie("token", jwtProvider.generateJwtToken(user.getRole().getName().toString()));
//            cookie.setPath("/");
//            cookie.setHttpOnly(true);
//            resp.addCookie(cookie);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(user.getRole().getName().toString(), "accessToken"), "accessToken", resp);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(user.getRole().getName().toString(), "refreshToken"), "refreshToken", resp);

            resp.sendRedirect("/");
        }
        else {
            resp.sendRedirect("/auth/login?error=Username or password is incorrect");
        }
    }
}
