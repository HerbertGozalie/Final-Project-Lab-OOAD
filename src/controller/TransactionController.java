package controller;

import model.TransactionModel;
import model.Offer;
import model.Transaction;

import java.util.List;

public class TransactionController {

    /**
     * Creates a new transaction.
     * Validates the transaction details before calling the model to insert the transaction.
     * @param userId The ID of the user making the transaction.
     * @param itemId The ID of the item being purchased.
     * @param price The price of the item.
     * @param status The status of the transaction (e.g., "Completed").
     * @return A message indicating whether the transaction was successfully created or not.
     */
    public static String createTransaction(int userId, int itemId, double price, String status) {
        // Validate the transaction details
        if (userId <= 0 || itemId <= 0 || price <= 0) {
            return "Invalid transaction details."; // Return error if any detail is invalid
        }

        // Attempt to create the transaction using the TransactionModel
        boolean isCreated = TransactionModel.createTransaction(userId, itemId, price, status);
        return isCreated ? "Transaction created successfully!" : "Failed to create transaction."; // Return success or failure message
    }

    /**
     * Retrieves the list of transactions for a specific user.
     * @param userId The ID of the user whose transactions are to be fetched.
     * @return A list of transactions for the user.
     */
    public static List<Transaction> getUserTransactions(int userId) {
        if (userId <= 0) {
            return null; // Return null if the user ID is invalid
        }
        // Retrieve transactions from the TransactionModel
        return TransactionModel.getUserTransactions(userId);
    }

    /**
     * Updates the status of a specific transaction.
     * @param transactionId The ID of the transaction to update.
     * @param newStatus The new status for the transaction.
     * @return A message indicating whether the transaction status was successfully updated or not.
     */
    public static String updateTransactionStatus(int transactionId, String newStatus) {
        // Validate the transaction ID and status
        if (transactionId <= 0 || newStatus == null || newStatus.isEmpty()) {
            return "Invalid transaction status."; // Return error if the status is invalid
        }

        // Attempt to update the transaction status using the TransactionModel
        boolean isUpdated = TransactionModel.updateTransactionStatus(transactionId, newStatus);
        return isUpdated ? "Transaction status updated successfully!" : "Failed to update transaction status."; // Return success or failure message
    }

    /**
     * Deletes a specific transaction.
     * @param transactionId The ID of the transaction to delete.
     * @return A message indicating whether the transaction was successfully deleted or not.
     */
    public static String deleteTransaction(int transactionId) {
        if (transactionId <= 0) {
            return "Invalid transaction ID."; // Return error if the transaction ID is invalid
        }

        // Attempt to delete the transaction using the TransactionModel
        boolean isDeleted = TransactionModel.deleteTransaction(transactionId);
        return isDeleted ? "Transaction deleted successfully!" : "Failed to delete transaction."; // Return success or failure message
    }

    /**
     * Retrieves the highest offer price for a specific item.
     * @param itemId The ID of the item to check for the highest offer.
     * @return The highest offer price for the item.
     */
    public static double getHighestOffer(int itemId) {
        // Retrieve the highest offer from the TransactionModel
        return TransactionModel.getHighestOffer(itemId);
    }

    /**
     * Submits a validated offer for a specific item.
     * Validates that the offer price is higher than the current highest offer.
     * @param userId The ID of the user making the offer.
     * @param itemId The ID of the item being offered on.
     * @param offerPrice The price being offered.
     * @return A message indicating whether the offer was successfully submitted or not.
     */
    public static String submitValidatedOffer(int userId, int itemId, double offerPrice) {
        // Validate the offer price
        if (offerPrice <= 0) {
            return "Offer price must be more than zero."; // Return error if offer price is invalid
        }

        // Get the current highest offer for the item
        double highestOffer = getHighestOffer(itemId);
        if (offerPrice <= highestOffer) {
            return "Offer price must be higher than the current highest offer."; // Return error if offer is too low
        }

        // Attempt to submit the offer using the TransactionModel
        boolean isSubmitted = TransactionModel.submitOffer(userId, itemId, offerPrice);
        return isSubmitted ? "Offer submitted successfully!" : "Failed to submit offer."; // Return success or failure message
    }

    /**
     * Retrieves a list of items that have offers placed on them.
     * @return A list of offers for items.
     */
    public static List<Offer> getOfferedItems() {
        // Retrieve offered items from the TransactionModel
        return TransactionModel.getOfferedItems();
    }

    /**
     * Accepts an offer and completes the transaction.
     * @param offerId The ID of the offer to accept.
     * @return A message indicating whether the offer was successfully accepted or not.
     */
    public static String acceptOffer(int offerId) {
        // Attempt to accept the offer using the TransactionModel
        boolean isAccepted = TransactionModel.acceptOffer(offerId);
        return isAccepted ? "Offer accepted successfully!" : "Failed to accept the offer."; // Return success or failure message
    }

    /**
     * Declines an offer and removes it from the database.
     * @param offerId The ID of the offer to decline.
     * @return A message indicating whether the offer was successfully declined or not.
     */
    public static String declineOffer(int offerId) {
        // Attempt to decline the offer using the TransactionModel
        boolean isDeclined = TransactionModel.declineOffer(offerId);
        return isDeclined ? "Offer declined successfully!" : "Failed to decline the offer."; // Return success or failure message
    }
}
