package com.example.stockmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReorderAssistantActivity extends AppCompatActivity implements ProductAdapter.OnProductActionListener {

    private AppDatabase db;
    private SettingsManager settings;
    private ProductAdapter adapter;
    private TextView tvReorderCount, tvReorderEstCost;
    private List<Product> lowStockList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reorder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = AppDatabase.getInstance(this);
        settings = new SettingsManager(this);

        tvReorderCount = findViewById(R.id.tvReorderCount);
        tvReorderEstCost = findViewById(R.id.tvReorderEstCost);

        RecyclerView rv = findViewById(R.id.rvReorderItems);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this);
        adapter.setLowStockThreshold(settings.getLowStockThreshold());
        adapter.setCurrencySymbol(settings.getCurrencySymbol());
        rv.setAdapter(adapter);

        findViewById(R.id.btnSendPurchaseOrder).setOnClickListener(v -> sharePurchaseOrder());

        loadReorderData();
    }

    private void loadReorderData() {
        int threshold = settings.getLowStockThreshold();
        db.productDao().getLowStockProductsWithThreshold(threshold).observe(this, products -> {
            this.lowStockList = products;
            adapter.setProducts(products);

            int count = products != null ? products.size() : 0;
            tvReorderCount.setText(count + " products require reordering");

            double estCost = 0;
            if (products != null) {
                for (Product p : products) {
                    int needed = Math.max(10, (p.getMinStock() * 2) - p.getQuantity());
                    estCost += needed * p.getBuyingPrice();
                }
            }
            tvReorderEstCost.setText("Est. Restock Cost: " + settings.formatCurrency(estCost));
        });
    }

    private void sharePurchaseOrder() {
        if (lowStockList == null || lowStockList.isEmpty()) {
            Toast.makeText(this, "No low stock products to reorder!", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder po = new StringBuilder();
        po.append("PURCHASE ORDER - ").append("STOCK MANAGER\n");
        po.append("Date: ").append(new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date())).append("\n\n");
        po.append("REORDER ITEMS REQUIRED:\n");

        double totalEst = 0;
        for (Product p : lowStockList) {
            int suggestQty = Math.max(10, (p.getMinStock() * 2) - p.getQuantity());
            double itemCost = suggestQty * p.getBuyingPrice();
            totalEst += itemCost;

            po.append("• ").append(p.getName()).append(" (SKU: ").append(p.getSku()).append(")\n");
            po.append("  Current Stock: ").append(p.getQuantity()).append(" | Order Qty: ").append(suggestQty).append("\n");
            po.append("  Supplier: ").append(p.getSupplier()).append("\n\n");
        }

        po.append("Estimated Purchase Order Value: ").append(settings.formatCurrency(totalEst)).append("\n\n");
        po.append("Please confirm availability and dispatch dates.");

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Purchase Order - Low Stock Reorder");
        sendIntent.putExtra(Intent.EXTRA_TEXT, po.toString());

        startActivity(Intent.createChooser(sendIntent, "Send Supplier Purchase Order"));
    }

    @Override
    public void onProductClick(Product product) {
        Intent i = new Intent(this, ProductDetailsActivity.class);
        i.putExtra("product_id", product.getId());
        startActivity(i);
    }

    @Override
    public void onRestockClick(Product product) {
        StockDialogHelper.showRestockDialog(this, db, settings, product, this::loadReorderData);
    }

    @Override
    public void onSaleClick(Product product) {
        StockDialogHelper.showSaleDialog(this, db, settings, product, this::loadReorderData);
    }
}
