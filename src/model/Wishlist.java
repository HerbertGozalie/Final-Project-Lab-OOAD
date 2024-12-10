package model;

public class Wishlist {
   
	// Attributes
    private int wishlistId;
    private int userId;
    private int itemId;
    
    // Constructor
    public Wishlist(int wishlistId, int userId, int itemId) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.itemId = itemId;
    }

    // Getter Setter
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
}
