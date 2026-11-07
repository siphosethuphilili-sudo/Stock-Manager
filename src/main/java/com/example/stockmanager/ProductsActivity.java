package com.example.stockmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductsActivity extends AppCompatActivity implements ProductAdapter.OnProductActionListener {

    private AppDatabase db;
    private SettingsManager settings;
    private ProductAdapter adapter;
    private MaterialButton btnFilterAll, btnFilterLowStock;
    private LiveData<List<Product>> currentLiveData;
    private boolean isLowStockFilterActive = false;
    private String currentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(this);
        settings = new SettingsManager(this);

        btnFilterAll = findViewById(R.id.btnFilterAll);
        btnFilterLowStock = findViewById(R.id.btnFilterLowStock);

        RecyclerView rv = findViewById(R.id.rvAllProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this);
        adapter.setLowStockThreshold(settings.getLowStockThreshold());
        adapter.setCurrencySymbol(settings.getCurrencySymbol());
        rv.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAddProduct);
        fab.setOnClickListener(v -> startActivity(new Intent(this, AddProductActivity.class)));

        // Check if navigated from Low Stock Alert card
        if (getIntent().getBooleanExtra("filter_low_stock", false)) {
            isLowStockFilterActive = true;
        }

        updateFilterButtonsUI();

        btnFilterAll.setOnClickListener(v -> {
            isLowStockFilterActive = false;
            updateFilterButtonsUI();
            observeProducts();
        });

        btnFilterLowStock.setOnClickListener(v -> {
            isLowStockFilterActive = true;
            updateFilterButtonsUI();
            observeProducts();
        });

        EditText etSearch = findViewById(R.id.etSearchProducts);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s.toString();
                observeProducts();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        observeProducts();
        NavigationHelper.setupBottomNavigation(this, R.id.nav_products);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null && settings != null) {
            adapter.setLowStockThreshold(settings.getLowStockThreshold());
            adapter.setCurrencySymbol(settings.getCurrencySymbol());
        }
        observeProducts();
    }

    private void updateFilterButtonsUI() {
        if (isLowStockFilterActive) {
            btnFilterAll.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.surface));
            btnFilterAll.setTextColor(ContextCompat.getColor(this, R.color.text_primary));

            btnFilterLowStock.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.low_stock));
            btnFilterLowStock.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            btnFilterAll.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
            btnFilterAll.setTextColor(ContextCompat.getColor(this, android.R.color.white));

            btnFilterLowStock.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.surface));
            btnFilterLowStock.setTextColor(ContextCompat.getColor(this, R.color.low_stock));
        }
    }

    private void observeProducts() {
        if (currentLiveData != null) {
            currentLiveData.removeObservers(this);
        }

        LiveData<List<Product>> ld;
        int threshold = settings.getLowStockThreshold();

        if (isLowStockFilterActive) {
            ld = db.productDao().getLowStockProductsWithThreshold(threshold);
        } else if (!currentQuery.isEmpty()) {
            ld = db.productDao().searchProducts(currentQuery);
        } else {
            ld = db.productDao().getAllProducts();
        }

        currentLiveData = ld;
        ld.observe(this, products -> adapter.setProducts(products));
    }

    @Override
    public void onProductClick(Product product) {
        Intent i = new Intent(this, ProductDetailsActivity.class);
        i.putExtra("product_id", product.getId());
        startActivity(i);
    }

    @Override
    public void onRestockClick(Product product) {
        StockDialogHelper.showRestockDialog(this, db, settings, product, this::observeProducts);
    }

    @Override
    public void onSaleClick(Product product) {
        StockDialogHelper.showSaleDialog(this, db, settings, product, this::observeProducts);
    }
}
