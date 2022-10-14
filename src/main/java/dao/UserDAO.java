package dao;

import entity.Role;
import entity.User;

public interface UserDAO {
    User getUserByEmail(String username);
    boolean insertUser(User user);
    boolean updateUser(int userId, Role role);
}
