package util.db;

import dao_impl.ProductDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Close db connection resources
 */
public class DBUtil {
    private static final Logger logger = LogManager.getLogger(DBUtil.class);

    public static void close(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
            if (resultSet != null)
                resultSet.close();
        }
        catch (SQLException e) {
            logger.error("Cannot close resources", e.getCause());
        }
    }
}
