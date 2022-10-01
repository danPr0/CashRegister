package dao_impl;

import dao.UserDAO;
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

    private static final String GET_USER_BY_NAME_QUERY = "SELECT * FROM users WHERE %s = ?".formatted(USER_LOGIN);
    private static final String INSERT_USER_QUERY = "INSERT INTO users (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
            .formatted(USER_LOGIN, USER_PASSWORD, USER_FIRST_NAME, USER_SECOND_NAME, USER_ROLE_ID);

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    @Override
    public User getUserByUsername(String username) {
        User result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_USER_BY_NAME_QUERY)) {
            ps.setString(1, username);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new User(resultSet.getInt(USER_ID), resultSet.getString(USER_LOGIN),
                            resultSet.getString(USER_PASSWORD), resultSet.getString(USER_FIRST_NAME),
                            resultSet.getString(USER_SECOND_NAME),
                            roleRepository.getRoleById(resultSet.getInt(USER_ROLE_ID)));
                    logger.info("User " + username + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean insertUser(User user) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_USER_QUERY)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getSecondName());
            ps.setInt(5, user.getRole().getId());
            ps.execute();
            logger.info("User " + user.getUsername() + " was successfully added");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
