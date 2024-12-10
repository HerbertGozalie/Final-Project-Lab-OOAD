package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtUsername; // Text field to input the username
    private JPasswordField txtPassword; // Password field to input the password
    private JLabel lblMessage; // Label to display messages (e.g., error or success)

    /**
     * Constructor to initialize the LoginView UI and handle user interactions.
     */
    public LoginView() {
        // Frame setup
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Initialize input fields and message label
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        lblMessage = new JLabel("", SwingConstants.CENTER);

        // Buttons for login and registration
        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        // Create and configure the input panel (username and password)
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(txtUsername);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(txtPassword);

        // Panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        // Add components to the main frame
        add(lblMessage, BorderLayout.NORTH); // Message label at the top
        add(inputPanel, BorderLayout.CENTER); // Input fields in the center
        add(buttonPanel, BorderLayout.SOUTH); // Buttons at the bottom

        // Action listeners for the buttons
        btnLogin.addActionListener(e -> handleLogin()); // Login button action
        btnRegister.addActionListener(e -> {
            new RegisterView(); // Opens the RegisterView if the user clicks Register
            dispose(); // Close the login window
        });

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Handles the login process.
     * Validates the username and password, and redirects the user based on their role.
     */
    private void handleLogin() {
        // Get user input for username and password
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        // Call the controller to attempt login and get the user object
        User user = UserController.login(username, password);

        if (user != null) {
            // Print user details to console for debugging (can be removed later)
            System.out.println("User ID: " + user.getId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Role: " + user.getRole());

            // Redirect based on the user's role
            if (user.getRole().equals("Buyer")) {
                JOptionPane.showMessageDialog(this, "Login successful! Redirecting to BuyerView...");
                new BuyerView(user.getId()); // Open BuyerView
                dispose(); // Close LoginView
            } else if (user.getRole().equals("Seller")) {
                JOptionPane.showMessageDialog(this, "Login successful! Redirecting to ItemView...");
                new SellerView(); // Open SellerView
                dispose(); // Close LoginView
            } else if (user.getRole().equals("Admin")) {
                JOptionPane.showMessageDialog(this, "Login successful! Redirecting to AdminView...");
                new AdminView(); // Open AdminView
                dispose(); // Close LoginView
            } else {
                // Handle invalid role
                lblMessage.setText("Invalid role.");
            }
        } else {
            // Display error message if login fails
            lblMessage.setText("Invalid username or password.");
        }
    }

    /**
     * Main method to test the LoginView independently.
     */
    public static void main(String[] args) {
        new LoginView(); // Launch the login window
    }
}
