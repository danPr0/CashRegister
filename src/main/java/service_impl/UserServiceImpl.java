package service_impl;

import dao.KeyDAO;
import garbage.RoleDAO;
import dao.UserDAO;
import dao_impl.KeyDAOImpl;
import garbage.RoleDAOImpl;
import dao_impl.UserDAOImpl;
import entity.Key;
import entity.User;
import org.apache.commons.codec.binary.Base64;
import service.UserService;
import util.AESUtil;
import util.enums.RoleName;
import util.Validator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Objects;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance = null;
    private UserDAO userDAO = UserDAOImpl.getInstance();
    private KeyDAO keyDAO = KeyDAOImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null)
            instance = new UserServiceImpl();
        return instance;
    }

    @Override
    public User getUser(String email) {
        return userDAO.getEntityByEmail(email);
    }

    @Override
    public boolean addUser(String email, String password, String firstName, String secondName, RoleName role) {
        if (!(Validator.validateEmail(email) && Validator.validatePassword(password)
                && Validator.validateFirstName(firstName) && Validator.validateSecondName(secondName)))
            return false;

        SecretKey secretKey = AESUtil.generateSecretKey();
        String encodedPassword = encryptPassword(secretKey, password);
        User user = new User(0, email, encodedPassword, firstName, secondName, role);
        user = userDAO.insertEntity(user);
        if (user == null)
            return false;

        return keyDAO.insertEntity(new Key(user.getId(), new Base64().encodeToString(secretKey.getEncoded())));
    }

    @Override
    public boolean updateUserRole(int id, RoleName role) {
        User user = userDAO.getEntityById(id);
        user.setRoleId(role);
        return userDAO.updateEntity(user);
    }

    @Override
    public boolean authenticate(String email, String password) {
        User user = userDAO.getEntityByEmail(email);
        if (user == null || !Validator.validatePassword(password))
            return false;

        byte[] decodedKey = new Base64().decode(keyDAO.getEntityByUserId(user.getId()).getKey());
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey,"AES");
        return password.equals(decryptPassword(secretKey, user.getPassword()));
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        User user = userDAO.getEntityByEmail(email);
        if (user == null)
            return false;

        SecretKey secretKey = AESUtil.generateSecretKey();
        keyDAO.updateEntity(new Key(user.getId(), new Base64().encodeToString(secretKey.getEncoded())));
        user.setPassword(encryptPassword(secretKey, newPassword));

        return userDAO.updateEntity(user);
    }

    public String encryptPassword(SecretKey secretKey, String input) {
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

    public String decryptPassword(SecretKeySpec secretKey, String input) {
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

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setKeyDAO(KeyDAO keyDAO) {
        this.keyDAO = keyDAO;
    }
}
