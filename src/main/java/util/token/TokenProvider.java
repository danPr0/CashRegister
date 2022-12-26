package util.token;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class TokenProvider {
    protected static final String secretKey = "cash_register";

    public static boolean validateToken(String token) {
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
}
