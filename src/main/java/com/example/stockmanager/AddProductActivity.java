package com.example.stockmanager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.concurrent.Executors;

public class AddProductActivity extends AppCompatActivity {

    private SettingsManager settings;
    private TextView tvPreviewProfit, tvPreviewMargin;

    private static final String[] CATEGORIES = new String[]{
            "Electronics", "Groceries", "Clothing", "Hardware", "Food & Beverage", "Beauty & Health", "General", "Other"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        settings = new SettingsManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Product");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        TextInputEditText etName = findViewById(R.id.etName);
        TextInputEditText etSku = findViewById(R.id.etSku);
        TextInputEditText etSupplier = findViewById(R.id.etSupplier);
        AutoCompleteTextView etCategory = findViewById(R.id.etCategory);
        TextInputEditText etBuy = findViewById(R.id.etBuyPrice);
        TextInputEditText etSell = findViewById(R.id.etSellPrice);
        TextInputEditText etQty = findViewById(R.id.etQuantity);

        tvPreviewProfit = findViewById(R.id.tvPreviewProfit);
        tvPreviewMargin = findViewById(R.id.tvPreviewMargin);

        // Setup Category Options Dropdown
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, CATEGORIES
        );
        etCategory.setAdapter(categoryAdapter);

        // Live calculation listener
        TextWatcher priceWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateProfit(getText(etBuy), getText(etSell));
            }
            @Override public void afterTextChanged(Editable s) {}
        };
        etBuy.addTextChangedListener(priceWatcher);
        etSell.addTextChangedListener(priceWatcher);

        MaterialButton btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            String name = getText(etName);
            String sku = getText(etSku);
            String supplier = getText(etSupplier);
            String category = etCategory.getText() == null ? "" : etCategory.getText().toString().trim();
            String buyStr = getText(etBuy);
            String sellStr = getText(etSell);
            String qtyStr = getText(etQty);

            if (name.isEmpty() || category.isEmpty() || buyStr.isEmpty() || sellStr.isEmpty() || qtyStr.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double buy = Double.parseDouble(buyStr);
                double sell = Double.parseDouble(sellStr);
                int qty = Integer.parseInt(qtyStr);

                Product p = new Product(name, category, buy, sell, qty, sku, settings.getLowStockThreshold(), supplier);
                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase.getInstance(this).productDao().insert(p);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                });
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateProfit(String buyStr, String sellStr) {
        try {
            double buy = Double.parseDouble(buyStr);
            double sell = Double.parseDouble(sellStr);
            double profit = sell - buy;
            double margin = sell > 0 ? (profit / sell) * 100.0 : 0.0;

            tvPreviewProfit.setText(settings.formatCurrency(profit));
            tvPreviewMargin.setText(String.format(Locale.getDefault(), "%.1f%%", margin));
        } catch (Exception e) {
            tvPreviewProfit.setText(settings.formatCurrency(0.0));
            tvPreviewMargin.setText("0.0%");
        }
    }

    private String getText(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}
