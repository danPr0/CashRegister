package service;

import entity.User;
import util.RoleName;

public interface UserServiceInterface {
    User getUser(String username);
    boolean insertUser(String username, String password, String firstName, String secondName, RoleName role);
    boolean authenticate(String username, String password);
}
