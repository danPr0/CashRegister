package filter;

import util.token.AuthTokenProvider;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter requests to protected resources
 */
@WebFilter(servletNames = {"MainServlet", "ChangePasswordServlet"})
public class ProtectedResourcesFilter implements Filter {
    /**
     * If not authenticated, user is redirected to login page
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = AuthTokenProvider.resolveToken(httpRequest, "accessToken");
        if (!AuthTokenProvider.validateToken(accessToken))
            httpResponse.sendRedirect("/auth/login");
        else chain.doFilter(request, response);
    }
}
