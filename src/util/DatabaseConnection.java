package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database credentials
    private static final String USERNAME = "root"; // Default username for XAMPP
    private static final String PASSWORD = ""; // Default password for XAMPP
    private static final String DATABASE = "calouseif"; // Replace with your database name
    private static final String HOST = "localhost:3306"; // Default host and port
    private static final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    // Static block to load the driver once
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found!");
        }
    }

    // Method to get a new database connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish database connection!");
        }
    }
}
