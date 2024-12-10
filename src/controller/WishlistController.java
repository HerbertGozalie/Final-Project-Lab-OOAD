package controller;

import model.Item;
import model.WishlistModel;

import java.util.List;

public class WishlistController {

    /**
     * Adds an item to the user's wishlist.
     * Validates user and item IDs before attempting to add the item.
     * @param userId The ID of the user adding the item.
     * @param itemId The ID of the item to add.
     * @return A message indicating whether the item was successfully added or not.
     */
	public static String addItemToWishlist(int userId, int itemId) {
	    // Validate user and item IDs
	    if (userId <= 0 || itemId <= 0) {
	        return "Invalid user or item ID."; // Return error if IDs are invalid
	    }

	    // Attempt to add the item to the wishlist using the WishlistModel
	    boolean isAdded = WishlistModel.addItemToWishlist(userId, itemId);
	    return isAdded ? "Item added to wishlist!" : "Failed to add item to wishlist. Ensure the user and item exist.";
	}

    /**
     * Removes an item from the user's wishlist.
     * Validates user and item IDs before attempting to remove the item.
     * @param userId The ID of the user removing the item.
     * @param itemId The ID of the item to remove.
     * @return A message indicating whether the item was successfully removed or not.
     */
    public static String removeItemFromWishlist(int userId, int itemId) {
        // Validate user and item IDs
        if (userId <= 0 || itemId <= 0) {
            return "Invalid user or item ID."; // Return error if IDs are invalid
        }

        // Attempt to remove the item from the wishlist using the WishlistModel
        boolean isRemoved = WishlistModel.removeItemFromWishlist(userId, itemId);
        return isRemoved ? "Item removed from wishlist!" : "Failed to remove item from wishlist.";
    }

    /**
     * Retrieves the list of items in the user's wishlist.
     * @param userId The ID of the user whose wishlist items are to be fetched.
     * @return A list of items in the user's wishlist, or null if the user ID is invalid.
     */
    public static List<Item> getWishlistItems(int userId) {
        // Validate user ID
        if (userId <= 0) {
            return null; // Return null if the user ID is invalid
        }
        // Retrieve the wishlist items using the WishlistModel
        return WishlistModel.getWishlistItems(userId);
    }
}
