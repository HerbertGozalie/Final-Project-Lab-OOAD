package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionModel {

    /**
     * Creates a new transaction in the database.
     * @param userId The ID of the user making the transaction.
     * @param itemId The ID of the item being purchased.
     * @param price The price of the item.
     * @param status The status of the transaction (e.g., "Completed").
     * @return true if the transaction was successfully created, false otherwise.
     */
    public static boolean createTransaction(int userId, int itemId, double price, String status) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to insert a new transaction into the database
            String query = "INSERT INTO transactions (user_id, item_id, price, status, timestamp) VALUES (?, ?, ?, ?, NOW())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            statement.setDouble(3, price);
            statement.setString(4, status);

            // Execute the query and check if the transaction was inserted
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
        return false; // Return false if an error occurs
    }

    /**
     * Retrieves the list of transactions for a specific user.
     * @param userId The ID of the user whose transactions are to be retrieved.
     * @return A list of transactions for the user.
     */
    public static List<Transaction> getUserTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to get the user's transactions ordered by timestamp
            String query = "SELECT id AS transaction_id, user_id, item_id, price, status, timestamp FROM transactions WHERE user_id = ? ORDER BY timestamp DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            // Execute the query and populate the list of transactions
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(new Transaction(
                        resultSet.getInt("transaction_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("item_id"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("timestamp").toLocalDateTime() // Convert timestamp to LocalDateTime
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
        return transactions; // Return the list of transactions
    }

    /**
     * Updates the status of a transaction.
     * @param transactionId The ID of the transaction to update.
     * @param newStatus The new status for the transaction.
     * @return true if the transaction status was successfully updated, false otherwise.
     */
    public static boolean updateTransactionStatus(int transactionId, String newStatus) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to update the status of a transaction
            String query = "UPDATE transactions SET status = ? WHERE transaction_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newStatus);
            statement.setInt(2, transactionId);

            // Execute the update query and check if the status was updated
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
        return false; // Return false if an error occurs
    }

    /**
     * Deletes a transaction from the database.
     * @param transactionId The ID of the transaction to delete.
     * @return true if the transaction was successfully deleted, false otherwise.
     */
    public static boolean deleteTransaction(int transactionId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to delete the transaction from the database
            String query = "DELETE FROM transactions WHERE transaction_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transactionId);

            // Execute the delete query and check if the transaction was deleted
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
        return false; // Return false if an error occurs
    }

    /**
     * Retrieves the highest offer price for a specific item.
     * @param itemId The ID of the item.
     * @return The highest offer price for the item, or 0 if no offers exist.
     */
    public static double getHighestOffer(int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to get the highest offer price for a given item
            String query = "SELECT MAX(offer_price) AS highest_offer FROM offers WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);

            // Execute the query and return the highest offer price
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("highest_offer");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
        return 0; // Return 0 if no offers exist for the item
    }

    /**
     * Submits a new offer for an item.
     * @param userId The ID of the user making the offer.
     * @param itemId The ID of the item being offered on.
     * @param offerPrice The price being offered for the item.
     * @return true if the offer was successfully submitted, false otherwise.
     */
    public static boolean submitOffer(int userId, int itemId, double offerPrice) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to insert a new offer into the offers table
            String query = "INSERT INTO offers (user_id, item_id, offer_price, timestamp) VALUES (?, ?, ?, NOW())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            statement.setDouble(3, offerPrice);

            // Execute the insert query and check if the offer was inserted
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
        return false; // Return false if an error occurs
    }

    /**
     * Retrieves the list of offered items (approved items with offers).
     * @return A list of offers made on approved items.
     */
    public static List<Offer> getOfferedItems() {
        List<Offer> offers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to get offers for approved items
            String query = "SELECT o.offer_id AS offer_id, i.name AS item_name, i.category, i.price AS initial_price, o.offer_price " +
                           "FROM offers o JOIN items i ON o.item_id = i.id WHERE i.status = 'approved'";
            PreparedStatement statement = connection.prepareStatement(query);

            // Execute the query and populate the list of offers
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                offers.add(new Offer(
                        resultSet.getInt("offer_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("initial_price"),
                        resultSet.getDouble("offer_price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
        return offers; // Return the list of offers
    }

    /**
     * Accepts an offer, creates a transaction, and updates the item status to "Sold".
     * @param offerId The ID of the offer to accept.
     * @return true if the offer was successfully accepted, false otherwise.
     */
    public static boolean acceptOffer(int offerId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to fetch the offer details from the database
            String fetchQuery = "SELECT item_id, user_id, offer_price FROM offers WHERE offer_id = ?";
            PreparedStatement fetchStmt = connection.prepareStatement(fetchQuery);
            fetchStmt.setInt(1, offerId);

            ResultSet resultSet = fetchStmt.executeQuery();
            if (!resultSet.next()) {
                return false; // Return false if the offer does not exist
            }

            // Get the details of the offer
            int itemId = resultSet.getInt("item_id");
            int userId = resultSet.getInt("user_id");
            double offerPrice = resultSet.getDouble("offer_price");

            // Create a transaction for the accepted offer
            String transactionQuery = "INSERT INTO transactions (user_id, item_id, price, status, timestamp) VALUES (?, ?, ?, 'Completed', NOW())";
            PreparedStatement transactionStmt = connection.prepareStatement(transactionQuery);
            transactionStmt.setInt(1, userId);
            transactionStmt.setInt(2, itemId);
            transactionStmt.setDouble(3, offerPrice);
            transactionStmt.executeUpdate();

            // Delete the accepted offer from the offers table
            String deleteOfferQuery = "DELETE FROM offers WHERE offer_id = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteOfferQuery);
            deleteStmt.setInt(1, offerId);
            deleteStmt.executeUpdate();

            // Update the item status to "Sold"
            String updateItemQuery = "UPDATE items SET status = 'Sold' WHERE id = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateItemQuery);
            updateStmt.setInt(1, itemId);
            updateStmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
        return false; // Return false if an error occurs
    }

    /**
     * Declines an offer and removes it from the database.
     * @param offerId The ID of the offer to decline.
     * @return true if the offer was successfully declined, false otherwise.
     */
    public static boolean declineOffer(int offerId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to delete the declined offer from the database
            String query = "DELETE FROM offers WHERE offer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, offerId);

            // Execute the delete query and check if the offer was deleted
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
        return false; // Return false if an error occurs
    }
}
