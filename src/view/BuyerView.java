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
	private JTable tblItems;
    private JButton btnAddToWishlist, btnPurchase, btnViewWishlist, btnPurchaseHistory, btnMakeOffer;
    private JLabel lblMessage;
    private int userId;

    public BuyerView(int userId) {
        this.userId = userId;
        setTitle("Buyer - Browse and Purchase Items");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tblItems = new JTable();
        JScrollPane itemScrollPane = new JScrollPane(tblItems);
        itemScrollPane.setBorder(BorderFactory.createTitledBorder("Available Items"));
        add(itemScrollPane, BorderLayout.CENTER);

        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.NORTH);

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
        add(buttonPanel, BorderLayout.SOUTH);
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(this::handleLogout);
        buttonPanel.add(btnLogout);

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

        loadAvailableItems();
        setVisible(true);
    }
    private void loadAvailableItems() {
        List<Item> items = ItemController.getApprovedItems();
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
            loadAvailableItems();
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid item ID or price.");
            lblMessage.setForeground(Color.RED);
        }
    }
    
    private void handleMakeOffer() {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to make an offer.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString());
        double currentPrice = Double.parseDouble(tblItems.getValueAt(selectedRow, 4).toString());

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

        btnSubmit.addActionListener(e -> {
            String offerInput = txtOfferPrice.getText();
            try {
                double offerPrice = Double.parseDouble(offerInput);

                String result = TransactionController.submitValidatedOffer(userId, itemId, offerPrice);
                lblMessage.setText(result);
                lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);

                if (result.contains("successfully")) {
                    offerDialog.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(offerDialog, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> offerDialog.dispose());

        offerDialog.setVisible(true);
    }
    
    private void handleLogout(ActionEvent e) {
        
        new LoginView();
        dispose(); 
    }

    public static void main(String[] args) {
        new BuyerView(1);
    }
}
