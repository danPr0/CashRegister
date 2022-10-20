package dao;

import entity.User;
import util.enums.RoleName;

/**
 * DAO layer for "users" table
 */
public interface UserDAO {
    User getEntityById(int id);
    User getEntityByEmail(String username);
    boolean insertEntity(User user);
    boolean updateEntity(int userId, RoleName role);
}
