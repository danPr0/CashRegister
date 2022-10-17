package service;

import entity.Role;

public interface RoleService {
    Role getRole(int id);
    Role getRole(String name);
}
