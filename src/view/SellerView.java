package view;

import controller.ItemController;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SellerView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtName, txtCategory, txtSize, txtPrice; // Text fields for item details
    private JLabel lblMessage; // Label for feedback messages
    private JTable tblItems; // Table to display the list of uploaded items

    // Constructor to initialize the SellerView window
    public SellerView() {
        setTitle("Item Management");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application when closed
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(new BorderLayout());

        // Input panel to collect item details
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));

        txtName = new JTextField();
        txtCategory = new JTextField();
        txtSize = new JTextField();
        txtPrice = new JTextField();

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(txtName);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(txtCategory);
        inputPanel.add(new JLabel("Size:"));
        inputPanel.add(txtSize);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(txtPrice);

        // Buttons for CRUD operations and viewing offer items
        JButton btnUpload = new JButton("Upload Item");
        JButton btnEdit = new JButton("Edit Item");
        JButton btnDelete = new JButton("Delete Item");
        JButton btnViewOffers = new JButton("View Offer Items");

        btnUpload.addActionListener(this::handleUpload);
        btnEdit.addActionListener(this::handleEdit);
        btnDelete.addActionListener(this::handleDelete);
        btnViewOffers.addActionListener(this::handleViewOffers);

        // Logout button to return to the login screen
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(this::handleLogout);

        // Button panel for all the action buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        buttonPanel.add(btnUpload);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewOffers);
        buttonPanel.add(btnLogout);

        // Feedback message label
        lblMessage = new JLabel("", SwingConstants.CENTER);

        // Bottom panel to hold the buttons and feedback label
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(lblMessage, BorderLayout.SOUTH);

        // Table to display the list of uploaded items
        tblItems = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(tblItems);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Uploaded Items"));

        // Add panels to the main layout
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load the list of uploaded items when the window is initialized
        handleViewItems();

        setVisible(true); // Make the window visible
    }
    
    // Method to fetch and display uploaded items in the table
    private void handleViewItems() {
        List<Item> items = ItemController.getApprovedItems(); // Get the list of approved items
        if (items != null && !items.isEmpty()) {
            // Populate the table with item details
            String[][] data = new String[items.size()][5];
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                data[i][0] = String.valueOf(item.getId()); // Item ID
                data[i][1] = item.getName(); // Item name
                data[i][2] = item.getCategory(); // Item category
                data[i][3] = item.getSize(); // Item size
                data[i][4] = String.valueOf(item.getPrice()); // Item price
            }
            String[] columnNames = {"ID", "Name", "Category", "Size", "Price"};
            tblItems.setModel(new javax.swing.table.DefaultTableModel(data, columnNames)); // Update table model
        } else {
            // Show a message if no items are available
            lblMessage.setText("No approved items available.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Method to handle the upload of a new item
    private void handleUpload(ActionEvent e) {
        String name = txtName.getText().trim();
        String category = txtCategory.getText().trim();
        String size = txtSize.getText().trim();
        String priceStr = txtPrice.getText().trim();

        double price;
        try {
            // Parse the price input
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException ex) {
            lblMessage.setText("Invalid price.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        // Upload the item and display the result
        String result = ItemController.uploadItem(name, category, size, price);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        handleViewItems(); // Refresh the item list
    }

    // Method to handle editing an existing item
    private void handleEdit(ActionEvent e) {
        int selectedRow = tblItems.getSelectedRow(); // Get the selected row
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to edit.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString()); // Get the item ID
        String name = txtName.getText().trim();
        String category = txtCategory.getText().trim();
        String size = txtSize.getText().trim();
        String priceStr = txtPrice.getText().trim();

        double price;
        try {
            // Parse the price input
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException ex) {
            lblMessage.setText("Invalid price.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        // Edit the item and display the result
        String result = ItemController.editItem(itemId, name, category, size, price);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        handleViewItems(); // Refresh the item list
    }

    // Method to handle deleting an item
    private void handleDelete(ActionEvent e) {
        int selectedRow = tblItems.getSelectedRow(); // Get the selected row
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to delete.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString()); // Get the item ID
        String result = ItemController.deleteItem(itemId); // Delete the item
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        handleViewItems(); // Refresh the item list
    }

    // Method to view offer items (navigate to another view)
    private void handleViewOffers(ActionEvent e) {
        new OfferedItemsView(); // Open the OfferedItemsView
        dispose(); // Close the current window
    }

    // Method to logout and return to the login screen
    private void handleLogout(ActionEvent e) {
        new LoginView(); // Open the LoginView
        dispose(); // Close the current window
    }

    public static void main(String[] args) {
        new SellerView(); // Launch the SellerView
    }
}
