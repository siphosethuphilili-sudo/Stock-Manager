package com.example.stockmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SaleDao {
    @Insert
    void insert(Sale sale);

    @Query("SELECT * FROM sales ORDER BY date DESC")
    LiveData<List<Sale>> getAllSales();

    @Query("SELECT SUM(totalPrice) FROM sales WHERE date >= :startOfDay")
    LiveData<Double> getTodaySales(long startOfDay);

    @Query("SELECT SUM(totalPrice) FROM sales WHERE date >= :startOfMonth")
    LiveData<Double> getMonthSales(long startOfMonth);

    @Query("SELECT SUM(totalPrice) FROM sales")
    LiveData<Double> getTotalSalesRevenue();

    @Query("SELECT COUNT(*) FROM sales")
    LiveData<Integer> getTotalSalesCount();

    @Query("DELETE FROM sales")
    void deleteAllSales();
}
