package filter;

import util.JWTProvider;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/auth/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = JWTProvider.resolveToken(httpRequest, "accessToken");
        if (JWTProvider.validateToken(accessToken))
            httpResponse.sendRedirect("/");
        else {
//            httpRequest.getServletContext().setAttribute("username", "");
            httpRequest.getSession().removeAttribute("username");
            chain.doFilter(request, response);
        }
    }
}
