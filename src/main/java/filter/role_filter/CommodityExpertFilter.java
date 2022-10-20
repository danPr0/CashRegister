package filter.role_filter;

import util.JWTProvider;
import util.enums.RoleName;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter all commodity expert requests
 */
@WebFilter("/commodity-expert/*")
public class CommodityExpertFilter implements Filter {
    /**
     * If not authenticated, user is redirected to login page
     * If role mismatch, user is redirected to main page
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = JWTProvider.resolveToken(httpRequest, "accessToken");
        if (JWTProvider.validateToken(accessToken)) {
            if (!JWTProvider.getRole(accessToken).equals(RoleName.commodity_expert))
                httpResponse.sendRedirect("/");
            else chain.doFilter(request, response);
        }
        else httpResponse.sendRedirect("/auth/login");
    }
}
