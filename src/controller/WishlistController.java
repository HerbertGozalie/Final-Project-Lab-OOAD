package controller;

import model.Item;
import model.WishlistModel;

import java.util.List;

public class WishlistController {

	public static String addItemToWishlist(int userId, int itemId) {
	    if (userId <= 0 || itemId <= 0) {
	        return "Invalid user or item ID.";
	    }

	    boolean isAdded = WishlistModel.addItemToWishlist(userId, itemId);
	    return isAdded ? "Item added to wishlist!" : "Failed to add item to wishlist. Ensure the user and item exist.";
	}


    public static String removeItemFromWishlist(int userId, int itemId) {
        if (userId <= 0 || itemId <= 0) {
            return "Invalid user or item ID.";
        }

        boolean isRemoved = WishlistModel.removeItemFromWishlist(userId, itemId);
        return isRemoved ? "Item removed from wishlist!" : "Failed to remove item from wishlist.";
    }

    public static List<Item> getWishlistItems(int userId) {
        if (userId <= 0) {
            return null;
        }
        return WishlistModel.getWishlistItems(userId);
    }
}
