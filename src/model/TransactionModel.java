package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionModel {

    public static boolean createTransaction(int userId, int itemId, double price, String status) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO transactions (user_id, item_id, price, status, timestamp) VALUES (?, ?, ?, ?, NOW())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            statement.setDouble(3, price);
            statement.setString(4, status);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Transaction> getUserTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id AS transaction_id, user_id, item_id, price, status, timestamp FROM transactions WHERE user_id = ? ORDER BY timestamp DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(new Transaction(
                        resultSet.getInt("transaction_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("item_id"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("timestamp").toLocalDateTime()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static boolean updateTransactionStatus(int transactionId, String newStatus) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE transactions SET status = ? WHERE transaction_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newStatus);
            statement.setInt(2, transactionId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteTransaction(int transactionId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM transactions WHERE transaction_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transactionId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static double getHighestOffer(int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT MAX(offer_price) AS highest_offer FROM offers WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("highest_offer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean submitOffer(int userId, int itemId, double offerPrice) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO offers (user_id, item_id, offer_price, timestamp) VALUES (?, ?, ?, NOW())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            statement.setDouble(3, offerPrice);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static List<Offer> getOfferedItems() {
        List<Offer> offers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT o.offer_id AS offer_id, i.name AS item_name, i.category, i.price AS initial_price, o.offer_price " +
                           "FROM offers o JOIN items i ON o.item_id = i.id WHERE i.status = 'approved'";
            PreparedStatement statement = connection.prepareStatement(query);

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
            e.printStackTrace();
        }
        return offers;
    }

    public static boolean acceptOffer(int offerId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
           
            String fetchQuery = "SELECT item_id, user_id, offer_price FROM offers WHERE offer_id = ?";
            PreparedStatement fetchStmt = connection.prepareStatement(fetchQuery);
            fetchStmt.setInt(1, offerId);

            ResultSet resultSet = fetchStmt.executeQuery();
            if (!resultSet.next()) {
                return false;
            }

            int itemId = resultSet.getInt("item_id");
            int userId = resultSet.getInt("user_id");
            double offerPrice = resultSet.getDouble("offer_price");

          
            String transactionQuery = "INSERT INTO transactions (user_id, item_id, price, status, timestamp) VALUES (?, ?, ?, 'Completed', NOW())";
            PreparedStatement transactionStmt = connection.prepareStatement(transactionQuery);
            transactionStmt.setInt(1, userId);
            transactionStmt.setInt(2, itemId);
            transactionStmt.setDouble(3, offerPrice);
            transactionStmt.executeUpdate();

          
            String deleteOfferQuery = "DELETE FROM offers WHERE offer_id = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteOfferQuery);
            deleteStmt.setInt(1, offerId);
            deleteStmt.executeUpdate();

            
            String updateItemQuery = "UPDATE items SET status = 'Sold' WHERE id = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateItemQuery);
            updateStmt.setInt(1, itemId);
            updateStmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean declineOffer(int offerId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM offers WHERE offer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, offerId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
