package dao_impl;

import dao.RoleDAO;
import entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConnectionFactory;

import java.sql.*;

import static util.DBFields.*;

public class RoleRepository implements RoleDAO {
    private static RoleRepository instance = null;

    private static final String GET_ROLE_BY_ID_QUERY = "SELECT * FROM roles WHERE %s = ?".formatted(ROLE_ID);
    private static final String GET_ROLE_BY_NAME_QUERY = "SELECT * FROM roles WHERE %s = ?".formatted(ROLE_NAME);

    private final Logger logger = LogManager.getLogger(RoleRepository.class);

    private RoleRepository() {
        super();
    }

    public static RoleRepository getInstance() {
        if (instance == null)
            instance = new RoleRepository();
        return instance;
    }

    private Connection getConnection() {
        return ConnectionFactory.getInstance().getConnection();
    }

    @Override
    public Role getRoleById(int id) {
        Role result = null;

        try (PreparedStatement statement = getConnection().prepareStatement(GET_ROLE_BY_ID_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = new Role(resultSet.getInt(ROLE_ID), resultSet.getString(ROLE_NAME));
                    logger.info("Role with id = " + id + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Role getRoleByName(String name) {
        Role result = null;

        try (PreparedStatement statement = getConnection().prepareStatement(GET_ROLE_BY_NAME_QUERY)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = new Role(resultSet.getInt(ROLE_ID), resultSet.getString(ROLE_NAME));
                    logger.info("Role " + name + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
