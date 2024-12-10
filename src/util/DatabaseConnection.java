package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // Database connection details
    private static final String USERNAME = "root"; // Database username
    private static final String PASSWORD = ""; // Database password (currently empty, change as needed)
    private static final String DATABASE = "calouseif"; // Database name
    private static final String HOST = "localhost:3306"; // Database host and port
    private static final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE); // Full JDBC connection URL

    // Static block to load the MySQL JDBC driver
    static {
        try {
            // Attempt to load the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // If the driver is not found, print the stack trace and throw an exception
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found!"); 
        }
    }

    /**
     * Method to establish and return a connection to the database.
     * @return Connection object representing the database connection.
     * @throws RuntimeException if the connection fails.
     */
    public static Connection getConnection() {
        try {
            // Try to establish a connection to the database using the provided connection string, username, and password
            return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // If the connection fails, print the stack trace and throw an exception
            e.printStackTrace();
            throw new RuntimeException("Failed to establish database connection!");
        }
    }
}
