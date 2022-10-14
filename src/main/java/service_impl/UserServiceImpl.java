package service_impl;

import dao_impl.KeyRepository;
import dao_impl.RoleRepository;
import dao_impl.UserRepository;
import entity.Key;
import entity.Role;
import entity.User;
import org.apache.commons.codec.binary.Base64;
import service.UserServiceInterface;
import util.AESUtil;
import util.RoleName;
import util.Validator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Objects;

/**
 * This class represents business logic of operation with {@link entity.User}
 */

public class UserServiceImpl implements UserServiceInterface {
    private static UserServiceImpl instance = null;
    private final UserRepository userRepository = UserRepository.getInstance();
    RoleRepository roleRepository = RoleRepository.getInstance();
    private final KeyRepository keyRepository = KeyRepository.getInstance();

    private UserServiceImpl() {

    }

    public static UserServiceImpl getInstance() {
        if (instance == null)
            instance = new UserServiceImpl();
        return instance;
    }

    @Override
    public User getUser(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public boolean insertUser(String email, String password, String firstName, String secondName, RoleName role) {
        System.out.println(Validator.validateEmail(email));
        System.out.println(Validator.validatePassword(password));
        System.out.println(Validator.validateFirstName(firstName));
        if (!(Validator.validateEmail(email) && Validator.validatePassword(password)
                && Validator.validateFirstName(firstName) && Validator.validateSecondName(secondName)))
            return false;



        SecretKey secretKey = AESUtil.generateSecretKey();
        String encodedPassword = encryptPassword(secretKey, password);
        User user = new User(0, email, encodedPassword, firstName, secondName, roleRepository.getRoleByName(role.toString()));
        if (!userRepository.insertUser(user))
            return false;

        return keyRepository.insertKey(new Key(userRepository.getUserByEmail(email).getId(), new Base64().encodeToString(secretKey.getEncoded())));
    }

    @Override
    public boolean updateUser(int id, Role role) {
        return userRepository.updateUser(id, role);
    }

    @Override
    public boolean authenticate(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user == null || !Validator.validatePassword(password))
            return false;

        byte[] decodedKey = new Base64().decode(keyRepository.getKeyByUserId(user.getId()).getKey());
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey,"AES");
        return password.equals(decryptPassword(secretKey, user.getPassword()));
    }

    protected String encryptPassword(SecretKey secretKey, String input) {
        byte[] cipherText = null;

        try {
            Cipher cipher = Cipher.getInstance(AESUtil.algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, cipher.getParameters());
            cipherText = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return new String(Objects.requireNonNull(new Base64().encode(cipherText)), StandardCharsets.UTF_8);
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

        return new String(Objects.requireNonNull(plainText), StandardCharsets.UTF_8);
    }
}
