package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;

    private Connection connection;

    private DBConnection() throws SQLException {
        // Corrected URL
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smallshop", "root", "12345");
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
