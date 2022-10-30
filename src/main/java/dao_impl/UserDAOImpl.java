package dao_impl;

import dao.UserDAO;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.db.ConnectionFactory;
import util.enums.RoleName;

import java.sql.*;

import static util.db.DBFields.*;
import static util.db.DBQueryConstants.*;

/**
 * This class represents persistence layer of {@link entity.User}
 */


public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl instance = null;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    private UserDAOImpl() {
    }

    public static UserDAOImpl getInstance() {
        if (instance == null)
            instance = new UserDAOImpl();
        return instance;
    }

    @Override
    public User getEntityById(int id) {
        User result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(USERS_GET_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                result = new User(id, resultSet.getString(USER_EMAIL),
                        resultSet.getString(USER_PASSWORD), resultSet.getString(USER_FIRST_NAME),
                        resultSet.getString(USER_SECOND_NAME),
                        RoleName.valueOf(resultSet.getString(USER_ROLE_ID)));
                logger.info("User with id=" + id + " was successfully retrieved");
            }
        } catch (SQLException e) {
            logger.error("Cannot get user by id=" + id, e.getCause());
        }

        return result;
    }

    @Override
    public User getEntityByEmail(String email) {
        User result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(USERS_GET_BY_EMAIL_QUERY)) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                result = new User(resultSet.getInt(USER_ID), email,
                        resultSet.getString(USER_PASSWORD), resultSet.getString(USER_FIRST_NAME),
                        resultSet.getString(USER_SECOND_NAME),
                        RoleName.valueOf(resultSet.getString(USER_ROLE_ID)));
                logger.info("User with email=" + email + " was successfully retrieved");
            }
        } catch (SQLException e) {
            logger.error("Cannot get user by email=" + email, e.getCause());
        }

        return result;
    }

    @Override
    public User insertEntity(User user) {
        User result = user;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(USERS_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getSecondName());
            ps.setString(5, user.getRoleId().name());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                generatedKeys.next();
                result.setId(generatedKeys.getInt(1));
            }

            logger.info("User " + user.getEmail() + " was successfully added");
        } catch (SQLException e) {
            logger.error("Cannot insert user with email=" + user.getEmail(), e);
            result = null;
        }

        return result;
    }

    @Override
    public boolean updateEntity(User user) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(USERS_UPDATE_QUERY)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getRoleId().name());
            ps.setInt(3, user.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("Updating user failed, no rows affected");

            logger.info("User with id=" + user.getId() + " was successfully updated");
        } catch (SQLException e) {
            logger.error("Cannot insert user with id=" + user.getId(), e);
            result = false;
        }

        return result;
    }
}
