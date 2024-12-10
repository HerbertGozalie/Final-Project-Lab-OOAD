package model;

public class Offer {
    private int offerId;
    private String itemName;
    private String category;
    private double initialPrice;
    private double offerPrice;

    public Offer(int offerId, String itemName, String category, double initialPrice, double offerPrice) {
        this.offerId = offerId;
        this.itemName = itemName;
        this.category = category;
        this.initialPrice = initialPrice;
        this.offerPrice = offerPrice;
    }

    public int getOfferId() {
        return offerId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public double getOfferPrice() {
        return offerPrice;
    }
}
