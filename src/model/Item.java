package model;

public class Item {
    // Attributes
    private int id; // Unique ID for the item
    private String name; // Name of the item
    private String category; // Category of the item
    private String size; // Size of the item
    private double price; // Price of the item
    private String status; // Status of the item (e.g., pending, approved, declined)
    private int sellerId; // ID of the seller who uploaded the item

    // Constructor
    public Item(int id, String name, String category, String size, double price, String status, int sellerId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.size = size;
        this.price = price;
        this.status = status;
        this.sellerId = sellerId;
    }

    // Overloaded constructor (for creating a new item without an ID)
    public Item(String name, String category, String size, double price, String status, int sellerId) {
        this.name = name;
        this.category = category;
        this.size = size;
        this.price = price;
        this.status = status;
        this.sellerId = sellerId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    // Method to display item info (optional)
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", sellerId=" + sellerId +
                '}';
    }
}
