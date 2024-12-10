package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminModel {

    public static List<Item> getPendingItems() {
        List<Item> pendingItems = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM items WHERE status = 'pending'";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
            e.printStackTrace();
        }
        return pendingItems;
    }

    public static boolean approveItem(int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE items SET status = 'approved' WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean declineItem(int itemId, String reason) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE items SET status = 'declined', decline_reason = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, reason);
            statement.setInt(2, itemId);

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
