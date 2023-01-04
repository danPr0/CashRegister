//package garbage;
//
//import controller.authentication.LoginServlet;
//import controller.authentication.SignupServlet;
//import org.junit.jupiter.api.*;
//import org.mockito.Mockito;
//import util.enums.Language;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class TestAuth extends Mockito {
//    @Test
//    @Order(1)
//    public void testSignUpServlet() throws ServletException, IOException {
//        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
//        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
//        ServletContext servletContext = Mockito.mock(ServletContext.class);
//
//        Mockito.when(request.getSession().getAttribute("lang").toString()).thenReturn("en");
//        Mockito.when(request.getParameter("email")).thenReturn("danich@gmail.com");
//        Mockito.when(request.getParameter("password")).thenReturn("xxxxxxxx");
//        Mockito.when(request.getParameter("passwordConfirm")).thenReturn("xxxxxxxx");
//        Mockito.when(request.getParameter("firstName")).thenReturn("d");
//        Mockito.when(request.getParameter("secondName")).thenReturn("p");
//        Mockito.when(request.getServletContext()).thenReturn(servletContext);
//
//        new SignupServlet().doPost(request, response);
//        Mockito.verify(servletContext).setAttribute("email", "danich@gmail.com");
//        Mockito.verify(response, Mockito.times(2)).addCookie(ArgumentMatchers.any(Cookie.class));
//        Mockito.verify(response).sendRedirect("/");
//
////        new SignupServlet().doPost(request, response);
////        verify(response).sendRedirect("/auth/signup?error=Email is already taken");
////
////        when(request.getParameter("passwordConfirm")).thenReturn("ssssssss");
////        new SignupServlet().doPost(request, response);
////        verify(response).sendRedirect("/auth/signup?error=Password mismatch");
//    }
//
//    @Test()
//    @Order(2)
//    public void testLoginServlet() throws ServletException, IOException {
//        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
//        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
//        ServletContext servletContext = Mockito.mock(ServletContext.class);
//        HttpSession session = Mockito.mock(HttpSession.class);
//
//        Mockito.when(request.getParameter("email")).thenReturn("danich@gmail.com");
//        Mockito.when(request.getParameter("password")).thenReturn("xxxxxxxx");
//        Mockito.when(request.getServletContext()).thenReturn(servletContext);
//
//        new LoginServlet().doPost(request, response);
//        Mockito.verify(session).setAttribute("email", "dadanich@gmail.comn");
//        Mockito.verify(response, Mockito.times(2)).addCookie(ArgumentMatchers.any(Cookie.class));
//        Mockito.verify(response).sendRedirect("/");
//
////        when(request.getParameter("password")).thenReturn("ssssssss");
////        new LoginServlet().doPost(request, response);
////        verify(response).sendRedirect("/auth/login?error=Username or password is incorrect");
//    }
//}
