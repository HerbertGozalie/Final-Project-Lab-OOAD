package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtUsername, txtPhoneNumber, txtAddress; // Fields for user input
    private JPasswordField txtPassword; // Password field for secure input
    private JLabel lblMessage; // Label for feedback messages
    private JRadioButton rbBuyer, rbSeller; // Radio buttons for selecting role

    // Constructor to initialize the registration form
    public RegisterView() {
        setTitle("Register"); // Set the title of the window
        setSize(500, 500); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application on close
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(new GridLayout(8, 2, 5, 5)); // Use a grid layout for the form

        // Initialize input fields and labels
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtPhoneNumber = new JTextField();
        txtAddress = new JTextField();
        lblMessage = new JLabel("", SwingConstants.CENTER); // Message label for feedback

        // Radio buttons for role selection
        rbBuyer = new JRadioButton("Buyer");
        rbSeller = new JRadioButton("Seller");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(rbBuyer);
        roleGroup.add(rbSeller);

        // Buttons for registration and going back to login
        JButton btnRegister = new JButton("Register");
        JButton btnBack = new JButton("Back");

        // Add components to the form
        add(new JLabel("Username:"));
        add(txtUsername);
        add(new JLabel("Password:"));
        add(txtPassword);
        add(new JLabel("Phone Number:"));
        add(txtPhoneNumber);
        add(new JLabel("Address:"));
        add(txtAddress);

        // Add radio buttons for role selection
        add(new JLabel("Role:"));
        JPanel rolePanel = new JPanel();
        rolePanel.add(rbBuyer);
        rolePanel.add(rbSeller);
        add(rolePanel);

        // Add message label and buttons
        add(lblMessage);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);
        add(buttonPanel);

        // Register button action listener
        btnRegister.addActionListener(e -> handleRegister());

        // Back button action listener
        btnBack.addActionListener(e -> {
            new LoginView(); // Open the login view
            dispose(); // Close the registration window
        });

        setVisible(true); // Make the window visible
    }

    // Method to handle the registration process
    private void handleRegister() {
        // Collect input from the form fields
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String phoneNumber = txtPhoneNumber.getText().trim();
        String address = txtAddress.getText().trim();
        String role = rbBuyer.isSelected() ? "Buyer" : rbSeller.isSelected() ? "Seller" : null;

        // Validate and register the user through the UserController
        String result = UserController.registerUser(username, password, phoneNumber, address, role);
        
        // Display feedback to the user
        lblMessage.setText(result);
        lblMessage.setForeground(result.equals("Registration successful!") ? Color.GREEN : Color.RED);

        // Navigate to the login view if registration is successful
        if (result.equals("Registration successful!")) {
            new LoginView();
            dispose();
        }
    }
    
    // Main method to launch the registration form
    public static void main(String[] args) {
        new RegisterView();
    }

}
