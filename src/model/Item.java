package model;

public class Item {
    
    private int id;
    private String name;
    private String category;
    private String size;
    private double price;
    private String status;
    private int sellerId;

    public Item(int id, String name, String category, String size, double price, String status, int sellerId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.size = size;
        this.price = price;
        this.status = status;
        this.sellerId = sellerId;
    }

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

}
