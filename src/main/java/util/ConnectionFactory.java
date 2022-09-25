package util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory instance = null;
    private static BasicDataSource dataSource = null;

    private ConnectionFactory() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        dataSource = new BasicDataSource();
        dataSource.setUrl(getConnectionUrl());

//        dataSource.setMinIdle(3);
//        dataSource.setMaxIdle(11);
        dataSource.setMaxTotal(150);
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
//            conn = DriverManager.getConnection(getConnectionUrl());
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
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
            properties.load(getClass().getClassLoader().getResourceAsStream("/application.properties"));
            connection_url = properties.getProperty("connection.url");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection_url;
    }
}