package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WishlistModel {

    /**
     * Adds an item to the user's wishlist.
     * It checks if the user and item exist before performing the insertion.
     * @param userId The ID of the user.
     * @param itemId The ID of the item to be added.
     * @return true if the item was successfully added, false otherwise.
     */
	public static boolean addItemToWishlist(int userId, int itemId) {
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        
	        // Check if the user exists
	        String userQuery = "SELECT COUNT(*) FROM users WHERE id = ?";
	        PreparedStatement userStatement = connection.prepareStatement(userQuery);
	        userStatement.setInt(1, userId);
	        ResultSet userResult = userStatement.executeQuery();
	        if (userResult.next() && userResult.getInt(1) == 0) {
	            System.out.println("User does not exist.");
	            return false;
	        }

	        // Check if the item exists
	        String itemQuery = "SELECT COUNT(*) FROM items WHERE id = ?";
	        PreparedStatement itemStatement = connection.prepareStatement(itemQuery);
	        itemStatement.setInt(1, itemId);
	        ResultSet itemResult = itemStatement.executeQuery();
	        if (itemResult.next() && itemResult.getInt(1) == 0) {
	            System.out.println("Item does not exist.");
	            return false;
	        }

	        // Insert the item into the wishlist
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

    /**
     * Removes an item from the user's wishlist.
     * @param userId The ID of the user.
     * @param itemId The ID of the item to be removed.
     * @return true if the item was successfully removed, false otherwise.
     */
    public static boolean removeItemFromWishlist(int userId, int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Delete the item from the wishlist
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

    /**
     * Retrieves the list of items in the user's wishlist.
     * @param userId The ID of the user.
     * @return A list of items in the user's wishlist.
     */
    public static List<Item> getWishlistItems(int userId) {
        List<Item> wishlist = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to get items in the user's wishlist
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
                        "Wishlist",  // A placeholder value for item status
                        userId
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wishlist;
    }

}
