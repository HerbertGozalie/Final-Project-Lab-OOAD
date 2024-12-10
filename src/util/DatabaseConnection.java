package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    // Database credentials
    private final String USERNAME = "root"; // Default username for XAMPP
    private final String PASSWORD = ""; // Default password for XAMPP
    private final String DATABASE = "calouseif"; // Replace with your database name
    private final String HOST = "localhost:3306"; // Default host and port
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
    private static Connection con;

    // Singleton instance
    private static DatabaseConnection instance;

    // Private constructor to prevent external instantiation
    private DatabaseConnection() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Public method to get the singleton instance
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Public method to get the connection object
    public static Connection getConnection() {
        if (con == null) {
            getInstance(); // Initialize the singleton instance if not already initialized
        }
        return con;
    }
}
