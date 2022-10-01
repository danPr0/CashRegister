package service;

import dao_impl.KeyRepository;
import dao_impl.RoleRepository;
import dao_impl.UserRepository;
import entity.Key;
import entity.User;
import org.apache.commons.codec.binary.Base64;
import util.AESUtil;
import util.RoleName;
import util.Validator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents business logic of operation with {@link entity.User}
 */

public class UserService {
    private static UserService instance = null;
    private final UserRepository userRepository = UserRepository.getInstance();
    RoleRepository roleRepository = RoleRepository.getInstance();
    private final KeyRepository keyRepository = KeyRepository.getInstance();

    private UserService() {
//        try {
//            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    public User getUser(String username) {
        return userRepository.getUserByUsername(username);
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user == null || !Validator.validatePassword(password))
            return false;

        byte[] decodedKey = new Base64().decode(keyRepository.getKeyByUserId(user.getId()).getKey());
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey,"AES");
        return password.equals(decryptPassword(secretKey, user.getPassword()));
    }

    public boolean insertUser(String username, String password, String firstName, String secondName, RoleName role) {
        if (!(Validator.validateUsername(username) && Validator.validatePassword(password)
                && Validator.validateFirstName(firstName) && Validator.validateSecondName(secondName)))
            return false;

        SecretKey secretKey = AESUtil.generateSecretKey();
        String encodedPassword = encryptPassword(secretKey, password);
        User user = new User(0, username, encodedPassword, firstName, secondName, roleRepository.getRoleByName(role.toString()));
        if (!userRepository.insertUser(user))
            return false;

        return keyRepository.insertKey(new Key(userRepository.getUserByUsername(username).getId(), new Base64().encodeToString(secretKey.getEncoded())));
    }

    private String encryptPassword(SecretKey secretKey, String input) {
        byte[] cipherText = null;

        try {
            Cipher cipher = Cipher.getInstance(AESUtil.algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, cipher.getParameters());
            cipherText = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return new String(Objects.requireNonNull(new Base64().encode(cipherText)));
    }

    private String decryptPassword(SecretKeySpec secretKey, String input) {
        byte[] plainText = null;

        try {
            Cipher cipher = Cipher.getInstance(AESUtil.algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            plainText = cipher.doFinal(new Base64().decode(input));
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return new String(Objects.requireNonNull(plainText));
    }
}
