package garbage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.db.ConnectionFactory;
import util.enums.RoleName;

import java.sql.*;

import static util.db.DBFields.*;
import static util.db.DBQueryConstants.*;

public class RoleDAOImpl implements RoleDAO {
    private static RoleDAOImpl instance = null;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(RoleDAOImpl.class);

    private RoleDAOImpl() {
    }

    public static RoleDAOImpl getInstance() {
        if (instance == null)
            instance = new RoleDAOImpl();
        return instance;
    }

    @Override
    public Role getEntityById(int id) {
        Role result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(ROLES_GET_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                result = new Role(resultSet.getInt(ROLE_ID), RoleName.valueOf(resultSet.getString(ROLE_NAME)));
                logger.info("Role with id = " + id + " was successfully retrieved");
            }
        } catch (SQLException e) {
            logger.error("Cannot get role by id=" + id, e.getCause());
        }

        return result;
    }

    @Override
    public Role getEntityByName(String name) {
        Role result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(ROLES_GET_BY_NAME_QUERY)) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                result = new Role(resultSet.getInt(ROLE_ID), RoleName.valueOf(resultSet.getString(ROLE_NAME)));
                logger.info("Role " + name + " was successfully retrieved");
            }
        } catch (SQLException e) {
            logger.error("Cannot get role by name=" + name, e.getCause());
        }

        return result;
    }
}
