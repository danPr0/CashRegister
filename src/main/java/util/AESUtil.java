package util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtil {
    public static final SecretKey secretKey = generateSecretKey();
    public static final IvParameterSpec ivParameterSpec = generateIv();
    public static final String algorithm = "AES/CBC/PKCS5Padding";

    private static SecretKey generateSecretKey() {
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

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
