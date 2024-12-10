package model;

import java.time.LocalDateTime;

public class Transaction {
    
	// Attributes
    private int transactionId;
    private int userId;
    private int itemId;
    private double price;
    private String status;
    private LocalDateTime timestamp;

    // Constructor
    public Transaction(int transactionId, int userId, int itemId, double price, String status, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getter Setter
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
