package dao;

import entity.User;

public interface UserDAO {
    User getUserByUsername(String username);
    boolean insertUser(User user);
}
