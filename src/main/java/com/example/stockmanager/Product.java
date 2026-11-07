package com.example.stockmanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String category;
    private double buyingPrice;
    private double sellingPrice;
    private int quantity;
    private String sku;
    private int minStock;
    private String supplier;

    public Product(String name, String category, double buyingPrice, double sellingPrice, int quantity, String sku, int minStock, String supplier) {
        this.name = name;
        this.category = category;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.sku = sku != null && !sku.isEmpty() ? sku : generateDefaultSku();
        this.minStock = minStock > 0 ? minStock : 5;
        this.supplier = supplier != null ? supplier : "General Supplier";
    }

    private static String generateDefaultSku() {
        return "SKU-" + ((int) (Math.random() * 900000) + 100000);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getBuyingPrice() { return buyingPrice; }
    public void setBuyingPrice(double buyingPrice) { this.buyingPrice = buyingPrice; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public int getMinStock() { return minStock; }
    public void setMinStock(int minStock) { this.minStock = minStock; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public double getProfitPerItem() { return sellingPrice - buyingPrice; }
    public double getProfitMargin() { return sellingPrice > 0 ? (getProfitPerItem() / sellingPrice) * 100.0 : 0; }
    public double getTotalStockValue() { return sellingPrice * quantity; }
}
