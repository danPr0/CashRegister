package dao_impl;

import dao.UserDAO;
import entity.Role;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConnectionFactory;

import java.sql.*;

import static util.DBFields.*;

/**
 * This class represents persistence layer of {@link entity.User}
 */


public class UserRepository implements UserDAO {
    private static UserRepository instance = null;
    private final RoleRepository roleRepository = RoleRepository.getInstance();
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(UserRepository.class);

    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE %s = ?".formatted(USER_EMAIL);
    private static final String INSERT_USER_QUERY = "INSERT INTO users (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
            .formatted(USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, USER_SECOND_NAME, USER_ROLE_ID);
    private static final String UPDATE_USER_ROLE_BY_ID = "UPDATE users SET %s = ? WHERE %s = ?".formatted(USER_ROLE_ID, USER_ID);

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    @Override
    public User getUserByEmail(String email) {
        User result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_USER_BY_EMAIL_QUERY)) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new User(resultSet.getInt(USER_ID), resultSet.getString(USER_EMAIL),
                            resultSet.getString(USER_PASSWORD), resultSet.getString(USER_FIRST_NAME),
                            resultSet.getString(USER_SECOND_NAME),
                            roleRepository.getRoleById(resultSet.getInt(USER_ROLE_ID)));
                    logger.info("User with email=" + email + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            logger.error("Cannot get user by email=" + email);
        }

        return result;
    }

    @Override
    public boolean insertUser(User user) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_USER_QUERY)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getSecondName());
            ps.setInt(5, user.getRole().getId());
            ps.execute();
            logger.info("User " + user.getEmail() + " was successfully added");
        } catch (SQLException e) {
            logger.error("Cannot insert user with email=" + user.getEmail());
            result = false;
        }

        return result;
    }

    @Override
    public boolean updateUser(int userId, Role role) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_USER_ROLE_BY_ID)) {
            ps.setInt(1, role.getId());
            ps.setInt(2, userId);
            ps.execute();
            logger.info("User with id=" + userId + " was successfully added");
        } catch (SQLException e) {
            logger.error("Cannot insert user with id=" + userId);
            result = false;
        }

        return result;
    }
}
