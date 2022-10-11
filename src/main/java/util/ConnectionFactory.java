package util;

import dao_impl.RoleRepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory instance = null;
    private static BasicDataSource dataSource = null;
    private final Logger logger = LogManager.getLogger(ConnectionFactory.class);

    private ConnectionFactory() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(getConnectionUrl());
        dataSource.setMaxTotal(150);
//        dataSource.setMinIdle(3);
//        dataSource.setMaxIdle(11);
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return conn;
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    private String getConnectionUrl() {
        Properties properties = new Properties();
        String connection_url = null;

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            connection_url = properties.getProperty("connection.url");
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }

        return connection_url;
    }
}