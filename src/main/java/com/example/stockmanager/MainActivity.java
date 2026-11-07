package com.example.stockmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductActionListener {

    private AppDatabase db;
    private SettingsManager settings;
    private ProductAdapter adapter;
    private TextView tvTotalProducts, tvLowStock, tvTodaySales, tvMonthSales;
    private SwipeRefreshLayout swipeRefresh;
    private LiveData<List<Product>> currentLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(this);
        settings = new SettingsManager(this);

        tvTotalProducts = findViewById(R.id.tvTotalProducts);
        tvLowStock = findViewById(R.id.tvLowStock);
        tvTodaySales = findViewById(R.id.tvTodaySales);
        tvMonthSales = findViewById(R.id.tvMonthSales);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        RecyclerView rv = findViewById(R.id.recyclerProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this);
        adapter.setLowStockThreshold(settings.getLowStockThreshold());
        adapter.setCurrencySymbol(settings.getCurrencySymbol());
        rv.setAdapter(adapter);

        // Clickable Low Stock Alert card & banner button -> Opens ProductsActivity with Low Stock filter enabled!
        findViewById(R.id.cardLowStockAlert).setOnClickListener(v -> openLowStockView());
        findViewById(R.id.btnLowStockAlertBanner).setOnClickListener(v -> openLowStockView());

        // Navigation & Quick Actions
        findViewById(R.id.cardAddProduct).setOnClickListener(v ->
                startActivity(new Intent(this, AddProductActivity.class)));

        findViewById(R.id.cardReorderAssistant).setOnClickListener(v ->
                startActivity(new Intent(this, ReorderAssistantActivity.class)));

        findViewById(R.id.cardAuditLog).setOnClickListener(v ->
                startActivity(new Intent(this, TransactionsActivity.class)));

        findViewById(R.id.cardSalesHistory).setOnClickListener(v ->
                startActivity(new Intent(this, SalesHistoryActivity.class)));

        findViewById(R.id.tvViewAllProducts).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductsActivity.class);
            startActivity(intent);
        });

        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                observeProducts(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        observeProducts("");
        observeStats();

        swipeRefresh.setOnRefreshListener(() -> {
            observeProducts(etSearch.getText().toString());
            observeStats();
            swipeRefresh.setRefreshing(false);
        });

        NavigationHelper.setupBottomNavigation(this, R.id.nav_dashboard);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null && settings != null) {
            adapter.setLowStockThreshold(settings.getLowStockThreshold());
            adapter.setCurrencySymbol(settings.getCurrencySymbol());
        }
        observeStats();
    }

    private void openLowStockView() {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra("filter_low_stock", true);
        startActivity(intent);
    }

    private void observeProducts(String search) {
        if (currentLiveData != null) {
            currentLiveData.removeObservers(this);
        }
        LiveData<List<Product>> ld = search.isEmpty()
                ? db.productDao().getAllProducts()
                : db.productDao().searchProducts(search);
        currentLiveData = ld;
        ld.observe(this, products -> adapter.setProducts(products));
    }

    private void observeStats() {
        int threshold = settings.getLowStockThreshold();
        db.productDao().getProductCount().observe(this, c ->
                tvTotalProducts.setText(String.valueOf(c == null ? 0 : c)));

        db.productDao().getLowStockCountWithThreshold(threshold).observe(this, c -> {
            int count = c == null ? 0 : c;
            tvLowStock.setText(count + " items");
        });

        db.saleDao().getTodaySales(DateUtils.startOfToday())
                .observe(this, v -> tvTodaySales.setText(settings.formatCurrency(v == null ? 0 : v)));
        db.saleDao().getMonthSales(DateUtils.startOfMonth())
                .observe(this, v -> tvMonthSales.setText(settings.formatCurrency(v == null ? 0 : v)));
    }

    @Override
    public void onProductClick(Product product) {
        Intent i = new Intent(this, ProductDetailsActivity.class);
        i.putExtra("product_id", product.getId());
        startActivity(i);
    }

    @Override
    public void onRestockClick(Product product) {
        StockDialogHelper.showRestockDialog(this, db, settings, product, this::observeStats);
    }

    @Override
    public void onSaleClick(Product product) {
        StockDialogHelper.showSaleDialog(this, db, settings, product, this::observeStats);
    }
}
