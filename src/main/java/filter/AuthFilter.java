package filter;

import util.token.AuthTokenProvider;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter all authentication requests
 */
@WebFilter("/auth/*")
public class AuthFilter implements Filter {
    /**
     * If already authenticated redirect to main page
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = AuthTokenProvider.resolveToken(httpRequest, "accessToken");
        if (AuthTokenProvider.validateToken(accessToken))
            httpResponse.sendRedirect("/");
        else {
            httpRequest.getSession().removeAttribute("email");
            httpRequest.getSession().removeAttribute("firstName");
            chain.doFilter(request, response);
        }
    }
}
