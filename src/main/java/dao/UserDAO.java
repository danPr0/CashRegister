package dao;

import entity.Role;
import entity.User;

/**
 * DAO layer for "users" table
 */
public interface UserDAO {
    User getEntityByEmail(String username);
    boolean insertEntity(User user);
    boolean updateEntity(int userId, Role role);
}
