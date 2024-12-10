package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DATABASE = "calouseif";
    private static final String HOST = "localhost:3306";
    private static final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found!");
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish database connection!");
        }
    }
}
