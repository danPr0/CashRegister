package filter;

import util.JWTProvider;
import util.RoleName;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/cashier/*")
public class CashierFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = JWTProvider.resolveToken(httpRequest, "accessToken");
        if (JWTProvider.validateToken(accessToken)) {
            if (!JWTProvider.getRole(accessToken).equals(RoleName.cashier))
                httpResponse.sendRedirect("/");
            else chain.doFilter(request, response);
        }
        else httpResponse.sendRedirect("/auth/login");
    }
}
