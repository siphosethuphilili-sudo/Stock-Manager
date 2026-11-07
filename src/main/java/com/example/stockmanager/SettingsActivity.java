package com.example.stockmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executors;

public class SettingsActivity extends AppCompatActivity {

    private AppDatabase db;
    private SettingsManager settings;
    private TextInputEditText etThreshold, etCurrency;
    private ActivityResultLauncher<String[]> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(this);
        settings = new SettingsManager(this);

        etThreshold = findViewById(R.id.etThreshold);
        etCurrency = findViewById(R.id.etCurrency);

        etThreshold.setText(String.valueOf(settings.getLowStockThreshold()));
        etCurrency.setText(settings.getCurrencySymbol());

        findViewById(R.id.btnSaveThreshold).setOnClickListener(v -> saveThreshold());
        findViewById(R.id.btnSaveCurrency).setOnClickListener(v -> saveCurrency());

        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        CsvImporter.importProductsFromUri(this, uri, db, settings, count ->
                                Toast.makeText(this, "Import completed! Added " + count + " items.", Toast.LENGTH_SHORT).show());
                    }
                }
        );

        findViewById(R.id.btnImportCsv).setOnClickListener(v ->
                filePickerLauncher.launch(new String[]{"text/csv", "text/comma-separated-values", "*/*"}));

        findViewById(R.id.btnClearSales).setOnClickListener(v -> confirmClearSales());
        findViewById(R.id.btnResetProducts).setOnClickListener(v -> confirmResetProducts());

        NavigationHelper.setupBottomNavigation(this, R.id.nav_settings);
    }

    private void saveThreshold() {
        String input = etThreshold.getText() != null ? etThreshold.getText().toString().trim() : "";
        try {
            int val = Integer.parseInt(input);
            if (val < 1) {
                Toast.makeText(this, "Threshold must be at least 1", Toast.LENGTH_SHORT).show();
                return;
            }
            settings.setLowStockThreshold(val);
            Toast.makeText(this, "Low stock threshold saved: " + val, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid threshold number", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveCurrency() {
        String symbol = etCurrency.getText() != null ? etCurrency.getText().toString().trim() : "";
        if (symbol.isEmpty()) {
            Toast.makeText(this, "Please enter a currency symbol", Toast.LENGTH_SHORT).show();
            return;
        }
        settings.setCurrencySymbol(symbol);
        Toast.makeText(this, "Currency symbol updated to: " + symbol, Toast.LENGTH_SHORT).show();
    }

    private void confirmClearSales() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Sales History")
                .setMessage("Are you sure you want to delete all sales history records?")
                .setPositiveButton("Clear All", (d, w) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        db.saleDao().deleteAllSales();
                        db.transactionDao().deleteAllTransactions();
                        runOnUiThread(() -> Toast.makeText(this, "Sales history cleared", Toast.LENGTH_SHORT).show());
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void confirmResetProducts() {
        new AlertDialog.Builder(this)
                .setTitle("Reset Inventory")
                .setMessage("Are you sure you want to delete ALL inventory products? This cannot be undone.")
                .setPositiveButton("Reset Inventory", (d, w) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        db.productDao().deleteAllProducts();
                        db.transactionDao().deleteAllTransactions();
                        runOnUiThread(() -> Toast.makeText(this, "All products deleted", Toast.LENGTH_SHORT).show());
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
