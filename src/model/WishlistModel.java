package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WishlistModel {

	public static boolean addItemToWishlist(int userId, int itemId) {
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        
	        String userQuery = "SELECT COUNT(*) FROM users WHERE id = ?";
	        PreparedStatement userStatement = connection.prepareStatement(userQuery);
	        userStatement.setInt(1, userId);
	        ResultSet userResult = userStatement.executeQuery();
	        if (userResult.next() && userResult.getInt(1) == 0) {
	            System.out.println("User does not exist.");
	            return false;
	        }

	        String itemQuery = "SELECT COUNT(*) FROM items WHERE id = ?";
	        PreparedStatement itemStatement = connection.prepareStatement(itemQuery);
	        itemStatement.setInt(1, itemId);
	        ResultSet itemResult = itemStatement.executeQuery();
	        if (itemResult.next() && itemResult.getInt(1) == 0) {
	            System.out.println("Item does not exist.");
	            return false;
	        }

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
                        "Wishlist",
                        userId
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wishlist;
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

}
