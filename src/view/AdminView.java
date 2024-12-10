package view;

import controller.AdminController;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AdminView extends JFrame {
	
	private static final long serialVersionUID = 1L;
    private JTable tblPendingItems;
    private JButton btnApprove, btnDecline;
    private JLabel lblMessage;
    private JTextField txtReason;

    public AdminView() {
        setTitle("Admin - Review Items");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tblPendingItems = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(tblPendingItems);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Pending Items"));
        add(tableScrollPane, BorderLayout.CENTER);

        lblMessage = new JLabel("", SwingConstants.CENTER);

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
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(this::handleLogout);
        buttonPanel.add(btnLogout);

        actionPanel.add(reasonPanel);
        actionPanel.add(buttonPanel);

        add(actionPanel, BorderLayout.SOUTH);
        add(lblMessage, BorderLayout.NORTH);

        btnApprove.addActionListener(this::handleApprove);
        btnDecline.addActionListener(this::handleDecline);

        loadPendingItems();

        setVisible(true);
    }

    private void loadPendingItems() {
        List<Item> pendingItems = AdminController.getPendingItems();
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

    private void handleApprove(ActionEvent e) {
        int selectedRow = tblPendingItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to approve.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt(tblPendingItems.getValueAt(selectedRow, 0).toString());
        String result = AdminController.approveItem(itemId);

        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        loadPendingItems();
    }

    private void handleDecline(ActionEvent e) {
        int selectedRow = tblPendingItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to decline.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        String reason = txtReason.getText().trim();
        String result = AdminController.declineItem(Integer.parseInt(tblPendingItems.getValueAt(selectedRow, 0).toString()), reason);

        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        loadPendingItems();
    }
    
    private void handleLogout(ActionEvent e) {
        new LoginView();
        dispose();
    }


    public static void main(String[] args) {
        new AdminView();
    }
}
