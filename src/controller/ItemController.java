package controller;

import model.Item;
import model.ItemModel;

import java.util.List;

public class ItemController {

	public static List<Item> getApprovedItems() {
        return ItemModel.getApprovedItems();
    }

    public static String uploadItem(String name, String category, String size, double price) {
    	if (name.isEmpty() || name.length() < 3) {
            return "Item name must be at least 3 characters long and cannot be empty.";
        }

    	if (category.isEmpty() || category.length() < 3) {
            return "Item category must be at least 3 characters long and cannot be empty.";
        }
    	
    	if (size.isEmpty()) {
            return "Item size cannot be empty.";
        }
    	
    	if (price <= 0) {
            return "Price must be a positive number and cannot be zero.";
        }

    	boolean isInserted = ItemModel.insertItem(name, category, size, price);
    	return isInserted ? "Item uploaded successfully!" : "Failed to upload item.";
    }

    public static String deleteItem(int itemId) {
        boolean isDeleted = ItemModel.deleteItem(itemId);
        return isDeleted ? "Item deleted successfully!" : "Failed to delete item.";
    }
    
    public static String editItem(int itemId, String name, String category, String size, double price) {
    	if (name.isEmpty() || name.length() < 3) {
            return "Item name must be at least 3 characters long and cannot be empty.";
        }
    	
    	if (category.isEmpty() || category.length() < 3) {
            return "Item category must be at least 3 characters long and cannot be empty.";
        }
    	
    	if (size.isEmpty()) {
            return "Item size cannot be empty.";
        }

    	if (price <= 0) {
            return "Price must be a positive number and cannot be zero.";
        }

        boolean isUpdated = ItemModel.editItem(itemId, name, category, size, price);
        return isUpdated ? "Item updated successfully!" : "Failed to update item.";
    }
}
