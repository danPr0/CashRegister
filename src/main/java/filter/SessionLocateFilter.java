package filter;

import util.JWTProvider;
import util.RoleName;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SessionLocateFilter implements Filter {
    private final JWTProvider jwtProvider = JWTProvider.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        if (httpRequest.getParameter("sessionLocale") != null) {
            httpRequest.getSession().setAttribute("lang", httpRequest.getParameter("sessionLocale"));
        }

        String accessToken = jwtProvider.resolveToken(httpRequest, "accessToken");
        String refreshToken = jwtProvider.resolveToken(httpRequest, "refreshToken");
        if (!jwtProvider.validateToken(accessToken) && jwtProvider.validateToken(refreshToken)) {
            RoleName role = jwtProvider.getRole(refreshToken);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(role.toString(), "accessToken"), "accessToken", httpResponse);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(role.toString(), "refreshToken"), "refreshToken", httpResponse);
        }
        chain.doFilter(request, response);
    }
}
