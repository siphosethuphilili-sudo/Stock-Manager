package com.example.stockmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StockTransactionDao {
    @Insert
    void insert(StockTransaction transaction);

    @Query("SELECT * FROM stock_transactions ORDER BY timestamp DESC")
    LiveData<List<StockTransaction>> getAllTransactions();

    @Query("SELECT * FROM stock_transactions ORDER BY timestamp DESC")
    List<StockTransaction> getAllTransactionsList();

    @Query("SELECT * FROM stock_transactions WHERE productId = :productId ORDER BY timestamp DESC")
    LiveData<List<StockTransaction>> getTransactionsForProduct(int productId);

    @Query("DELETE FROM stock_transactions")
    void deleteAllTransactions();
}
