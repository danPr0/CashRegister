package filter;

import entity.Role;
import util.JWTProvider;
import util.RoleName;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/")
public class MainFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = JWTProvider.resolveToken(httpRequest, "accessToken");
        if (!JWTProvider.validateToken(accessToken)) {
//            String refreshToken = jwtProvider.resolveToken(httpRequest, "refreshToken");
//            if (!jwtProvider.validateToken(refreshToken))
//                httpResponse.sendRedirect("/auth/login");
//            else {
//                RoleName role = jwtProvider.getRole(refreshToken);
//                jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(role.toString(), "accessToken"), httpResponse);
//                jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(role.toString(), "refreshToken"), httpResponse);
//                chain.doFilter(request, response);
//            }
            httpResponse.sendRedirect("/auth/login");
        }
        else chain.doFilter(request, response);
    }
}
