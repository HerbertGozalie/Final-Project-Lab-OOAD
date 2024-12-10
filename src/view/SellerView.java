package view;

import controller.ItemController;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SellerView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtName, txtCategory, txtSize, txtPrice;
    private JLabel lblMessage;
    private JTable tblItems;

    public SellerView() {
        setTitle("Item Management");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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

        JButton btnUpload = new JButton("Upload Item");
        JButton btnEdit = new JButton("Edit Item");
        JButton btnDelete = new JButton("Delete Item");
        JButton btnViewOffers = new JButton("View Offer Items");

        btnUpload.addActionListener(this::handleUpload);
        btnEdit.addActionListener(this::handleEdit);
        btnDelete.addActionListener(this::handleDelete);
        btnViewOffers.addActionListener(this::handleViewOffers);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonPanel.add(btnUpload);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewOffers);
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(this::handleLogout);
        buttonPanel.add(btnLogout);


        lblMessage = new JLabel("", SwingConstants.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(lblMessage, BorderLayout.SOUTH);

        tblItems = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(tblItems);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Uploaded Items"));

        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        handleViewItems();

        setVisible(true);
    }
    
    private void handleViewItems() {
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
        } else {
            lblMessage.setText("No approved items available.");
            lblMessage.setForeground(Color.RED);
        }
    }

    private void handleUpload(ActionEvent e) {
        String name = txtName.getText().trim();
        String category = txtCategory.getText().trim();
        String size = txtSize.getText().trim();
        String priceStr = txtPrice.getText().trim();

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException ex) {
            lblMessage.setText("Invalid price.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        String result = ItemController.uploadItem(name, category, size, price);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        handleViewItems();
    }

    private void handleEdit(ActionEvent e) {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to edit.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString());
        String name = txtName.getText().trim();
        String category = txtCategory.getText().trim();
        String size = txtSize.getText().trim();
        String priceStr = txtPrice.getText().trim();

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException ex) {
            lblMessage.setText("Invalid price.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        String result = ItemController.editItem(itemId, name, category, size, price);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        handleViewItems();
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to delete.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString());
        String result = ItemController.deleteItem(itemId);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        handleViewItems();
    }

    private void handleViewOffers(ActionEvent e) {
        new OfferedItemsView();
        dispose();
    }

    private void handleLogout(ActionEvent e) {
        new LoginView();
        dispose();
    }


    public static void main(String[] args) {
        new SellerView();
    }
}
