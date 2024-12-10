package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    /**
     * Retrieves a list of approved items from the database.
     * @return A list of approved items.
     */
	public static List<Item> getApprovedItems() {
        List<Item> approvedItems = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to fetch approved items
            String query = "SELECT * FROM items WHERE status = 'approved'";
            PreparedStatement statement = connection.prepareStatement(query);

            // Execute the query and process the result set
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                approvedItems.add(new Item(
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
        return approvedItems; // Return the list of approved items
    }

    /**
     * Inserts a new item into the database with the status 'Pending'.
     * @param name The name of the item.
     * @param category The category of the item.
     * @param size The size of the item.
     * @param price The price of the item.
     * @return true if the item was successfully inserted, false otherwise.
     */
    public static boolean insertItem(String name, String category, String size, double price) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to insert a new item with 'Pending' status
            String query = "INSERT INTO items (name, category, size, price, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setString(3, size);
            statement.setDouble(4, price);

            // Execute the insert statement and check if it was successful
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
        return false; // Return false if an error occurs
    }

    /**
     * Deletes an item from the database by its ID.
     * @param itemId The ID of the item to be deleted.
     * @return true if the item was successfully deleted, false otherwise.
     */
    public static boolean deleteItem(int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to delete the item by its ID
            String query = "DELETE FROM items WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);

            // Execute the delete statement and check if it was successful
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
        return false; // Return false if an error occurs
    }

    /**
     * Edits the details of an item in the database.
     * @param itemId The ID of the item to be updated.
     * @param name The new name of the item.
     * @param category The new category of the item.
     * @param size The new size of the item.
     * @param price The new price of the item.
     * @return true if the item was successfully updated, false otherwise.
     */
    public static boolean editItem(int itemId, String name, String category, String size, double price) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to update the item's details
            String query = "UPDATE items SET name = ?, category = ?, size = ?, price = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setString(3, size);
            statement.setDouble(4, price);
            statement.setInt(5, itemId);

            // Execute the update statement and check if it was successful
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
        return false; // Return false if an error occurs
    }
}
