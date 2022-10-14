package filter;

import dao_impl.UserRepository;
import entity.User;
import util.JWTProvider;
import util.RoleName;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = "/*", filterName = "refreshAccessToken")
public class RefreshAccessTokenFilter extends HttpFilter {
    private final UserRepository userRepository = UserRepository.getInstance();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

        String accessToken = JWTProvider.resolveToken(httpRequest, "accessToken");
        String refreshToken = JWTProvider.resolveToken(httpRequest, "refreshToken");

        if (!JWTProvider.validateToken(accessToken) && JWTProvider.validateToken(refreshToken)) {
            User user = userRepository.getUserByEmail(String.valueOf(httpRequest.getSession().getAttribute("email")));
            if (user == null) {
                chain.doFilter(req, res);
                return;
            }
            RoleName role = user.getRole().getName();
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(role, "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec,  httpResponse);
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(role, "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, httpResponse);
            httpResponse.sendRedirect(httpRequest.getRequestURI());
        }
        else chain.doFilter(req, res);
    }
}

