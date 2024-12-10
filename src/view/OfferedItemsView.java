package view;

import controller.TransactionController;
import model.Offer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OfferedItemsView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tblOffers; // Table to display offered items
    private JButton btnAcceptOffer, btnDeclineOffer, btnBack; // Action buttons
    private JLabel lblMessage; // Label to show success/error messages

    /**
     * Constructor to initialize the OfferedItemsView.
     * Sets up the GUI components and loads the offered items.
     */
    public OfferedItemsView() {
        // Frame setup
        setTitle("View Offered Items");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create and add table to display offered items
        tblOffers = new JTable();
        JScrollPane offerScrollPane = new JScrollPane(tblOffers);
        offerScrollPane.setBorder(BorderFactory.createTitledBorder("Offers on Items"));
        add(offerScrollPane, BorderLayout.CENTER);

        // Create and add a message label at the top
        lblMessage = new JLabel("", SwingConstants.CENTER);
        add(lblMessage, BorderLayout.NORTH);

        // Create action buttons and add them to a panel
        btnAcceptOffer = new JButton("Accept Offer");
        btnDeclineOffer = new JButton("Decline Offer");
        btnBack = new JButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnAcceptOffer);
        buttonPanel.add(btnDeclineOffer);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners for the buttons
        btnAcceptOffer.addActionListener(e -> handleAcceptOffer());
        btnDeclineOffer.addActionListener(e -> handleDeclineOffer());
        btnBack.addActionListener(e -> handleBack());

        // Load the offered items into the table
        loadOfferedItems();

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Method to fetch and display the offered items in the table.
     * Uses the TransactionController to retrieve data.
     */
    private void loadOfferedItems() {
        List<Offer> offers = TransactionController.getOfferedItems();
        if (offers != null && !offers.isEmpty()) {
            // Prepare data for the table
            String[][] data = new String[offers.size()][5];
            for (int i = 0; i < offers.size(); i++) {
                Offer offer = offers.get(i);
                data[i][0] = String.valueOf(offer.getOfferId()); // Offer ID
                data[i][1] = offer.getItemName(); // Item name
                data[i][2] = offer.getCategory(); // Item category
                data[i][3] = String.valueOf(offer.getInitialPrice()); // Initial price
                data[i][4] = String.valueOf(offer.getOfferPrice()); // Offered price
            }

            // Column names for the table
            String[] columnNames = {"Offer ID", "Item Name", "Category", "Initial Price", "Offered Price"};
            tblOffers.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

            // Display success message
            lblMessage.setText("Offers loaded successfully.");
            lblMessage.setForeground(Color.GREEN);
        } else {
            // Display error message if no offers are available
            lblMessage.setText("No offers available.");
            lblMessage.setForeground(Color.RED);
        }
    }

    /**
     * Method to handle accepting an offer.
     * Validates selection and updates the status of the offer.
     */
    private void handleAcceptOffer() {
        int selectedRow = tblOffers.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an offer to accept.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        // Retrieve the selected offer's ID
        int offerId = Integer.parseInt(tblOffers.getValueAt(selectedRow, 0).toString());

        // Call the controller to accept the offer and display the result
        String result = TransactionController.acceptOffer(offerId);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);

        // Reload the offered items table
        loadOfferedItems();
    }

    /**
     * Method to handle declining an offer.
     * Validates selection and updates the status of the offer.
     */
    private void handleDeclineOffer() {
        int selectedRow = tblOffers.getSelectedRow();
        if (selectedRow == -1) {
            lblMessage.setText("Select an offer to decline.");
            lblMessage.setForeground(Color.RED);
            return;
        }

        // Retrieve the selected offer's ID
        int offerId = Integer.parseInt(tblOffers.getValueAt(selectedRow, 0).toString());

        // Call the controller to decline the offer and display the result
        String result = TransactionController.declineOffer(offerId);
        lblMessage.setText(result);
        lblMessage.setForeground(result.contains("successfully") ? Color.GREEN : Color.RED);

        // Reload the offered items table
        loadOfferedItems();
    }

    /**
     * Method to handle the back button action.
     * Navigates back to the SellerView.
     */
    private void handleBack() {
        this.dispose(); // Close the current view
        new SellerView(); // Open the SellerView
    }

    /**
     * Main method for testing the OfferedItemsView independently.
     */
    public static void main(String[] args) {
        new OfferedItemsView();
    }
}
