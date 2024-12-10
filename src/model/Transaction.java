package model;

import java.time.LocalDateTime;

public class Transaction {
    // Attributes
    private int transactionId; // Unique ID for the transaction
    private int userId; // ID of the user involved in the transaction
    private int itemId; // ID of the item involved in the transaction
    private double price; // Final price of the transaction
    private String status; // Status of the transaction (e.g., "Completed", "Pending", "Cancelled")
    private LocalDateTime timestamp; // Timestamp of the transaction

    // Constructor
    public Transaction(int transactionId, int userId, int itemId, double price, String status, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Overloaded constructor (for creating a new transaction without ID)
    public Transaction(int userId, int itemId, double price, String status) {
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
        this.status = status;
        this.timestamp = LocalDateTime.now(); // Automatically set the current timestamp
    }

    // Getters and Setters
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

    // Method to display transaction details
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
