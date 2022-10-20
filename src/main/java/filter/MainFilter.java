package filter;

import util.JWTProvider;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter requests to main page
 */
@WebFilter("/")
public class MainFilter implements Filter {
    /**
     * If not authenticated, user is redirected to login page
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = JWTProvider.resolveToken(httpRequest, "accessToken");
        if (!JWTProvider.validateToken(accessToken))
            httpResponse.sendRedirect("/auth/login");
        else chain.doFilter(request, response);
    }
}
