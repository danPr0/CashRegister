package filter;

import entity.User;
import garbage.RoleService;
import service.UserService;
import garbage.RoleServiceImpl;
import service_impl.UserServiceImpl;
import util.JWTProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The first filter in chain. Filters all requests and renew access token if refresh one is valid
 */
@WebFilter(value = "/*", filterName = "refreshAccessToken")
public class RefreshAccessTokenFilter extends HttpFilter {
    private final UserService userService = UserServiceImpl.getInstance();
    private final RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

        String accessToken = JWTProvider.resolveToken(httpRequest, "accessToken");
        String refreshToken = JWTProvider.resolveToken(httpRequest, "refreshToken");

        if (!JWTProvider.validateToken(accessToken) && JWTProvider.validateToken(refreshToken)) {
            User user = userService.getUser(String.valueOf(httpRequest.getSession().getAttribute("email")));
            if (user == null) {
                chain.doFilter(req, res);
                return;
            }

            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(user.getRoleId(), "accessToken"),
                    "accessToken", JWTProvider.accessTokenExpirationInSec,  httpResponse);
            JWTProvider.setTokenCookie(JWTProvider.generateJwtToken(user.getRoleId(), "refreshToken"),
                    "refreshToken", JWTProvider.refreshTokenExpirationInSec, httpResponse);
            httpResponse.sendRedirect(httpRequest.getRequestURI());
        }
        else chain.doFilter(req, res);
    }
}

