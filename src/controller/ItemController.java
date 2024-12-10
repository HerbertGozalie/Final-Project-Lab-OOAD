package controller;

import model.Item;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemController {
    public static List<Item> getPendingItems() {
        List<Item> items = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM items WHERE status = 'Pending'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                items.add(new Item(
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
            e.printStackTrace();
        }
        return items;
    }

    public static boolean approveItem(int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE items SET status = 'Approved' WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean declineItem(int itemId, String reason) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE items SET status = 'Declined', decline_reason = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, reason);
            statement.setInt(2, itemId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM items";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                items.add(new Item(
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
            e.printStackTrace();
        }
        return items;
    }

    public static boolean deleteItem(int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM items WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean uploadItem(String name, String category, String size, double price) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO items (name, category, size, price, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setString(3, size);
            statement.setDouble(4, price);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean purchaseItem(int itemId, int buyerId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Update item status to 'Purchased' and associate it with the buyer
            String query = "UPDATE items SET status = 'Purchased', buyer_id = ? WHERE id = ? AND status = 'Approved'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, buyerId); // Buyer ID who purchased the item
            statement.setInt(2, itemId); // Item ID to purchase

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static List<Item> getAvailableItems() {
        List<Item> items = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to retrieve items with status 'Approved'
            String query = "SELECT * FROM items WHERE status = 'Approved'";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                items.add(new Item(
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
            e.printStackTrace();
        }
        return items;
    }


}
