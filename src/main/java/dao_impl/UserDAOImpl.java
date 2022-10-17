package dao_impl;

import dao.UserDAO;
import entity.Role;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.db.ConnectionFactory;

import java.sql.*;

import static util.db.DBFields.*;
import static util.db.DBQueryConstants.*;

/**
 * This class represents persistence layer of {@link entity.User}
 */


public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl instance = null;
    private final RoleDAOImpl roleDAOImpl = RoleDAOImpl.getInstance();
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    private UserDAOImpl() {}

    public static UserDAOImpl getInstance() {
        if (instance == null)
            instance = new UserDAOImpl();
        return instance;
    }

    @Override
    public User getEntityByEmail(String email) {
        User result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(USER_GET_BY_EMAIL_QUERY)) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new User(resultSet.getInt(USER_ID), resultSet.getString(USER_EMAIL),
                            resultSet.getString(USER_PASSWORD), resultSet.getString(USER_FIRST_NAME),
                            resultSet.getString(USER_SECOND_NAME),
                            roleDAOImpl.getEntityById(resultSet.getInt(USER_ROLE_ID)));
                    logger.info("User with email=" + email + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            logger.error("Cannot get user by email=" + email);
        }

        return result;
    }

    @Override
    public boolean insertEntity(User user) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(USER_INSERT_QUERY)) {
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
    public boolean updateEntity(int userId, Role role) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(USER_UPDATE_ROLE_BY_ID)) {
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
