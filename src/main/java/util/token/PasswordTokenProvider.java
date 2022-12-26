package util.token;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import util.enums.RoleName;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PasswordTokenProvider extends TokenProvider {
    public static final int tokenExpirationInSec = 86_400;

    public static String generateJwtToken(String email, String password) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("password", password);

        return Jwts.builder()
                .setSubject(email)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpirationInSec * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public static String getPassword(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("password").toString();
    }
}
