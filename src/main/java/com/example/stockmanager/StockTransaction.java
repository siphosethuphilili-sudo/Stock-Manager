package com.example.stockmanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stock_transactions")
public class StockTransaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int productId;
    private String productName;
    private String type; // "SALE" or "RESTOCK"
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private long timestamp;
    private String note;

    public StockTransaction(int productId, String productName, String type, int quantity, double unitPrice, double totalAmount, long timestamp, String note) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
        this.timestamp = timestamp;
        this.note = note != null ? note : "";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
