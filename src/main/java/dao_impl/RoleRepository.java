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
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(RoleRepository.class);

    private static final String GET_ROLE_BY_ID_QUERY = "SELECT * FROM roles WHERE %s = ?".formatted(ROLE_ID);
    private static final String GET_ROLE_BY_NAME_QUERY = "SELECT * FROM roles WHERE %s = ?".formatted(ROLE_NAME);

    private RoleRepository() {}

    public static RoleRepository getInstance() {
        if (instance == null)
            instance = new RoleRepository();
        return instance;
    }

    @Override
    public Role getRoleById(int id) {
        Role result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ROLE_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
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

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ROLE_BY_NAME_QUERY)) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {
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
