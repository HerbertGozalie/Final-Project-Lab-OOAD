package view;

import controller.TransactionController;
import model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PurchaseHistoryView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tblHistory; // Table to display purchase history
    private JLabel lblMessage; // Label to display messages to the user
    private int userId; // User ID of the buyer to fetch purchase history

    /**
     * Constructor to initialize the PurchaseHistoryView with a specific user's ID.
     * @param userId The ID of the user whose purchase history will be displayed.
     */
    public PurchaseHistoryView(int userId) {
        this.userId = userId;

        // Set up frame properties
        setTitle("Purchase History");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create and add a table to display purchase history
        tblHistory = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblHistory);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Purchase History"));
        add(scrollPane, BorderLayout.CENTER);

        // Create and add a label to show messages (e.g., errors or success messages)
        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.SOUTH);

        // Add a back button to navigate back to the buyer view
        JButton btnBack = new JButton("Back to Buyer View");
        btnBack.addActionListener(e -> {
            // Open BuyerView and dispose of the current frame
            new BuyerView(userId);
            dispose();
        });
        add(btnBack, BorderLayout.NORTH);

        // Load and display purchase history data
        loadPurchaseHistory();
        setVisible(true); // Make the frame visible
    }

    /**
     * Method to fetch and display the purchase history of the user in the table.
     * Uses the TransactionController to get transaction data.
     */
    private void loadPurchaseHistory() {
        // Fetch transactions for the user from the controller
        List<Transaction> transactions = TransactionController.getUserTransactions(userId);

        if (transactions != null && !transactions.isEmpty()) {
            // Prepare data for the table from the list of transactions
            String[][] data = new String[transactions.size()][5];
            for (int i = 0; i < transactions.size(); i++) {
                Transaction transaction = transactions.get(i);
                data[i][0] = String.valueOf(transaction.getTransactionId()); // Transaction ID
                data[i][1] = String.valueOf(transaction.getItemId()); // Item ID
                data[i][2] = String.valueOf(transaction.getPrice()); // Price of the item
                data[i][3] = transaction.getStatus(); // Status of the transaction
                data[i][4] = transaction.getTimestamp().toString(); // Timestamp of the transaction
            }

            // Column names for the table
            String[] columnNames = {"Transaction ID", "Item ID", "Price", "Status", "Timestamp"};
            tblHistory.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

            // Set success message
            lblMessage.setText("Purchase history loaded successfully.");
            lblMessage.setForeground(Color.GREEN);
        } else {
            // Set error message if no transactions are found
            lblMessage.setText("No purchase history found.");
            lblMessage.setForeground(Color.RED);
        }
    }
}
