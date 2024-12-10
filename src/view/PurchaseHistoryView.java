package view;

import controller.TransactionController;
import model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PurchaseHistoryView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tblHistory;
    private JLabel lblMessage;
    private int userId;

    public PurchaseHistoryView(int userId) {
        this.userId = userId;
        setTitle("Purchase History");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tblHistory = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblHistory);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Purchase History"));
        add(scrollPane, BorderLayout.CENTER);

        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Back to Buyer View");
        btnBack.addActionListener(e -> {
            new BuyerView(userId);
            dispose();
        });
        add(btnBack, BorderLayout.NORTH);

        loadPurchaseHistory();
        setVisible(true);
    }

    private void loadPurchaseHistory() {
        List<Transaction> transactions = TransactionController.getUserTransactions(userId);
        if (transactions != null && !transactions.isEmpty()) {
            String[][] data = new String[transactions.size()][5];
            for (int i = 0; i < transactions.size(); i++) {
                Transaction transaction = transactions.get(i);
                data[i][0] = String.valueOf(transaction.getTransactionId());
                data[i][1] = String.valueOf(transaction.getItemId());
                data[i][2] = String.valueOf(transaction.getPrice());
                data[i][3] = transaction.getStatus();
                data[i][4] = transaction.getTimestamp().toString();
            }
            String[] columnNames = {"Transaction ID", "Item ID", "Price", "Status", "Timestamp"};
            tblHistory.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
            lblMessage.setText("Purchase history loaded successfully.");
            lblMessage.setForeground(Color.GREEN);
        } else {
            lblMessage.setText("No purchase history found.");
            lblMessage.setForeground(Color.RED);
        }
    }
}
