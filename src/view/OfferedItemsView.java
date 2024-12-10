package view;

import controller.TransactionController;
import model.Offer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OfferedItemsView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tblOffers;
    private JButton btnAcceptOffer, btnDeclineOffer, btnBack;
    private JLabel lblMessage;

    public OfferedItemsView() {
        setTitle("View Offered Items");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tblOffers = new JTable();
        JScrollPane offerScrollPane = new JScrollPane(tblOffers);
        offerScrollPane.setBorder(BorderFactory.createTitledBorder("Offers on Items"));
        add(offerScrollPane, BorderLayout.CENTER);

        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.NORTH);

        btnAcceptOffer = new JButton("Accept Offer");
        btnDeclineOffer = new JButton("Decline Offer");
        btnBack = new JButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnAcceptOffer);
        buttonPanel.add(btnDeclineOffer);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAcceptOffer.addActionListener(e -> handleAcceptOffer());
        btnDeclineOffer.addActionListener(e -> handleDeclineOffer());
        btnBack.addActionListener(e -> handleBack());

        loadOfferedItems();
        setVisible(true);
    }

    private void loadOfferedItems() {
        List<Offer> offers = TransactionController.getOfferedItems();
        if (offers != null && !offers.isEmpty()) {
            String[][] data = new String[offers.size()][5];
            for (int i = 0; i < offers.size(); i++) {
                Offer offer = offers.get(i);
                data[i][0] = String.valueOf(offer.getOfferId());
                data[i][1] = offer.getItemName();
                data[i][2] = offer.getCategory();
                data[i][3] = String.valueOf(offer.getInitialPrice());
                data[i][4] = String.valueOf(offer.getOfferPrice());
            }
            String[] columnNames = {"Offer ID", "Item Name", "Category", "Initial Price", "Offered Price"};
            tblOffers.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
            lblMessage.setText("Offers loaded successfully.");
            lblMessage.setForeground(Color.GREEN);
        } else {
            lblMessage.setText("No offers available.");
            lblMessage.setForeground(Color.RED);
        }
    }

    private void handleAcceptOffer() {
        int selectedRow = tblOffers.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an offer to accept.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int offerId = Integer.parseInt(tblOffers.getValueAt(selectedRow, 0).toString());
        String result = TransactionController.acceptOffer(offerId);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        loadOfferedItems(); // Refresh table
    }

    private void handleDeclineOffer() {
        int selectedRow = tblOffers.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an offer to decline.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        int offerId = Integer.parseInt(tblOffers.getValueAt(selectedRow, 0).toString());
        String result = TransactionController.declineOffer(offerId);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);
        loadOfferedItems();
    }

    private void handleBack() {
        this.dispose();
        new SellerView();
    }

    public static void main(String[] args) {
        new OfferedItemsView();
    }
}
