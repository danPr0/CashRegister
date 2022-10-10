package filter;

import dao_impl.UserRepository;
import entity.User;
import util.JWTProvider;
import util.RoleName;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebFilter(value = "/*", filterName = "main")
public class SessionLocateFilter implements Filter {
    private final JWTProvider jwtProvider = JWTProvider.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (httpRequest.getParameter("sessionLocale") != null) {
            httpRequest.getSession().setAttribute("lang", httpRequest.getParameter("sessionLocale"));
        }
        if (httpRequest.getParameter("searchType") != null) {
            httpRequest.getSession().setAttribute("searchType", httpRequest.getParameter("searchType"));
        }

        String accessToken = jwtProvider.resolveToken(httpRequest, "accessToken");
        String refreshToken = jwtProvider.resolveToken(httpRequest, "refreshToken");

        if (!jwtProvider.validateToken(accessToken) && jwtProvider.validateToken(refreshToken)) {
            User user = userRepository.getUserByUsername(String.valueOf(httpRequest.getSession().getAttribute("username")));
            if (user == null) {
                chain.doFilter(request, response);
                return;
            }
            RoleName role = user.getRole().getName();
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(role, "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec,  httpResponse);
            jwtProvider.setTokenCookie(jwtProvider.generateJwtToken(role, "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, httpResponse);
            httpResponse.sendRedirect(httpRequest.getRequestURI());
        }
        else chain.doFilter(request, response);
    }
}
