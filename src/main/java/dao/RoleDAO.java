package dao;

import entity.Role;

/**
 * DAO layer for "roles" table
 */
public interface RoleDAO {
    Role getEntityById(int id);
    Role getEntityByName(String name);
}
