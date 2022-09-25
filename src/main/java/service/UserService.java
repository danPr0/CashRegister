package service;

import dao_impl.RoleRepository;
import dao_impl.UserRepository;
import entity.User;
import org.apache.commons.codec.binary.Base64;
import util.AESUtil;
import util.RoleName;
import util.Validator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class UserService {
    private static UserService instance = null;
    private final UserRepository userRepository = UserRepository.getInstance();
    RoleRepository roleRepository = RoleRepository.getInstance();
    private SecretKeyFactory secretKeyFactory = null;

    private UserService() {
        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
        return user != null && Validator.validatePassword(password) && password.equals(decryptPassword(user.getPassword()));
    }

    public boolean insertUser(String username, String password, String firstName, String secondName, RoleName role) {
        if (!(Validator.validateUsername(username) && Validator.validatePassword(password)
                && Validator.validateFirstName(firstName) && Validator.validateSecondName(secondName)))
            return false;

        //new Base64().encode(password.getBytes(StandardCharsets.UTF_8))
        String encodedPassword = encryptPassword(password);
        User user = new User(0, username, encodedPassword, firstName, secondName, roleRepository.getRoleByName(role.toString()));
        return userRepository.insertUser(user);
    }

    private String encryptPassword(String input) {
        byte[] cipherText = null;

        try {
            Cipher cipher = Cipher.getInstance(AESUtil.algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, AESUtil.secretKey, AESUtil.ivParameterSpec);
            cipherText = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return new String(Objects.requireNonNull(new Base64().encode(cipherText)));
    }

    private String decryptPassword(String input) {
        byte[] plainText = null;

        try {
            Cipher cipher = Cipher.getInstance(AESUtil.algorithm);
            cipher.init(Cipher.DECRYPT_MODE, AESUtil.secretKey, AESUtil.ivParameterSpec);
            plainText = cipher.doFinal(new Base64().decode(input));
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return new String(Objects.requireNonNull(plainText));
    }
}
