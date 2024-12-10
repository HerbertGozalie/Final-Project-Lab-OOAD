package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserModel {

    /**
     * Authenticates a user based on the provided username and password.
     * @param username The username of the user trying to authenticate.
     * @param password The password of the user.
     * @return A User object if authentication is successful, otherwise null.
     */
	public static User authenticate(String username, String password) {
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        // Query to check if the user exists with the given username and password
	        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, username);
	        statement.setString(2, password);

	        // Execute the query
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            // If a user is found, return a new User object with data from the database
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
	        e.printStackTrace(); // Print any exception that occurs
	    }
	    return null; // Return null if authentication fails
	}

    /**
     * Inserts a new user into the database.
     * @param user The user object containing user details.
     * @return true if the user was successfully inserted, false otherwise.
     */
    public static boolean insertUser(User user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to check if the username already exists
            String checkQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, user.getUsername());

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                // If username already exists, return false
                return false;
            }

            // Query to insert the new user into the database
            String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getRole());

            // Execute the insert query and return true if the insertion is successful
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print any exception that occurs
        }
        return false; // Return false if an error occurs
    }

    /**
     * Checks if the provided username is unique in the database.
     * @param username The username to check for uniqueness.
     * @return true if the username is unique, false otherwise.
     */
    public static boolean isUsernameUnique(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to count how many times the username appears in the database
            String query = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Return true if the username is not found (count is 0)
                return resultSet.getInt("count") == 0;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any exception that occurs
        }
        return false; // Return false if an error occurs
    }

}
