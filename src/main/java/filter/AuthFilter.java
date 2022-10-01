package filter;

import util.JWTProvider;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/auth/*")
public class AuthFilter implements Filter {
    private final JWTProvider jwtProvider = JWTProvider.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = jwtProvider.resolveToken(httpRequest, "accessToken");
        if (jwtProvider.validateToken(accessToken))
            httpResponse.sendRedirect("/");
        else {
            httpRequest.getServletContext().setAttribute("username", "");
            chain.doFilter(request, response);
        }
    }
}
