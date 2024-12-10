package view;

import controller.ItemController;
import controller.TransactionController;
import controller.WishlistController;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BuyerView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tblItems; // Table to display available items
    private JButton btnAddToWishlist, btnPurchase, btnViewWishlist, btnPurchaseHistory, btnMakeOffer; // Action buttons
    private JLabel lblMessage; // Label to display messages (success or error)
    private int userId; // ID of the current user (buyer)

    /**
     * Constructor to initialize the BuyerView with the user's ID.
     * Sets up the GUI components and loads the available items for browsing.
     * @param userId The ID of the logged-in user.
     */
    public BuyerView(int userId) {
        this.userId = userId;
        setTitle("Buyer - Browse and Purchase Items");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Initialize the table for displaying items
        tblItems = new JTable();
        JScrollPane itemScrollPane = new JScrollPane(tblItems);
        itemScrollPane.setBorder(BorderFactory.createTitledBorder("Available Items"));
        add(itemScrollPane, BorderLayout.CENTER);

        // Initialize the message label
        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.NORTH);

        // Panel for action buttons (purchase, add to wishlist, etc.)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnPurchase = new JButton("Purchase Item");
        btnAddToWishlist = new JButton("Add to Wishlist");
        btnViewWishlist = new JButton("View Wishlist");
        btnPurchaseHistory = new JButton("View Purchase History");
        btnMakeOffer = new JButton("Make Offer");

        buttonPanel.add(btnPurchase);
        buttonPanel.add(btnAddToWishlist);
        buttonPanel.add(btnViewWishlist);
        buttonPanel.add(btnPurchaseHistory);
        buttonPanel.add(btnMakeOffer);

        // Logout button
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(this::handleLogout);
        buttonPanel.add(btnLogout);

        add(buttonPanel, BorderLayout.SOUTH); // Add buttons to the bottom of the frame

        // Action listeners for buttons
        btnPurchase.addActionListener(e -> handlePurchase());
        btnAddToWishlist.addActionListener(e -> handleAddToWishlist());
        btnViewWishlist.addActionListener(e -> {
            new WishlistView(userId);
            dispose();
        });
        btnPurchaseHistory.addActionListener(e -> {
            new PurchaseHistoryView(userId);
            dispose();
        });
        btnMakeOffer.addActionListener(e -> handleMakeOffer());

        // Load available items from the database
        loadAvailableItems();

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Method to load available items from the ItemController and display them in the table.
     */
    private void loadAvailableItems() {
        List<Item> items = ItemController.getApprovedItems(); // Fetch approved items
        if (items != null && !items.isEmpty()) {
            // Prepare data for the table
            String[][] data = new String[items.size()][5];
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                data[i][0] = String.valueOf(item.getId()); // Item ID
                data[i][1] = item.getName(); // Item Name
                data[i][2] = item.getCategory(); // Item Category
                data[i][3] = item.getSize(); // Item Size
                data[i][4] = String.valueOf(item.getPrice()); // Item Price
            }
            String[] columnNames = {"ID", "Name", "Category", "Size", "Price"};
            tblItems.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

            // Display success message
            lblMessage.setText("Available items loaded successfully.");
            lblMessage.setForeground(Color.GREEN);
        } else {
            // Display error message if no items are available
            lblMessage.setText("No available items.");
            lblMessage.setForeground(Color.RED);
        }
    }

    /**
     * Method to handle adding an item to the wishlist.
     * Displays a message after attempting to add the selected item.
     */
    private void handleAddToWishlist() {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to add to wishlist.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        try {
            int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString());
            String result = WishlistController.addItemToWishlist(userId, itemId);

            lblMessage.setText(result);
            lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid item ID.");
            lblMessage.setForeground(Color.RED);
        }
    }

    /**
     * Method to handle the purchase of a selected item.
     * Creates a transaction and updates the purchase history.
     */
    private void handlePurchase() {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to purchase.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        try {
            int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString());
            double price = Double.parseDouble(tblItems.getValueAt(selectedRow, 4).toString());
            String result = TransactionController.createTransaction(userId, itemId, price, "Completed");

            lblMessage.setText(result);
            lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
            loadAvailableItems(); // Reload items after purchase
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid item ID or price.");
            lblMessage.setForeground(Color.RED);
        }
    }

    /**
     * Method to handle making an offer on a selected item.
     * Opens a dialog for the user to input their offer price.
     */
    private void handleMakeOffer() {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to make an offer.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString());
        double currentPrice = Double.parseDouble(tblItems.getValueAt(selectedRow, 4).toString());

        // Open a dialog for the user to enter their offer
        JDialog offerDialog = new JDialog(this, "Make an Offer", true);
        offerDialog.setSize(400, 200);
        offerDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblCurrentPrice = new JLabel("Current Price: " + currentPrice);
        JLabel lblOfferPrice = new JLabel("Your Offer:");
        JTextField txtOfferPrice = new JTextField();
        JButton btnSubmit = new JButton("Submit");
        JButton btnCancel = new JButton("Cancel");

        panel.add(lblCurrentPrice);
        panel.add(new JLabel());
        panel.add(lblOfferPrice);
        panel.add(txtOfferPrice);
        panel.add(btnSubmit);
        panel.add(btnCancel);

        offerDialog.add(panel);

        // Action to submit the offer
        btnSubmit.addActionListener(e -> {
            String offerInput = txtOfferPrice.getText();
            try {
                double offerPrice = Double.parseDouble(offerInput);

                String result = TransactionController.submitValidatedOffer(userId, itemId, offerPrice);
                lblMessage.setText(result);
                lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);

                if (result.contains("successfully")) {
                    offerDialog.dispose(); // Close the offer dialog on success
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(offerDialog, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> offerDialog.dispose()); // Close the dialog on cancel

        offerDialog.setVisible(true); // Show the offer dialog
    }

    /**
     * Method to handle logout and return to the LoginView.
     * Disposes of the current BuyerView.
     */
    private void handleLogout(ActionEvent e) {
        new LoginView(); // Open the login view
        dispose(); // Close the current BuyerView
    }

    /**
     * Main method to test the BuyerView independently.
     */
    public static void main(String[] args) {
        new BuyerView(1); // Create a new instance of BuyerView with a sample user ID
    }
}
