package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminModel {

    /**
     * Retrieves the list of items that are pending approval from the database.
     * @return A list of items with status 'pending'.
     */
    public static List<Item> getPendingItems() {
        List<Item> pendingItems = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to fetch items with status 'pending'
            String query = "SELECT * FROM items WHERE status = 'pending'";
            PreparedStatement statement = connection.prepareStatement(query);

            // Execute the query and process the result set
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Add each pending item to the list
                pendingItems.add(new Item(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getString("size"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status"),
                        resultSet.getInt("seller_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
        return pendingItems; // Return the list of pending items
    }

    /**
     * Approves an item and updates its status to 'approved' in the database.
     * @param itemId The ID of the item to approve.
     * @return true if the item was successfully approved, false otherwise.
     */
    public static boolean approveItem(int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to update the item status to 'approved'
            String query = "UPDATE items SET status = 'approved' WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);

            // Execute the update statement and check if any rows were affected
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
        return false; // Return false if an error occurs
    }

    /**
     * Declines an item and updates its status to 'declined' with a reason in the database.
     * @param itemId The ID of the item to decline.
     * @param reason The reason for declining the item.
     * @return true if the item was successfully declined, false otherwise.
     */
    public static boolean declineItem(int itemId, String reason) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to update the item status to 'declined' and set the decline reason
            String query = "UPDATE items SET status = 'declined', decline_reason = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, reason);
            statement.setInt(2, itemId);

            // Execute the update statement and check if any rows were affected
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
        return false; // Return false if an error occurs
    }
}
