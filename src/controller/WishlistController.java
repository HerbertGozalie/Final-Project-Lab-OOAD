package controller;

import model.Item;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WishlistController {

    // Add an item to the wishlist
    public static boolean addItemToWishlist(int userId, int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO wishlist (user_id, item_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, itemId);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Remove an item from the wishlist
    public static boolean removeItemFromWishlist(int userId, int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM wishlist WHERE user_id = ? AND item_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, itemId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve all wishlist items for a specific user
    public static List<Item> getWishlistItems(int userId) {
        List<Item> wishlist = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT items.id, items.name, items.category, items.size, items.price " +
                           "FROM wishlist " +
                           "JOIN items ON wishlist.item_id = items.id " +
                           "WHERE wishlist.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                wishlist.add(new Item(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getString("size"),
                        resultSet.getDouble("price"),
                        "Wishlist", // Placeholder status
                        userId // Placeholder sellerId
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wishlist;
    }

}
