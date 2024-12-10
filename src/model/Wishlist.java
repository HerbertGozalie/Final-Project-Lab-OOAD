package model;

public class Wishlist {
    // Attributes
    private int wishlistId; // Unique ID for the wishlist entry
    private int userId; // ID of the user who added the item to the wishlist
    private int itemId; // ID of the item added to the wishlist

    // Constructor
    public Wishlist(int wishlistId, int userId, int itemId) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.itemId = itemId;
    }

    // Overloaded constructor (for creating a new wishlist entry without ID)
    public Wishlist(int userId, int itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    // Getters and Setters
    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    // Method to display wishlist details
    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                '}';
    }
}
