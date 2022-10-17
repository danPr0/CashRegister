package dao_impl;

import dao.KeyDAO;
import entity.Key;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.db.DBFields.*;
import static util.db.DBQueryConstants.*;

public class KeyDAOImpl implements KeyDAO {
    private static KeyDAOImpl instance = null;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(KeyDAOImpl.class);

    private KeyDAOImpl() {}

    public static KeyDAOImpl getInstance() {
        if (instance == null)
            instance = new KeyDAOImpl();
        return instance;
    }

    @Override
    public boolean insertEntity(Key key) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(KEY_INSERT_QUERY)) {
            ps.setInt(1, key.getUser_id());
            ps.setString(2, key.getKey());
            ps.execute();
            logger.info("Key was added to user with id=" + key.getUser_id());
        }
        catch (SQLException e) {
            logger.error("Cannot insert secret key with userId=" + key.getUser_id());
            result = false;
        }

        return result;
    }

    @Override
    public Key getEntityByUserId(int userId) {
        Key result = null;

        try (Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(KEY_GET_BY_USER_ID)) {
            ps.setInt(1, userId);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new Key(userId, resultSet.getString(KEY_KEY));
                    logger.info("Key to user with id=" + userId + "was retrieved");
                }
            }
        }
        catch (SQLException e) {
            logger.error("Cannot get secret key for user with id=" + userId);
        }

        return result;
    }
}
