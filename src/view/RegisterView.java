package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    // Components for the Register UI
    private JLabel lblUsername, lblPassword, lblPhoneNumber, lblAddress, lblRole, lblMessage;
    private JTextField txtUsername, txtPhoneNumber, txtAddress;
    private JPasswordField txtPassword;
    private JRadioButton rbBuyer, rbSeller;
    private ButtonGroup roleGroup;
    private JButton btnRegister, btnBack;

    // Constructor
    public RegisterView() {
        // Set up the JFrame
        setTitle("Register");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 5, 5));

        // Initialize components
        lblUsername = new JLabel("Username:");
        txtUsername = new JTextField();

        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField();

        lblPhoneNumber = new JLabel("Phone Number:");
        txtPhoneNumber = new JTextField();

        lblAddress = new JLabel("Address:");
        txtAddress = new JTextField();

        lblRole = new JLabel("Role:");
        rbBuyer = new JRadioButton("Buyer");
        rbSeller = new JRadioButton("Seller");
        roleGroup = new ButtonGroup();
        roleGroup.add(rbBuyer);
        roleGroup.add(rbSeller);

        lblMessage = new JLabel("", SwingConstants.CENTER);

        btnRegister = new JButton("Register");
        btnBack = new JButton("Back");

        // Add components to the frame
        add(lblUsername);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(lblPhoneNumber);
        add(txtPhoneNumber);
        add(lblAddress);
        add(txtAddress);
        add(lblRole);

        JPanel rolePanel = new JPanel();
        rolePanel.add(rbBuyer);
        rolePanel.add(rbSeller);
        add(rolePanel);

        add(lblMessage);
        add(new JPanel()); // Placeholder for alignment

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);
        add(buttonPanel);

        // Add action listeners
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Return to the LoginView
                new LoginView();
                dispose();
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    // Method to handle registration logic
    private void handleRegister() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String phoneNumber = txtPhoneNumber.getText().trim();
        String address = txtAddress.getText().trim();
        String role = rbBuyer.isSelected() ? "Buyer" : rbSeller.isSelected() ? "Seller" : null;

        // Validate inputs
        if (username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || role == null) {
            lblMessage.setText("All fields are required.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        if (username.length() < 3) {
            lblMessage.setText("Username must be at least 3 characters.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        if (password.length() < 8 || !password.matches(".*[!@#$%^&*].*")) {
            lblMessage.setText("Password must be 8+ characters and include special characters.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        if (!phoneNumber.matches("\\+62\\d{9,10}")) {
            lblMessage.setText("Phone number must start with +62 and be 10+ digits.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        // Call UserController to register the user
        boolean isRegistered = UserController.registerUser(username, password, phoneNumber, address, role);

        if (isRegistered) {
            lblMessage.setText("Registration successful!");
            lblMessage.setForeground(Color.GREEN);
            // Redirect to LoginView
            new LoginView();
            dispose();
        } else {
            lblMessage.setText("Registration failed. Username might already exist.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Main method for testing the RegisterView
    public static void main(String[] args) {
        new RegisterView();
    }
}
