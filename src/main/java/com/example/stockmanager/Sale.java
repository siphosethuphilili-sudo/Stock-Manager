package com.example.stockmanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sales")
public class Sale {
    @PrimaryKey(autoGenerate = true)
    private int saleId;
    private int productId;
    private String productName;
    private int quantitySold;
    private double totalPrice;
    private long date;

    public Sale(int productId, String productName, int quantitySold, double totalPrice, long date) {
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantitySold() { return quantitySold; }
    public void setQuantitySold(int quantitySold) { this.quantitySold = quantitySold; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }
}
