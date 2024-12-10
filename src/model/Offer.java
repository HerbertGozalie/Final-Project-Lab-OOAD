package model;

public class Offer {
    // Attributes
    private int offerId; // Unique ID for the offer
    private int userId; // ID of the user making the offer
    private int itemId; // ID of the item for which the offer is made
    private double offerPrice; // Price offered by the user
    private String status; // Status of the offer (e.g., "Pending", "Accepted", "Declined")
    private String reason; // Reason for declining the offer (optional)

    // Constructor
    public Offer(int offerId, int userId, int itemId, double offerPrice, String status, String reason) {
        this.offerId = offerId;
        this.userId = userId;
        this.itemId = itemId;
        this.offerPrice = offerPrice;
        this.status = status;
        this.reason = reason;
    }

    // Overloaded constructor (for creating a new offer without ID or reason)
    public Offer(int userId, int itemId, double offerPrice, String status) {
        this.userId = userId;
        this.itemId = itemId;
        this.offerPrice = offerPrice;
        this.status = status;
    }

    // Getters and Setters
    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
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

    public double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    // Method to display offer details
    @Override
    public String toString() {
        return "Offer{" +
                "offerId=" + offerId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", offerPrice=" + offerPrice +
                ", status='" + status + '\'' +
                (reason != null ? ", reason='" + reason + '\'' : "") +
                '}';
    }
}
