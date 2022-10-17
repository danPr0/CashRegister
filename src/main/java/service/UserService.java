package service;

import entity.Role;
import entity.User;
import util.RoleName;

/**
 * Service layer for {@link dao.UserDAO}
 */
public interface UserService {
    User getUser(String email);
    boolean addUser(String email, String password, String firstName, String secondName, RoleName role);
    boolean updateUserRole(int id, Role role);
    boolean authenticate(String email, String password);
}
