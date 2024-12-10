package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserModel {

	public static User authenticate(String username, String password) {
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, username);
	        statement.setString(2, password);

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

    public static boolean insertUser(User user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String checkQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, user.getUsername());

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }

            String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getRole());

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean isUsernameUnique(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count") == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
