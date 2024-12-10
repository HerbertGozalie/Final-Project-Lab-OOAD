package view;

import controller.UserController;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    // Components for the Login UI
    private JLabel lblUsername, lblPassword, lblMessage;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;

    // Constructor
    public LoginView() {
        // Set up the JFrame
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        // Initialize components
        lblUsername = new JLabel("Username:");
        txtUsername = new JTextField(20);

        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(20);

        lblMessage = new JLabel("", SwingConstants.CENTER);

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        // Add components to the frame
        add(lblUsername);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(lblMessage);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);
        add(buttonPanel);

        // Add action listeners
        btnLogin.addActionListener(e -> handleLogin());
        btnRegister.addActionListener(e -> JOptionPane.showMessageDialog(null, "Register page will open!"));

        // Make the frame visible
        setVisible(true);
    }

    // Method to handle login action
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        String message = UserController.login(username, password); // Controller handles logic and returns a message
        lblMessage.setText(message);

        // Change message color based on success or failure
        if (message.equals("Login successful!")) {
            lblMessage.setForeground(Color.GREEN);
            // Redirect to another page if needed
        } else {
            lblMessage.setForeground(Color.RED);
        }
    }

    // Main method for testing the LoginView
    public static void main(String[] args) {
        new LoginView();
    }
}
