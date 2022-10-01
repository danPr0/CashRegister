import controller.authentication.LoginServlet;
import controller.authentication.SignupServlet;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAuth extends Mockito {
    @Test
    @Order(1)
    public void testSignUpServlet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletContext servletContext = mock(ServletContext.class);

        when(request.getParameter("username")).thenReturn("dan");
        when(request.getParameter("password")).thenReturn("xxxxxxxx");
        when(request.getParameter("passwordConfirm")).thenReturn("xxxxxxxx");
        when(request.getParameter("firstName")).thenReturn("d");
        when(request.getParameter("secondName")).thenReturn("p");
        when(request.getServletContext()).thenReturn(servletContext);

        new SignupServlet().doPost(request, response);
        verify(servletContext).setAttribute("username", "dan");
        verify(response, times(2)).addCookie(any(Cookie.class));
        verify(response).sendRedirect("/");

        new SignupServlet().doPost(request, response);
        verify(response).sendRedirect("/auth/signup?error=Username is already taken");

        when(request.getParameter("passwordConfirm")).thenReturn("ssssssss");
        new SignupServlet().doPost(request, response);
        verify(response).sendRedirect("/auth/signup?error=Password mismatch");
    }

    @Test()
    @Order(2)
    public void testLoginServlet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletContext servletContext = mock(ServletContext.class);

        when(request.getParameter("username")).thenReturn("dan");
        when(request.getParameter("password")).thenReturn("xxxxxxxx");
        when(request.getServletContext()).thenReturn(servletContext);

        new LoginServlet().doPost(request, response);
        verify(servletContext).setAttribute("username", "dan");
        verify(response, times(2)).addCookie(any(Cookie.class));
        verify(response).sendRedirect("/");

        when(request.getParameter("password")).thenReturn("ssssssss");
        new LoginServlet().doPost(request, response);
        verify(response).sendRedirect("/auth/login?error=Username or password is incorrect");
    }
}
