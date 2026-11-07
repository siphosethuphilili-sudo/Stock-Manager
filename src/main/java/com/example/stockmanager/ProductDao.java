package com.example.stockmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    long insert(Product product);

    @Insert
    void insertAll(List<Product> products);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM products ORDER BY name ASC")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products ORDER BY name ASC")
    List<Product> getAllProductsList();

    @Query("SELECT * FROM products WHERE id = :id")
    Product getProductById(int id);

    @Query("SELECT * FROM products WHERE sku = :sku LIMIT 1")
    Product getProductBySku(String sku);

    @Query("SELECT * FROM products WHERE name LIKE '%' || :search || '%' OR category LIKE '%' || :search || '%' OR sku LIKE '%' || :search || '%' ORDER BY name ASC")
    LiveData<List<Product>> searchProducts(String search);

    @Query("SELECT COUNT(*) FROM products")
    LiveData<Integer> getProductCount();

    @Query("SELECT COUNT(*) FROM products WHERE quantity < 5")
    LiveData<Integer> getLowStockCount();

    @Query("SELECT COUNT(*) FROM products WHERE quantity < :threshold")
    LiveData<Integer> getLowStockCountWithThreshold(int threshold);

    @Query("SELECT * FROM products WHERE quantity < 5 ORDER BY quantity ASC")
    LiveData<List<Product>> getLowStockProducts();

    @Query("SELECT * FROM products WHERE quantity < :threshold ORDER BY quantity ASC")
    LiveData<List<Product>> getLowStockProductsWithThreshold(int threshold);

    @Query("SELECT SUM(sellingPrice * quantity) FROM products")
    LiveData<Double> getTotalInventoryValue();

    @Query("SELECT SUM(buyingPrice * quantity) FROM products")
    LiveData<Double> getTotalCostValue();

    @Query("UPDATE products SET quantity = :newQty WHERE id = :id")
    void updateQuantity(int id, int newQty);

    @Query("DELETE FROM products")
    void deleteAllProducts();
}
