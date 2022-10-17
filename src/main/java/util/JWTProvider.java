package util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Utility for creating, resolving and validation access and refresh tokens
 */
public class JWTProvider {
    private static final String secretKey = "cash_register";
    public static final int accessTokenExpirationInSec = 900;
    public static final int refreshTokenExpirationInSec = 86_400;

    public static String generateJwtToken(RoleName role, String tokenName) {
        int tokenExpirationInSec;

        if (tokenName.equals("accessToken"))
            tokenExpirationInSec = accessTokenExpirationInSec;
        else if (tokenName.equals("refreshToken"))
            tokenExpirationInSec = refreshTokenExpirationInSec;
        else return null;

        return Jwts.builder()
                .setSubject(role.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpirationInSec * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static String resolveToken(HttpServletRequest request, String tokenName) {
        String result = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenName)) {
                    result = cookie.getValue();
                    break;
                }
            }

        return result;
    }

    public static boolean validateToken(String token) throws JwtException {
        if (token == null || token.isEmpty())
            return false;

        boolean result = true;
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (JwtException e) {
            result = false;
        }

        return result;
    }

    public static void setTokenCookie(String token, String tokenName, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static RoleName getRole(String token) {
        return RoleName.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }
}
