package DAO;

import dao.UserDAO;
import dao_impl.UserDAOImpl;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.db.ConnectionFactory;
import util.enums.RoleName;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static util.db.DBFields.*;

public class UserDAOTest {
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final UserDAO userDAO = UserDAOImpl.getInstance();

    private int USER_1_ID = 0;
    private final String USER_1_EMAIL = "1@mail.com";
    private final RoleName USER_1_ROLE = RoleName.cashier;

    private int USER_2_ID = 0;
    private final String USER_2_EMAIL = "2@mail.com";
    private final RoleName USER_2_ROLE = RoleName.commodity_expert;

    private final int USER_3_ID = 0;
    private final String USER_3_EMAIL = "3@mail.com";
    private final RoleName USER_3_ROLE = RoleName.senior_cashier;

    @BeforeEach
    public void insertRows() {
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement psInsertUser = con.prepareStatement("INSERT INTO users (%s, %s, %s, %s, %s, %s) VALUES (?, 'xxxxxxxx', 'dan', 'pro', ?, true)"
                     .formatted(USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, USER_SECOND_NAME, USER_ROLE_ID, USER_ENABLED), Statement.RETURN_GENERATED_KEYS)) {
            psInsertUser.setString(1, USER_1_EMAIL);
            psInsertUser.setString(2, USER_1_ROLE.name());
            psInsertUser.executeUpdate();
            try (ResultSet generatedKeys = psInsertUser.getGeneratedKeys()) {
                generatedKeys.next();
                USER_1_ID = generatedKeys.getInt(1);
            }

            psInsertUser.setString(1, USER_2_EMAIL);
            psInsertUser.setString(2, USER_2_ROLE.name());
            psInsertUser.executeUpdate();
            try (ResultSet generatedKeys = psInsertUser.getGeneratedKeys()) {
                generatedKeys.next();
                USER_2_ID = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void deleteAllRows() {
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement psDeleteUsers = con.prepareStatement("DELETE FROM users")) {
            psDeleteUsers.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestFindByEmailExistingUser() {
        User user = userDAO.getEntityByEmail(USER_1_EMAIL);

        assertEquals(user.getId(), USER_1_ID);
        assertEquals(user.getEmail(), USER_1_EMAIL);
        assertEquals(user.getRoleId(), USER_1_ROLE);
    }

    @Test
    public void TestFindByEmailNonExistingUser() {
        assertNull(userDAO.getEntityByEmail(USER_3_EMAIL));
    }

    @Test
    public void TestFindByIdExistingUser() {
        User user = userDAO.getEntityById(userDAO.getEntityByEmail(USER_1_EMAIL).getId());
        assertNotNull(user);

        assertEquals(USER_1_ID, user.getId());
        assertEquals(USER_1_EMAIL, user.getEmail());
        assertEquals(USER_1_ROLE, user.getRoleId());
    }

    @Test
    public void TestFindByIdNonExistingUser() {
        assertNull(userDAO.getEntityById(USER_3_ID));
    }

    @Test
    public void TestInsertNonExistingUser() {
        User user = new User(USER_3_ID, USER_3_EMAIL, "p", "d", "p", USER_3_ROLE, true);
        assertNotNull(userDAO.insertEntity(user));
        User insertedUser = userDAO.getEntityByEmail(USER_3_EMAIL);

        assertNotNull(insertedUser);
        assertEquals(user.getEmail(), insertedUser.getEmail());
        assertEquals(user.getRoleId(), insertedUser.getRoleId());
    }

    @Test
    public void TestInsertExistingUser() {
        User user = new User(USER_2_ID, USER_2_EMAIL, "p", "d", "p", USER_2_ROLE, true);
        assertNull(userDAO.insertEntity(user));
    }

    @Test
    public void TestUpdateExistingUser() {
        User user = userDAO.getEntityByEmail(USER_1_EMAIL);
        user.setRoleId(RoleName.commodity_expert);
        assertTrue(userDAO.updateEntity(user));

        User updatedUser = userDAO.getEntityByEmail(USER_1_EMAIL);
        assertEquals(RoleName.commodity_expert, updatedUser.getRoleId());
    }

    @Test
    public void TestUpdateNonExistingUser() {
        assertFalse(userDAO.updateEntity(User.builder().id(0).roleId(RoleName.guest).enabled(true).build()));
    }
}
