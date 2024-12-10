package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

	public static List<Item> getApprovedItems() {
        List<Item> approvedItems = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM items WHERE status = 'approved'";
            PreparedStatement statement = connection.prepareStatement(query);

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
            e.printStackTrace();
        }
        return approvedItems;
    }

    public static boolean insertItem(String name, String category, String size, double price) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO items (name, category, size, price, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setString(3, size);
            statement.setDouble(4, price);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteItem(int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM items WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean editItem(int itemId, String name, String category, String size, double price) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE items SET name = ?, category = ?, size = ?, price = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setString(3, size);
            statement.setDouble(4, price);
            statement.setInt(5, itemId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
