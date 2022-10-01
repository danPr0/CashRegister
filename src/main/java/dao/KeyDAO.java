package dao;

import entity.Key;

public interface KeyDAO {
    boolean insertKey(Key key);
    Key getKeyByUserId(int userId);
}
