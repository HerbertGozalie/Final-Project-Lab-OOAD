package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {

    private JTextField txtUsername, txtPhoneNumber, txtAddress;
    private JPasswordField txtPassword;
    private JLabel lblMessage;
    private JRadioButton rbBuyer, rbSeller;

    public RegisterView() {
        setTitle("Register");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 5, 5));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtPhoneNumber = new JTextField();
        txtAddress = new JTextField();
        lblMessage = new JLabel("", SwingConstants.CENTER);

        rbBuyer = new JRadioButton("Buyer");
        rbSeller = new JRadioButton("Seller");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(rbBuyer);
        roleGroup.add(rbSeller);

        JButton btnRegister = new JButton("Register");
        JButton btnBack = new JButton("Back");

        add(new JLabel("Username:"));
        add(txtUsername);
        add(new JLabel("Password:"));
        add(txtPassword);
        add(new JLabel("Phone Number:"));
        add(txtPhoneNumber);
        add(new JLabel("Address:"));
        add(txtAddress);

        add(new JLabel("Role:"));
        JPanel rolePanel = new JPanel();
        rolePanel.add(rbBuyer);
        rolePanel.add(rbSeller);
        add(rolePanel);

        add(lblMessage);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);
        add(buttonPanel);

        btnRegister.addActionListener(e -> handleRegister());
        btnBack.addActionListener(e -> {
            new LoginView();
            dispose();
        });

        setVisible(true);
    }

    private void handleRegister() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String phoneNumber = txtPhoneNumber.getText().trim();
        String address = txtAddress.getText().trim();
        String role = rbBuyer.isSelected() ? "Buyer" : rbSeller.isSelected() ? "Seller" : null;

        String result = UserController.registerUser(username, password, phoneNumber, address, role);
        lblMessage.setText(result);
        lblMessage.setForeground(result.equals("Registration successful!") ? Color.GREEN : Color.RED);

        if (result.equals("Registration successful!")) {
            new LoginView();
            dispose();
        }
    }
    
    public static void main(String[] args) {
        new RegisterView();
    }

}
