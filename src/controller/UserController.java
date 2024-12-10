package controller;

import model.User;
import model.UserModel;

public class UserController {

    /**
     * Authenticates a user based on the provided username and password.
     * If the username is "admin" and the password is "admin", it returns a predefined Admin user.
     * Otherwise, it calls the UserModel to authenticate the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A User object if authentication is successful, otherwise null.
     */
    public static User login(String username, String password) {
        // Check if username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            return null; // Return null if either field is empty
        }
        
        // Predefined admin user check (for testing or special use cases)
        if (username.equals("admin") && password.equals("admin")) {
            return new User(0, "admin", "admin", null, null, "Admin"); // Return admin user
        }

        // Authenticate using the UserModel if it's not the predefined admin
        return UserModel.authenticate(username, password);
    }

    /**
     * Registers a new user after validating the provided input.
     * Checks for non-empty fields, username length, uniqueness, password strength, and role.
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param phoneNumber The phone number of the user.
     * @param address The address of the user.
     * @param role The role of the user (Buyer or Seller).
     * @return A message indicating whether the registration was successful or why it failed.
     */
    public static String registerUser(String username, String password, String phoneNumber, String address, String role) {
        // Validate if any fields are empty
        if (username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || role == null) {
            return "All fields are required."; // Return error if any field is empty
        }

        // Validate username length
        if (username.length() < 3) {
            return "Username must be at least 3 characters."; // Return error for short username
        }
        
        // Check if the username is unique (not already taken)
        if (!UserModel.isUsernameUnique(username)) {
            return "Username is already taken. Please choose another."; // Return error if username is taken
        }

        // Validate password length and strength
        if (password.length() < 8) {
            return "Password must be at least 8 characters long."; // Return error if password is too short
        }
        
        // Ensure the password contains at least one special character
        if (!password.matches(".*[!@#$%^&*].*")) {
            return "Password must include at least one special character (!, @, #, $, %, ^, &, *)."; // Return error if no special character is present
        }

        // Validate phone number format
        if (!phoneNumber.matches("\\+62\\d{9,10}")) {
            return "Phone number must start with +62 and be 10-11 digits long."; // Return error if phone number is invalid
        }
        
        // Ensure address is not empty
        if (address.isEmpty()) {
            return "Address cannot be empty."; // Return error if address is empty
        }
        
        // Validate role (must be either "Buyer" or "Seller")
        if (!role.equalsIgnoreCase("Buyer") && !role.equalsIgnoreCase("Seller")) {
            return "Role must be either Buyer or Seller."; // Return error if role is invalid
        }

        // Create a new User object with the provided details
        User user = new User(0, username, password, phoneNumber, address, role);
        // Attempt to register the user using the UserModel
        boolean isRegistered = UserModel.insertUser(user);

        // Return success or failure message based on registration result
        if (isRegistered) {
            return "Registration successful!";
        }
        
        return "Registration failed. Please try again."; // Return failure message if user couldn't be registered
    }
}
