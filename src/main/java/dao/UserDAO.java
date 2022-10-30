package dao;

import entity.User;
import util.enums.RoleName;

/**
 * DAO layer for "users" table
 */
public interface UserDAO {
    User getEntityById(int id);
    User getEntityByEmail(String email);
    User insertEntity(User user);
    boolean updateEntity(User user);
}
