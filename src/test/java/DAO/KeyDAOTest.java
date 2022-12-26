package DAO;

import dao.KeyDAO;
import dao.UserDAO;
import dao_impl.KeyDAOImpl;
import dao_impl.UserDAOImpl;
import entity.Key;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.db.ConnectionFactory;
import util.db.DBUtil;
import util.enums.RoleName;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static util.db.DBFields.*;
import static util.db.DBFields.USER_ROLE_ID;

public class KeyDAOTest {
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final UserDAO userDAO = UserDAOImpl.getInstance();
    private final KeyDAO keyDAO = KeyDAOImpl.getInstance();

    private final String KEY_1 = "1";
    private final String KEY_1_USER_EMAIL = "1@gamil.com";
    private int KEY_1_USER_ID = 0;

    private final String KEY_2 = "2";
    private final String KEY_2_USER_EMAIL = "2@gamil.com";
    private int KEY_2_USER_ID = 0;

    @BeforeEach
    public void insertRows() {
        PreparedStatement ps = null;
        try (Connection con = connectionFactory.getConnection()) {
            ps = con.prepareStatement("INSERT INTO users (%s, %s, %s, %s, %s, %s) VALUES (?, 'xxxxxxxx', 'dan', 'pro', ?, true)"
                    .formatted(USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, USER_SECOND_NAME, USER_ROLE_ID, USER_ENABLED), Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, KEY_1_USER_EMAIL);
            ps.setString(2, RoleName.admin.name());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                generatedKeys.next();
                KEY_1_USER_ID = generatedKeys.getInt(1);
            }

            ps = con.prepareStatement("INSERT INTO user_key (%s, %s) VALUES (?, ?)".formatted(KEY_USER_ID, KEY_KEY));
            ps.setInt(1, KEY_1_USER_ID);
            ps.setString(2, KEY_1);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtil.close(null, ps);
        }
    }

    @AfterEach
    public void deleteAllRows() {
        PreparedStatement ps = null;
        try (Connection con = connectionFactory.getConnection()) {
            ps = con.prepareStatement("DELETE FROM user_key");
            ps.execute();

            ps = con.prepareStatement("DELETE FROM users");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtil.close(null, ps);
        }
    }

    @Test
    public void testGetExistingKey() {
        Key key = keyDAO.getEntityByUserId(KEY_1_USER_ID);
        assertEquals(KEY_1_USER_ID, key.getUserId());
        assertEquals(KEY_1, key.getKey());
    }

    @Test
    public void testGetNonExistingKey() {
        assertNull(keyDAO.getEntityByUserId(KEY_2_USER_ID));
    }

    @Test
    public void testInsertNonExistingKey() {
        User user = userDAO.insertEntity(new User(KEY_2_USER_ID, KEY_2_USER_EMAIL, "xxxxxxxx", "dan", "pro", RoleName.guest, true));
        assertTrue(keyDAO.insertEntity(new Key(user.getId(), KEY_2)));
        Key insertedKey = keyDAO.getEntityByUserId(user.getId());

        assertEquals(user.getId(), insertedKey.getUserId());
        assertEquals(KEY_2, insertedKey.getKey());
    }

    @Test
    public void testInsertExistingKey() {
        Key key = new Key(KEY_1_USER_ID, KEY_1);
        assertFalse(keyDAO.insertEntity(key));
    }

    @Test
    public void testUpdateExistingKey() {
        Key key = new Key(KEY_1_USER_ID, KEY_1);
        key.setKey("hey");

        assertTrue(keyDAO.updateEntity(key));
        assertEquals(key.getKey(), keyDAO.getEntityByUserId(key.getUserId()).getKey());
    }

    @Test
    public void testUpdateNonExistingKey() {
        Key key = new Key(KEY_2_USER_ID, KEY_2);
        assertFalse(keyDAO.updateEntity(key));
    }
}
