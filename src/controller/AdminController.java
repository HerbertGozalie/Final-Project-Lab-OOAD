package controller;

import model.AdminModel;
import model.Item;

import java.util.List;

public class AdminController {

    /**
     * Retrieves a list of items that are pending approval.
     * Calls the AdminModel to fetch items with 'pending' status.
     * @return A list of items that are pending approval.
     */
    public static List<Item> getPendingItems() {
        // Fetch the pending items from the AdminModel
        return AdminModel.getPendingItems();
    }

    /**
     * Approves an item by its ID.
     * Validates the item ID before attempting to approve it.
     * @param itemId The ID of the item to approve.
     * @return A message indicating whether the item was successfully approved or not.
     */
    public static String approveItem(int itemId) {
        // Validate the item ID
        if (itemId <= 0) {
            return "Invalid item ID."; // Return error if the item ID is invalid
        }

        // Attempt to approve the item using the AdminModel
        boolean isApproved = AdminModel.approveItem(itemId);
        return isApproved ? "Item approved successfully!" : "Failed to approve item."; // Return success or failure message
    }

    /**
     * Declines an item by its ID with a reason.
     * Validates the item ID and the decline reason before attempting to decline the item.
     * @param itemId The ID of the item to decline.
     * @param reason The reason for declining the item.
     * @return A message indicating whether the item was successfully declined or not.
     */
    public static String declineItem(int itemId, String reason) {
        // Validate the item ID
        if (itemId <= 0) {
            return "Invalid item ID."; // Return error if the item ID is invalid
        }

        // Validate the decline reason
        if (reason == null || reason.trim().isEmpty()) {
            return "Reason for declining cannot be empty."; // Return error if the reason is empty
        }

        // Attempt to decline the item using the AdminModel
        boolean isDeclined = AdminModel.declineItem(itemId, reason);
        return isDeclined ? "Item declined successfully!" : "Failed to decline item."; // Return success or failure message
    }
}
