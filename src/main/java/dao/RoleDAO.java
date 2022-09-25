package dao;

import entity.Role;

public interface RoleDAO {
    Role getRoleById(int id);
    Role getRoleByName(String name);
}
