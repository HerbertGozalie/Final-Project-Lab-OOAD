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
	private JTable tblWishlist;
    private JLabel lblMessage;
    private int userId;

    public WishlistView(int userId) {
        this.userId = userId;
        setTitle("Your Wishlist");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tblWishlist = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblWishlist);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Wishlist Items"));
        add(scrollPane, BorderLayout.CENTER);

        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnRemove = new JButton("Remove");
        JButton btnBack = new JButton("Back to Buyer View");

        buttonPanel.add(btnRemove);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);

        loadWishlist();

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveFromWishlist();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BuyerView(userId);
            }
        });

        setVisible(true);
    }

    private void loadWishlist() {
        List<Item> wishlist = WishlistController.getWishlistItems(userId);
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
            lblMessage.setText("Wishlist loaded successfully.");
            lblMessage.setForeground(Color.GREEN);
        } else {
            lblMessage.setText("Your wishlist is empty.");
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

        try {
            int itemId = Integer.parseInt(tblWishlist.getValueAt(selectedRow, 0).toString());
            String result = WishlistController.removeItemFromWishlist(userId, itemId);

            lblMessage.setText(result);
            lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
            loadWishlist();
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid item ID.");
            lblMessage.setForeground(Color.RED);
        }
    }
}
