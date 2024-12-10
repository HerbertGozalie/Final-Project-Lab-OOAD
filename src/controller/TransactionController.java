package controller;

import model.TransactionModel;
import model.Offer;
import model.Transaction;

import java.util.List;

public class TransactionController {

    public static String createTransaction(int userId, int itemId, double price, String status) {
        if (userId <= 0 || itemId <= 0 || price <= 0) {
            return "Invalid transaction details.";
        }

        boolean isCreated = TransactionModel.createTransaction(userId, itemId, price, status);
        return isCreated ? "Transaction created successfully!" : "Failed to create transaction.";
    }

    public static List<Transaction> getUserTransactions(int userId) {
        if (userId <= 0) {
            return null;
        }
        return TransactionModel.getUserTransactions(userId);
    }

    public static String updateTransactionStatus(int transactionId, String newStatus) {
        if (transactionId <= 0 || newStatus == null || newStatus.isEmpty()) {
            return "Invalid transaction status.";
        }

        boolean isUpdated = TransactionModel.updateTransactionStatus(transactionId, newStatus);
        return isUpdated ? "Transaction status updated successfully!" : "Failed to update transaction status.";
    }

    public static String deleteTransaction(int transactionId) {
        if (transactionId <= 0) {
            return "Invalid transaction ID.";
        }

        boolean isDeleted = TransactionModel.deleteTransaction(transactionId);
        return isDeleted ? "Transaction deleted successfully!" : "Failed to delete transaction.";
    }

    public static double getHighestOffer(int itemId) {
        return TransactionModel.getHighestOffer(itemId);
    }
    
    public static String submitValidatedOffer(int userId, int itemId, double offerPrice) {
        if (offerPrice <= 0) {
            return "Offer price must be more than zero.";
        }

        double highestOffer = getHighestOffer(itemId);
        if (offerPrice <= highestOffer) {
            return "Offer price must be higher than the current highest offer.";
        }

        boolean isSubmitted = TransactionModel.submitOffer(userId, itemId, offerPrice);
        return isSubmitted ? "Offer submitted successfully!" : "Failed to submit offer.";
    }
    
    public static List<Offer> getOfferedItems() {
        return TransactionModel.getOfferedItems();
    }

    public static String acceptOffer(int offerId) {
        boolean isAccepted = TransactionModel.acceptOffer(offerId);
        return isAccepted ? "Offer accepted successfully!" : "Failed to accept the offer.";
    }
    
    public static String declineOffer(int offerId) {
        boolean isDeclined = TransactionModel.declineOffer(offerId);
        return isDeclined ? "Offer declined successfully!" : "Failed to decline the offer.";
    }

}
