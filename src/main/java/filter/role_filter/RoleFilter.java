package filter.role_filter;

import garbage.Role;
import util.enums.RoleName;
import util.token.AuthTokenProvider;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoleFilter implements Filter {
    private final RoleName role;

    protected RoleFilter(RoleName role) {
        this.role = role;
    }

    /**
     * If not authenticated, user is redirected to login page
     * If role mismatch, user is redirected to main page
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = AuthTokenProvider.resolveToken(httpRequest, "accessToken");
        if (AuthTokenProvider.validateToken(accessToken)) {
            if (!AuthTokenProvider.getRole(accessToken).equals(role))
                httpResponse.sendRedirect("/");
            else chain.doFilter(request, response);
        }
        else httpResponse.sendRedirect("/auth/login");
    }
}
