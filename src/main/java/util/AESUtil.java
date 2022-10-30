package util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Utility for creating secret keys, which are used in password hashing and user authentication
 */
public class AESUtil {
    public static final String algorithm = "AES";

    public static SecretKey generateSecretKey() {
        SecretKey key = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            key = keyGenerator.generateKey();
        }
        catch (NoSuchAlgorithmException | NullPointerException e) {
            e.printStackTrace();
        }
        return key;
    }
}
