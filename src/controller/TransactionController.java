package controller;

import model.Transaction;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionController {

    // Create a new transaction
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

    // Retrieve a user's transaction history
    public static List<Transaction> getUserTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM transactions WHERE user_id = ? ORDER BY timestamp DESC";
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

    // Update transaction status
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

    // Delete a transaction
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
}
