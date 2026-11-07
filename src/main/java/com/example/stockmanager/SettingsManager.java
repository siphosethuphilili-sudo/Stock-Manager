package com.example.stockmanager;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    private static final String PREF_NAME = "stock_manager_prefs";
    private static final String KEY_LOW_STOCK_THRESHOLD = "low_stock_threshold";
    private static final String KEY_CURRENCY_SYMBOL = "currency_symbol";

    private final SharedPreferences prefs;

    public SettingsManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public int getLowStockThreshold() {
        return prefs.getInt(KEY_LOW_STOCK_THRESHOLD, 5);
    }

    public void setLowStockThreshold(int threshold) {
        prefs.edit().putInt(KEY_LOW_STOCK_THRESHOLD, threshold).apply();
    }

    public String getCurrencySymbol() {
        return prefs.getString(KEY_CURRENCY_SYMBOL, "R");
    }

    public void setCurrencySymbol(String symbol) {
        prefs.edit().putString(KEY_CURRENCY_SYMBOL, symbol).apply();
    }

    public String formatCurrency(double amount) {
        return String.format(java.util.Locale.getDefault(), "%s %.2f", getCurrencySymbol(), amount);
    }
}
