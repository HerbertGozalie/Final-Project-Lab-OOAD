package view;

import controller.ItemController;
import controller.WishlistController;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuyerView extends JFrame {
    // UI Components
    private JTable tblItems, tblWishlist;
    private JButton btnAddToWishlist, btnPurchase, btnViewWishlist;
    private JLabel lblMessage;
    private int userId; // User ID of the logged-in user

    // Constructor
    public BuyerView(int userID) {
    	this.userId = userId;
        // Set up JFrame
        setTitle("Buyer - Browse and Purchase Items");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Item Table
        tblItems = new JTable();
        JScrollPane itemScrollPane = new JScrollPane(tblItems);
        itemScrollPane.setBorder(BorderFactory.createTitledBorder("Available Items"));
        add(itemScrollPane, BorderLayout.CENTER);

        // Wishlist Table
        tblWishlist = new JTable();
        JScrollPane wishlistScrollPane = new JScrollPane(tblWishlist);
        wishlistScrollPane.setBorder(BorderFactory.createTitledBorder("Wishlist"));
        wishlistScrollPane.setPreferredSize(new Dimension(800, 200));
        add(wishlistScrollPane, BorderLayout.SOUTH);

        // Message Label
        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.NORTH);

        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAddToWishlist = new JButton("Add to Wishlist");
        btnPurchase = new JButton("Purchase Item");
        btnViewWishlist = new JButton("View Wishlist");

        buttonPanel.add(btnAddToWishlist);
        buttonPanel.add(btnPurchase);
        buttonPanel.add(btnViewWishlist);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnAddToWishlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddToWishlist();
            }
        });

        btnPurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePurchase();
            }
        });

        btnViewWishlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadWishlist();
            }
        });

        // Load Available Items
        loadAvailableItems();

        setVisible(true);
    }

    // Load available items into the table
    private void loadAvailableItems() {
        List<Item> items = ItemController.getAvailableItems();
        if (items != null && !items.isEmpty()) {
            String[][] data = new String[items.size()][5];
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                data[i][0] = String.valueOf(item.getId());
                data[i][1] = item.getName();
                data[i][2] = item.getCategory();
                data[i][3] = item.getSize();
                data[i][4] = String.valueOf(item.getPrice());
            }
            String[] columnNames = {"ID", "Name", "Category", "Size", "Price"};
            tblItems.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
            lblMessage.setText("Available items loaded successfully.");
            lblMessage.setForeground(Color.GREEN);
        } else {
            lblMessage.setText("No available items.");
            lblMessage.setForeground(Color.RED);
        }
    }


    // Load wishlist into the table
    private void loadWishlist() {
        List<Item> wishlist = WishlistController.getWishlistItems(userId); // Pass userId here
        if (wishlist != null && !wishlist.isEmpty()) {
            String[][] data = new String[wishlist.size()][5];
            for (int i = 0; i < wishlist.size(); i++) {
                Item item = wishlist.get(i);
                data[i][0] = String.valueOf(item.getId());
                data[i][1] = item.getName();
                data[i][2] = item.getCategory();
                data[i][3] = item.getSize();
                data[i][4] = String.valueOf(item.getPrice());
            }
            String[] columnNames = {"ID", "Name", "Category", "Size", "Price"};
            tblWishlist.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } else {
            lblMessage.setText("Your wishlist is empty.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Handle adding an item to the wishlist
    private void handleAddToWishlist() {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to add to wishlist.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        try {
            int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString()); // Ensure parsing to String first
            boolean isAdded = WishlistController.addItemToWishlist(userId, itemId); // Pass userId

            if (isAdded) {
                lblMessage.setText("Item added to wishlist!");
                lblMessage.setForeground(Color.GREEN);
                loadWishlist();
            } else {
                lblMessage.setText("Failed to add item to wishlist.");
                lblMessage.setForeground(Color.RED);
            }
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid item ID.");
            lblMessage.setForeground(Color.RED);
        }
    }

    
    private void handleRemoveFromWishlist() {
        int selectedRow = tblWishlist.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to remove from wishlist.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt((String) tblWishlist.getValueAt(selectedRow, 0));
        boolean isRemoved = WishlistController.removeItemFromWishlist(userId, itemId); // Pass userId

        if (isRemoved) {
            lblMessage.setText("Item removed from wishlist.");
            lblMessage.setForeground(Color.GREEN);
            loadWishlist();
        } else {
            lblMessage.setText("Failed to remove item from wishlist.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Handle purchasing an item
    private void handlePurchase() {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to purchase.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        try {
            int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString());
            boolean isPurchased = ItemController.purchaseItem(itemId, userId); // Pass userId as buyerId

            if (isPurchased) {
                lblMessage.setText("Purchase successful!");
                lblMessage.setForeground(Color.GREEN);
                loadAvailableItems(); // Refresh the available items
                loadWishlist(); // Refresh the wishlist
            } else {
                lblMessage.setText("Failed to purchase item. It may no longer be available.");
                lblMessage.setForeground(Color.RED);
            }
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid item ID.");
            lblMessage.setForeground(Color.RED);
        }
    }


    // Main method for testing
    public static void main(String[] args) {
        new BuyerView(1);
    }
}
