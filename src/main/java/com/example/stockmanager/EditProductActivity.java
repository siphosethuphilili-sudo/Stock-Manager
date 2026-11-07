package com.example.stockmanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executors;

public class EditProductActivity extends AppCompatActivity {

    private Product product;
    private AppDatabase db;

    private static final String[] CATEGORIES = new String[]{
            "Electronics", "Groceries", "Clothing", "Hardware", "Food & Beverage", "Beauty & Health", "General", "Other"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Product");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = AppDatabase.getInstance(this);
        int id = getIntent().getIntExtra("product_id", -1);

        TextInputEditText etName = findViewById(R.id.etName);
        AutoCompleteTextView etCategory = findViewById(R.id.etCategory);
        TextInputEditText etBuy = findViewById(R.id.etBuyPrice);
        TextInputEditText etSell = findViewById(R.id.etSellPrice);
        TextInputEditText etQty = findViewById(R.id.etQuantity);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, CATEGORIES
        );
        etCategory.setAdapter(categoryAdapter);

        MaterialButton btnSave = findViewById(R.id.btnSave);
        btnSave.setText("Update Product");

        Executors.newSingleThreadExecutor().execute(() -> {
            product = db.productDao().getProductById(id);
            runOnUiThread(() -> {
                if (product == null) {
                    finish();
                    return;
                }
                etName.setText(product.getName());
                etCategory.setText(product.getCategory());
                etBuy.setText(String.valueOf(product.getBuyingPrice()));
                etSell.setText(String.valueOf(product.getSellingPrice()));
                etQty.setText(String.valueOf(product.getQuantity()));
            });
        });

        btnSave.setOnClickListener(v -> {
            if (product == null) return;
            try {
                String name = etName.getText() == null ? "" : etName.getText().toString().trim();
                String category = etCategory.getText() == null ? "" : etCategory.getText().toString().trim();
                String buyStr = etBuy.getText() == null ? "" : etBuy.getText().toString().trim();
                String sellStr = etSell.getText() == null ? "" : etSell.getText().toString().trim();
                String qtyStr = etQty.getText() == null ? "" : etQty.getText().toString().trim();

                if (name.isEmpty() || category.isEmpty() || buyStr.isEmpty() || sellStr.isEmpty() || qtyStr.isEmpty()) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                product.setName(name);
                product.setCategory(category);
                product.setBuyingPrice(Double.parseDouble(buyStr));
                product.setSellingPrice(Double.parseDouble(sellStr));
                product.setQuantity(Integer.parseInt(qtyStr));

                Executors.newSingleThreadExecutor().execute(() -> {
                    db.productDao().update(product);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                });
            } catch (Exception e) {
                Toast.makeText(this, "Invalid input format", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
