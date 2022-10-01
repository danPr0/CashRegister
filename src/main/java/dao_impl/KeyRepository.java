package dao_impl;

import dao.KeyDAO;
import entity.Key;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.DBFields.*;

public class KeyRepository implements KeyDAO {
    private static KeyRepository instance = null;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(KeyRepository.class);

    private static final String INSERT_KEY_QUERY = "INSERT INTO user_key (%s, %s) VALUES (?, ?)".formatted(KEY_USER_ID, KEY_KEY);
    private static final String GET_KEY_BY_USER_ID = "SELECT * FROM user_key WHERE %s = ?".formatted(KEY_USER_ID);

    private KeyRepository() {}

    public static KeyRepository getInstance() {
        if (instance == null)
            instance = new KeyRepository();
        return instance;
    }

    @Override
    public boolean insertKey(Key key) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_KEY_QUERY)) {
            ps.setInt(1, key.getUser_id());
            ps.setString(2, key.getKey());
            ps.execute();
            logger.info("Key was added to user with id=" + key.getUser_id());
        }
        catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public Key getKeyByUserId(int userId) {
        Key result = null;

        try (Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(GET_KEY_BY_USER_ID)) {
            ps.setInt(1, userId);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new Key(userId, resultSet.getString(KEY_KEY));
                    logger.info("Key to user with id=" + userId + "was retrieved");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
