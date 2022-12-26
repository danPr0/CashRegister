package util.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConfirmationTokenProvider extends TokenProvider {
    public static final int tokenExpirationInSec = 86_400;

    public static String generateJwtToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpirationInSec * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
