package controller;

import model.User;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserController {

    // Authenticate user
    public static boolean authenticate(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if a matching user is found
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Register a new user
    public static boolean registerUser(String username, String password, String phoneNumber, String address, String role) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check if username already exists
            String checkQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                return false; // Username already exists
            }

            // Insert new user
            String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password); // Consider hashing the password in production
            statement.setString(3, phoneNumber);
            statement.setString(4, address);
            statement.setString(5, role);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get user by username
    public static User getUserByUsername(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("address"),
                        resultSet.getString("role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update user details
    public static boolean updateUser(User user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE users SET password = ?, phone_number = ?, address = ?, role = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getPhoneNumber());
            statement.setString(3, user.getAddress());
            statement.setString(4, user.getRole());
            statement.setInt(5, user.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete user
    public static boolean deleteUser(int userId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
