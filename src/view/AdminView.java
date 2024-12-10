package view;

import controller.ItemController;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminView extends JFrame {
    // UI Components
    private JTable tblPendingItems;
    private JButton btnApprove, btnDecline;
    private JLabel lblMessage;
    private JTextField txtReason;

    // Constructor
    public AdminView() {
        // Set up JFrame
        setTitle("Admin - Review Items");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pending Items Table
        tblPendingItems = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(tblPendingItems);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Pending Items"));
        add(tableScrollPane, BorderLayout.CENTER);

        // Message Label
        lblMessage = new JLabel("", SwingConstants.CENTER);

        // Action Buttons and Reason Field
        JPanel actionPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel reasonPanel = new JPanel(new FlowLayout());
        txtReason = new JTextField(20);
        reasonPanel.add(new JLabel("Reason for Decline:"));
        reasonPanel.add(txtReason);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnApprove = new JButton("Approve");
        btnDecline = new JButton("Decline");
        buttonPanel.add(btnApprove);
        buttonPanel.add(btnDecline);

        actionPanel.add(reasonPanel);
        actionPanel.add(buttonPanel);

        add(actionPanel, BorderLayout.SOUTH);
        add(lblMessage, BorderLayout.NORTH);

        // Action Listeners
        btnApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleApprove();
            }
        });

        btnDecline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDecline();
            }
        });

        // Load Pending Items
        loadPendingItems();

        setVisible(true);
    }

    // Load pending items into the table
    private void loadPendingItems() {
        List<Item> pendingItems = ItemController.getPendingItems();
        if (pendingItems != null && !pendingItems.isEmpty()) {
            String[][] data = new String[pendingItems.size()][5];
            for (int i = 0; i < pendingItems.size(); i++) {
                Item item = pendingItems.get(i);
                data[i][0] = String.valueOf(item.getId());
                data[i][1] = item.getName();
                data[i][2] = item.getCategory();
                data[i][3] = item.getSize();
                data[i][4] = String.valueOf(item.getPrice());
            }
            String[] columnNames = {"ID", "Name", "Category", "Size", "Price"};
            tblPendingItems.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } else {
            lblMessage.setText("No pending items to review.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Handle Approve
    private void handleApprove() {
        int selectedRow = tblPendingItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to approve.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt((String) tblPendingItems.getValueAt(selectedRow, 0));
        boolean isApproved = ItemController.approveItem(itemId);

        if (isApproved) {
            lblMessage.setText("Item approved successfully!");
            lblMessage.setForeground(Color.GREEN);
            loadPendingItems();
        } else {
            lblMessage.setText("Failed to approve item.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Handle Decline
    private void handleDecline() {
        int selectedRow = tblPendingItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to decline.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        String reason = txtReason.getText().trim();
        if (reason.isEmpty()) {
            lblMessage.setText("Please provide a reason for declining the item.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt((String) tblPendingItems.getValueAt(selectedRow, 0));
        boolean isDeclined = ItemController.declineItem(itemId, reason);

        if (isDeclined) {
            lblMessage.setText("Item declined successfully!");
            lblMessage.setForeground(Color.GREEN);
            loadPendingItems();
        } else {
            lblMessage.setText("Failed to decline item.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        new AdminView();
    }
}
