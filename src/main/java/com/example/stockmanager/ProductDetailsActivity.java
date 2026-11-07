package com.example.stockmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.concurrent.Executors;

public class ProductDetailsActivity extends AppCompatActivity {

    private AppDatabase db;
    private SettingsManager settings;
    private Product product;
    private int productId;
    private TextView tvName, tvCategory, tvBuy, tvSell, tvQty, tvProfit, tvTotalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Product Details");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = AppDatabase.getInstance(this);
        settings = new SettingsManager(this);
        productId = getIntent().getIntExtra("product_id", -1);

        tvName = findViewById(R.id.tvDetailName);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvBuy = findViewById(R.id.tvDetailBuy);
        tvSell = findViewById(R.id.tvDetailSell);
        tvQty = findViewById(R.id.tvDetailQty);
        tvProfit = findViewById(R.id.tvDetailProfit);
        tvTotalValue = findViewById(R.id.tvDetailValue);

        MaterialButton btnSale = findViewById(R.id.btnRecordSale);
        MaterialButton btnEdit = findViewById(R.id.btnEdit);
        MaterialButton btnDelete = findViewById(R.id.btnDelete);

        btnSale.setOnClickListener(v -> showSaleDialog());
        btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(this, EditProductActivity.class);
            i.putExtra("product_id", productId);
            startActivity(i);
        });
        btnDelete.setOnClickListener(v -> confirmDelete());

        loadProduct();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProduct();
    }

    private void loadProduct() {
        Executors.newSingleThreadExecutor().execute(() -> {
            product = db.productDao().getProductById(productId);
            runOnUiThread(() -> {
                if (product == null) {
                    finish();
                    return;
                }

                tvName.setText(product.getName());
                tvCategory.setText(product.getCategory());
                tvBuy.setText(settings.formatCurrency(product.getBuyingPrice()));
                tvSell.setText(settings.formatCurrency(product.getSellingPrice()));
                tvQty.setText(String.valueOf(product.getQuantity()));
                tvProfit.setText(settings.formatCurrency(product.getProfitPerItem()));
                tvTotalValue.setText(settings.formatCurrency(product.getSellingPrice() * product.getQuantity()));

                int threshold = settings.getLowStockThreshold();
                if (product.getQuantity() < threshold) {
                    tvQty.setTextColor(ContextCompat.getColor(this, R.color.low_stock));
                } else {
                    tvQty.setTextColor(ContextCompat.getColor(this, R.color.in_stock));
                }
            });
        });
    }

    private void showSaleDialog() {
        if (product == null) return;
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_record_sale, null);
        EditText etQty = v.findViewById(R.id.etSaleQty);
        TextView tvTotal = v.findViewById(R.id.tvSaleTotal);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Record Sale")
                .setView(v)
                .setPositiveButton("Confirm", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d -> {
            android.widget.Button pos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            pos.setOnClickListener(x -> {
                String s = etQty.getText().toString().trim();
                if (s.isEmpty()) { etQty.setError("Enter quantity"); return; }
                int qty;
                try { qty = Integer.parseInt(s); }
                catch (NumberFormatException e) { etQty.setError("Invalid"); return; }

                if (qty <= 0) { etQty.setError("Must be > 0"); return; }
                if (qty > product.getQuantity()) {
                    etQty.setError("Only " + product.getQuantity() + " in stock");
                    return;
                }
                double total = qty * product.getSellingPrice();
                Executors.newSingleThreadExecutor().execute(() -> {
                    db.productDao().updateQuantity(product.getId(), product.getQuantity() - qty);
                    Sale sale = new Sale(product.getId(), product.getName(), qty, total, System.currentTimeMillis());
                    db.saleDao().insert(sale);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Sale recorded: " + settings.formatCurrency(total), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadProduct();
                    });
                });
            });
        });

        etQty.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) {
                try {
                    int q = Integer.parseInt(s.toString());
                    tvTotal.setText(settings.formatCurrency(q * product.getSellingPrice()));
                } catch (Exception e) { tvTotal.setText(settings.formatCurrency(0.0)); }
            }
            @Override public void afterTextChanged(android.text.Editable s) {}
        });

        dialog.show();
    }

    private void confirmDelete() {
        if (product == null) return;
        new AlertDialog.Builder(this)
                .setTitle("Delete Product")
                .setMessage("Delete \"" + product.getName() + "\"? This cannot be undone.")
                .setPositiveButton("Delete", (d, w) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        db.productDao().delete(product);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
