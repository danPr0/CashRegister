package filter;

import util.JWTProvider;
import util.RoleName;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/senior-cashier/*")
public class SeniorCashierFilter implements Filter {
    private final JWTProvider jwtProvider = JWTProvider.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = jwtProvider.resolveToken(httpRequest, "accessToken");
        if (jwtProvider.validateToken(accessToken)) {
            if (!jwtProvider.getRole(accessToken).equals(RoleName.senior_cashier))
                httpResponse.sendRedirect("/");
            else chain.doFilter(request, response);
        }
        else httpResponse.sendRedirect("/auth/login");
    }
}
