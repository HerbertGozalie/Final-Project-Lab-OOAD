package controller;

import model.User;
import model.UserModel;

public class UserController {

	public static User login(String username, String password) {
	    if (username.isEmpty() || password.isEmpty()) {
	        return null;
	    }
	    
	    if (username.equals("admin") && password.equals("admin")) {
	        return new User(0, "admin", "admin", null, null, "Admin");
	    }

	    return UserModel.authenticate(username, password);
	}

    public static String registerUser(String username, String password, String phoneNumber, String address, String role) {
        if (username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || role == null) {
            return "All fields are required.";
        }

        if (username.length() < 3) {
            return "Username must be at least 3 characters.";
        }
        
        if (!UserModel.isUsernameUnique(username)) {
            return "Username is already taken. Please choose another.";
        }

        if (password.length() < 8) {
            return "Password must be at least 8 characters long.";
        }
        
        if (!password.matches(".*[!@#$%^&*].*")) {
            return "Password must include at least one special character (!, @, #, $, %, ^, &, *).";
        }

        if (!phoneNumber.matches("\\+62\\d{9,10}")) {
            return "Phone number must start with +62 and be 10-11 digits long.";
        }
        
        if (address.isEmpty()) {
            return "Address cannot be empty.";
        }
        
        if (!role.equalsIgnoreCase("Buyer") && !role.equalsIgnoreCase("Seller")) {
            return "Role must be either Buyer or Seller.";
        }

        User user = new User(0, username, password, phoneNumber, address, role);
        boolean isRegistered = UserModel.insertUser(user);

        if (isRegistered) {
            return "Registration successful!";
        }
        
        return "Registration failed. Please try again.";
    }
}
