package controller;

import model.Item;
import model.ItemModel;

import java.util.List;

public class ItemController {

    /**
     * Retrieves a list of approved items.
     * @return A list of approved items fetched from the model.
     */
	public static List<Item> getApprovedItems() {
        // Get approved items from the ItemModel
        return ItemModel.getApprovedItems();
    }

    /**
     * Uploads a new item after validating its details.
     * Validates the item name, category, size, and price before calling the model to insert the item.
     * @param name The name of the item.
     * @param category The category of the item.
     * @param size The size of the item.
     * @param price The price of the item.
     * @return A message indicating whether the item was successfully uploaded or not.
     */
    public static String uploadItem(String name, String category, String size, double price) {
        // Validate item name
    	if (name.isEmpty() || name.length() < 3) {
            return "Item name must be at least 3 characters long and cannot be empty.";
        }

        // Validate item category
    	if (category.isEmpty() || category.length() < 3) {
            return "Item category must be at least 3 characters long and cannot be empty.";
        }
    	
        // Validate item size
    	if (size.isEmpty()) {
            return "Item size cannot be empty.";
        }
    	
        // Validate item price
    	if (price <= 0) {
            return "Price must be a positive number and cannot be zero.";
        }

        // Attempt to insert the item into the database using the ItemModel
    	boolean isInserted = ItemModel.insertItem(name, category, size, price);
    	return isInserted ? "Item uploaded successfully!" : "Failed to upload item."; // Return success or failure message
    }

    /**
     * Deletes an item based on the provided item ID.
     * @param itemId The ID of the item to delete.
     * @return A message indicating whether the item was successfully deleted or not.
     */
    public static String deleteItem(int itemId) {
        // Attempt to delete the item using the ItemModel
        boolean isDeleted = ItemModel.deleteItem(itemId);
        return isDeleted ? "Item deleted successfully!" : "Failed to delete item."; // Return success or failure message
    }
    
    /**
     * Edits the details of an existing item.
     * Validates the updated item details before calling the model to update the item.
     * @param itemId The ID of the item to edit.
     * @param name The updated name of the item.
     * @param category The updated category of the item.
     * @param size The updated size of the item.
     * @param price The updated price of the item.
     * @return A message indicating whether the item was successfully updated or not.
     */
    public static String editItem(int itemId, String name, String category, String size, double price) {
        // Validate item name
    	if (name.isEmpty() || name.length() < 3) {
            return "Item name must be at least 3 characters long and cannot be empty.";
        }
    	
        // Validate item category
    	if (category.isEmpty() || category.length() < 3) {
            return "Item category must be at least 3 characters long and cannot be empty.";
        }
    	
        // Validate item size
    	if (size.isEmpty()) {
            return "Item size cannot be empty.";
        }

        // Validate item price
    	if (price <= 0) {
            return "Price must be a positive number and cannot be zero.";
        }

        // Attempt to update the item using the ItemModel
        boolean isUpdated = ItemModel.editItem(itemId, name, category, size, price);
        return isUpdated ? "Item updated successfully!" : "Failed to update item."; // Return success or failure message
    }
}
