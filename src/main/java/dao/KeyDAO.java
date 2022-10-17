package dao;

import entity.Key;

/**
 * DAO layer for "user_key" table
 */
public interface KeyDAO {
    boolean insertEntity(Key key);
    Key getEntityByUserId(int userId);
}
