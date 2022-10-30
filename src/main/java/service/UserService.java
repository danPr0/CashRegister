package service;

import entity.User;
import util.enums.RoleName;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Service layer for {@link dao.UserDAO}
 */
public interface UserService {
    User getUser(String email);
    boolean addUser(String email, String password, String firstName, String secondName, RoleName role);
    boolean updateUserRole(int id, RoleName role);
    boolean authenticate(String email, String password);
    boolean resetPassword(String email, String newPassword);
    String encryptPassword(SecretKey secretKey, String input);
    String decryptPassword(SecretKeySpec secretKey, String input);
}
