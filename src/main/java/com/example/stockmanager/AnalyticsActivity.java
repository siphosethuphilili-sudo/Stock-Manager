package com.example.stockmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AnalyticsActivity extends AppCompatActivity {

    private AppDatabase db;
    private SettingsManager settings;
    private TextView tvTotalInventoryValue, tvTotalCostValue, tvExpectedProfit;
    private TextView tvTotalRevenue, tvTotalSalesCount, tvLowStockHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(this);
        settings = new SettingsManager(this);

        tvTotalInventoryValue = findViewById(R.id.tvTotalInventoryValue);
        tvTotalCostValue = findViewById(R.id.tvTotalCostValue);
        tvExpectedProfit = findViewById(R.id.tvExpectedProfit);
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        tvTotalSalesCount = findViewById(R.id.tvTotalSalesCount);
        tvLowStockHealth = findViewById(R.id.tvLowStockHealth);

        findViewById(R.id.cardAnalyticsLowStock).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductsActivity.class);
            intent.putExtra("filter_low_stock", true);
            startActivity(intent);
        });

        findViewById(R.id.btnExportInventoryReport).setOnClickListener(v ->
                CsvReportExporter.exportInventoryCsv(this, db, settings));

        loadAnalytics();
        NavigationHelper.setupBottomNavigation(this, R.id.nav_analytics);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAnalytics();
    }

    private void loadAnalytics() {
        db.productDao().getTotalInventoryValue().observe(this, val -> {
            double inventoryVal = val == null ? 0.0 : val;
            tvTotalInventoryValue.setText(settings.formatCurrency(inventoryVal));

            db.productDao().getTotalCostValue().observe(this, costVal -> {
                double cost = costVal == null ? 0.0 : costVal;
                tvTotalCostValue.setText(settings.formatCurrency(cost));
                tvExpectedProfit.setText(settings.formatCurrency(inventoryVal - cost));
            });
        });

        db.saleDao().getTotalSalesRevenue().observe(this, rev -> {
            double revenue = rev == null ? 0.0 : rev;
            tvTotalRevenue.setText(settings.formatCurrency(revenue));
        });

        db.saleDao().getTotalSalesCount().observe(this, count -> {
            int cnt = count == null ? 0 : count;
            tvTotalSalesCount.setText(cnt + " sales recorded");
        });

        int threshold = settings.getLowStockThreshold();
        db.productDao().getLowStockCountWithThreshold(threshold).observe(this, count -> {
            int cnt = count == null ? 0 : count;
            if (cnt == 0) {
                tvLowStockHealth.setText("✅ Excellent! All products are in stock.");
                tvLowStockHealth.setTextColor(getColor(R.color.in_stock));
            } else {
                tvLowStockHealth.setText("⚠️ Warning: " + cnt + " items require re-ordering!");
                tvLowStockHealth.setTextColor(getColor(R.color.low_stock));
            }
        });
    }
}
