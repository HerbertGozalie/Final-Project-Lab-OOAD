package view;

import controller.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the RegisterView (to be implemented)
                JOptionPane.showMessageDialog(null, "Register page will open!");
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    // Method to handle login logic
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Username and password cannot be empty.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        // Call the UserController to validate login
        boolean isAuthenticated = UserController.authenticate(username, password);

        if (isAuthenticated) {
            lblMessage.setText("Login successful!");
            lblMessage.setForeground(Color.GREEN);
            // Redirect to the appropriate home page
        } else {
            lblMessage.setText("Invalid username or password.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Main method for testing the LoginView
    public static void main(String[] args) {
        new LoginView();
    }
}
