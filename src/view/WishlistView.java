package view;

import javax.swing.*;
import java.util.List;

import controller.WishlistController;
import model.Item;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WishlistView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTable tblWishlist; // Table to display the wishlist items
    private JLabel lblMessage; // Label to display messages to the user
    private int userId; // ID of the user whose wishlist is being viewed

    // Constructor to initialize the WishlistView window
    public WishlistView(int userId) {
        this.userId = userId;
        setTitle("Your Wishlist");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window on exit
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(new BorderLayout());

        // Table setup with a scrollable pane
        tblWishlist = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblWishlist);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Wishlist Items"));
        add(scrollPane, BorderLayout.CENTER);

        // Message label for feedback to the user
        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.NORTH);

        // Buttons for removing an item or going back to the buyer view
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnRemove = new JButton("Remove");
        JButton btnBack = new JButton("Back to Buyer View");

        buttonPanel.add(btnRemove);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load wishlist data when the window is initialized
        loadWishlist();

        // Action listener for the Remove button
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveFromWishlist();
            }
        });

        // Action listener for the Back button
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new BuyerView(userId); // Open the BuyerView window
            }
        });

        setVisible(true); // Make the window visible
    }

    // Method to load the wishlist items into the table
    private void loadWishlist() {
        List<Item> wishlist = WishlistController.getWishlistItems(userId); // Fetch wishlist items
        if (wishlist != null && !wishlist.isEmpty()) {
            // Populate the table with wishlist data
            String[][] data = new String[wishlist.size()][5];
            for (int i = 0; i < wishlist.size(); i++) {
                Item item = wishlist.get(i);
                data[i][0] = String.valueOf(item.getId()); // ID
                data[i][1] = item.getName(); // Name
                data[i][2] = item.getCategory(); // Category
                data[i][3] = item.getSize(); // Size
                data[i][4] = String.valueOf(item.getPrice()); // Price
            }
            String[] columnNames = {"ID", "Name", "Category", "Size", "Price"};
            tblWishlist.setModel(new javax.swing.table.DefaultTableModel(data, columnNames)); // Update table model
            lblMessage.setText("Wishlist loaded successfully.");
            lblMessage.setForeground(Color.GREEN);
        } else {
            // Show a message if the wishlist is empty
            lblMessage.setText("Your wishlist is empty.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Method to handle the removal of an item from the wishlist
    private void handleRemoveFromWishlist() {
        int selectedRow = tblWishlist.getSelectedRow(); // Get the selected row index
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to remove from wishlist.");
            lblMessage.setForeground(Color.RED);
            return; // Exit if no item is selected
        }

        try {
            // Get the ID of the selected item
            int itemId = Integer.parseInt(tblWishlist.getValueAt(selectedRow, 0).toString());
            String result = WishlistController.removeItemFromWishlist(userId, itemId); // Remove the item via the controller

            // Display the result message to the user
            lblMessage.setText(result);
            lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);

            // Reload the wishlist to reflect the changes
            loadWishlist();
        } catch (NumberFormatException e) {
            // Handle invalid item ID format
            lblMessage.setText("Invalid item ID.");
            lblMessage.setForeground(Color.RED);
        }
    }
}
