package view;

import controller.ItemController;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemView extends JFrame {
    // UI Components
    private JTextField txtName, txtCategory, txtSize, txtPrice;
    private JButton btnUpload, btnEdit, btnDelete, btnViewItems;
    private JLabel lblMessage;
    private JTable tblItems;

    // Constructor
    public ItemView() {
        // Set up JFrame
        setTitle("Item Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
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

        btnUpload = new JButton("Upload Item");
        btnEdit = new JButton("Edit Item");
        btnDelete = new JButton("Delete Item");

        inputPanel.add(btnUpload);
        inputPanel.add(btnEdit);

        // Message Label
        lblMessage = new JLabel("", SwingConstants.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnDelete);

        // Items Table
        tblItems = new JTable();
     // Add ScrollPane for the table in the constructor
        JScrollPane tableScrollPane = new JScrollPane(tblItems);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Uploaded Items"));
        add(tableScrollPane, BorderLayout.CENTER); // Correct placement


        // Main Layout
        add(inputPanel, BorderLayout.NORTH);
        add(lblMessage, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpload();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEdit();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDelete();
            }
        });

        btnViewItems = new JButton("View Items");
        btnViewItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleViewItems();
            }
        });

        setVisible(true);
    }

    // Handle Upload
    private void handleUpload() {
        String name = txtName.getText().trim();
        String category = txtCategory.getText().trim();
        String size = txtSize.getText().trim();
        String priceStr = txtPrice.getText().trim();

        if (name.isEmpty() || category.isEmpty() || size.isEmpty() || priceStr.isEmpty()) {
            lblMessage.setText("All fields are required.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            lblMessage.setText("Price must be a positive number.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        boolean isUploaded = ItemController.uploadItem(name, category, size, price);
        if (isUploaded) {
            lblMessage.setText("Item uploaded successfully!");
            lblMessage.setForeground(Color.GREEN);
        } else {
            lblMessage.setText("Failed to upload item.");
            lblMessage.setForeground(Color.RED);
        }
    }

    // Handle Edit
    private void handleEdit() {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to edit.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        // Assume editing logic to be implemented here
        lblMessage.setText("Edit functionality not yet implemented.");
    }

    // Handle Delete
    private void handleDelete() {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an item to delete.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        try {
            int itemId = Integer.parseInt(tblItems.getValueAt(selectedRow, 0).toString());
            boolean isDeleted = ItemController.deleteItem(itemId);
            if (isDeleted) {
                lblMessage.setText("Item deleted successfully!");
                lblMessage.setForeground(Color.GREEN);
                handleViewItems(); // Refresh table after deletion
            } else {
                lblMessage.setText("Failed to delete item.");
                lblMessage.setForeground(Color.RED);
            }
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid item ID.");
            lblMessage.setForeground(Color.RED);
        }
    }


    // Handle View Items
    private void handleViewItems() {
        List<Item> items = ItemController.getItems();
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
            lblMessage.setText("Items loaded successfully.");
            lblMessage.setForeground(Color.GREEN);
        } else {
            lblMessage.setText("No items available.");
            lblMessage.setForeground(Color.RED);
        }
    }


    // Main method for testing
    public static void main(String[] args) {
        new ItemView();
    }
}
