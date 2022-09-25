package util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JWTProvider {
    private static JWTProvider instance = null;
    private static final String secretKey = "cash_register";
    private static final int accessTokenExpiration = 900_000;
    private static final int refreshTokenExpiration = 86_400_000;

    private JWTProvider() {
    }

    public static JWTProvider getInstance() {
        if (instance == null)
            instance = new JWTProvider();
        return instance;
    }

    public String generateJwtToken(String role, String tokenName) {
        int tokenExpiration;

        if (tokenName.equals("accessToken"))
            tokenExpiration = accessTokenExpiration;
        else if (tokenName.equals("refreshToken"))
            tokenExpiration = refreshTokenExpiration;
        else return null;

        String token = Jwts.builder()
                .setSubject(role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        System.out.println(token);
        return token;
    }

    public String resolveToken(HttpServletRequest request, String tokenName) {
//        String headerValue = request.getHeader("Authorization");
//        if (headerValue == null)
//            return null;
//
//        return headerValue.substring(headerValue.lastIndexOf("Bearer ") + 7);
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

    public boolean validateToken(String token) throws JwtException {
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

    public void setTokenCookie(String token, String tokenName, HttpServletResponse response) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public RoleName getRole(String token) {
        return RoleName.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }
}
