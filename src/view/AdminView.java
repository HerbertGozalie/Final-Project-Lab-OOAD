package view;

import controller.AdminController;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AdminView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tblPendingItems; // Table to display pending items for review
    private JButton btnApprove, btnDecline; // Buttons for approving or declining items
    private JLabel lblMessage; // Label to display success or error messages
    private JTextField txtReason; // Text field to input the reason for declining an item

    /**
     * Constructor to initialize the AdminView and set up the layout.
     * Loads the pending items and handles user interactions for approving or declining items.
     */
    public AdminView() {
        setTitle("Admin - Review Items");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(new BorderLayout());

        // Initialize table for displaying pending items
        tblPendingItems = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(tblPendingItems);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Pending Items"));
        add(tableScrollPane, BorderLayout.CENTER);

        // Initialize label for displaying messages (success/error)
        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.NORTH);

        // Panel for reason input (only visible when declining an item)
        JPanel reasonPanel = new JPanel(new FlowLayout());
        txtReason = new JTextField(20);
        reasonPanel.add(new JLabel("Reason for Decline:"));
        reasonPanel.add(txtReason);

        // Panel for action buttons (Approve, Decline, Logout)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnApprove = new JButton("Approve");
        btnDecline = new JButton("Decline");
        buttonPanel.add(btnApprove);
        buttonPanel.add(btnDecline);

        // Logout button
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(this::handleLogout); // Logout action listener
        buttonPanel.add(btnLogout);

        // Action panel for reason and buttons
        JPanel actionPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        actionPanel.add(reasonPanel); // Add reason panel
        actionPanel.add(buttonPanel); // Add action buttons panel
        add(actionPanel, BorderLayout.SOUTH); // Add action panel to bottom

        // Add action listeners for approve and decline buttons
        btnApprove.addActionListener(this::handleApprove);
        btnDecline.addActionListener(this::handleDecline);

        // Load the list of pending items
        loadPendingItems();

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Method to load and display pending items in the table.
     * Uses AdminController to fetch the items that need review.
     */
    private void loadPendingItems() {
        // Fetch pending items from the AdminController
        List<Item> pendingItems = AdminController.getPendingItems();
        if (pendingItems != null && !pendingItems.isEmpty()) {
            // Prepare data for the table
            String[][] data = new String[pendingItems.size()][5];
            for (int i = 0; i < pendingItems.size(); i++) {
                Item item = pendingItems.get(i);
                data[i][0] = String.valueOf(item.getId()); // Item ID
                data[i][1] = item.getName(); // Item Name
                data[i][2] = item.getCategory(); // Item Category
                data[i][3] = item.getSize(); // Item Size
                data[i][4] = String.valueOf(item.getPrice()); // Item Price
            }
            String[] columnNames = {"ID", "Name", "Category", "Size", "Price"};
            tblPendingItems.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } else {
            // Display message if no items are pending
            lblMessage.setText("No pending items to review.");
            lblMessage.setForeground(Color.RED);
        }
    }

    /**
     * Method to handle the approval of the selected item.
     * It updates the item status and reloads the pending items list.
     */
    private void handleApprove(ActionEvent e) {
        int selectedRow = tblPendingItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to approve.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt(tblPendingItems.getValueAt(selectedRow, 0).toString());
        String result = AdminController.approveItem(itemId); // Approve the item using the AdminController

        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        loadPendingItems(); // Reload pending items after action
    }

    /**
     * Method to handle the decline of the selected item.
     * The reason for decline is entered by the admin.
     */
    private void handleDecline(ActionEvent e) {
        int selectedRow = tblPendingItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to decline.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        String reason = txtReason.getText().trim();
        if (reason.isEmpty()) {
            lblMessage.setText("Enter a reason for declining.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        String result = AdminController.declineItem(Integer.parseInt(tblPendingItems.getValueAt(selectedRow, 0).toString()), reason);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        loadPendingItems(); // Reload pending items after action
    }

    /**
     * Method to handle the logout action.
     * This will close the current AdminView and open the LoginView.
     */
    private void handleLogout(ActionEvent e) {
        new LoginView(); // Open the login view
        dispose(); // Close the AdminView
    }

    /**
     * Main method to test the AdminView independently.
     */
    public static void main(String[] args) {
        new AdminView(); // Create and show the AdminView
    }
}
