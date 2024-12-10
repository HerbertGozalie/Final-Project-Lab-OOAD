package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblMessage;

    public LoginView() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        lblMessage = new JLabel("", SwingConstants.CENTER);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(txtUsername);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(txtPassword);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        add(lblMessage, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> handleLogin());
        btnRegister.addActionListener(e -> {
            new RegisterView();
            dispose();
        });

        setVisible(true);
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        User user = UserController.login(username, password);

        if (user != null) {
            System.out.println("User ID: " + user.getId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Role: " + user.getRole());

            if (user.getRole().equals("Buyer")) {
                JOptionPane.showMessageDialog(this, "Login successful! Redirecting to BuyerView...");
                new BuyerView(user.getId());
                dispose();
            } else if (user.getRole().equals("Seller")) {
                JOptionPane.showMessageDialog(this, "Login successful! Redirecting to ItemView...");
                new SellerView();
                dispose();
            } else if (user.getRole().equals("Admin")) {
                JOptionPane.showMessageDialog(this, "Login successful! Redirecting to AdminView...");
                new AdminView();
                dispose();
            } else {
                lblMessage.setText("Invalid role.");
            }
        } else {
            lblMessage.setText("Invalid username or password.");
        }
    }
    
    public static void main(String[] args) {
        new LoginView();
    }

}
