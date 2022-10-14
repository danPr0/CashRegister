package service;

import entity.Role;
import entity.User;
import util.RoleName;

public interface UserServiceInterface {
    User getUser(String email);
    boolean insertUser(String email, String password, String firstName, String secondName, RoleName role);
    boolean updateUser(int id, Role role);
    boolean authenticate(String email, String password);
}
