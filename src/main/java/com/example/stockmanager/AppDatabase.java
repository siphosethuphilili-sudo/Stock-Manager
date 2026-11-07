package com.example.stockmanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, Sale.class, StockTransaction.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;

    public abstract ProductDao productDao();
    public abstract SaleDao saleDao();
    public abstract StockTransactionDao transactionDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "stock_manager_db"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
