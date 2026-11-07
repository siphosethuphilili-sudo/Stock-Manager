package com.example.stockmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        SettingsManager settings = new SettingsManager(this);

        RecyclerView rv = findViewById(R.id.rvTransactions);
        rv.setLayoutManager(new LinearLayoutManager(this));
        TransactionAdapter adapter = new TransactionAdapter();
        adapter.setCurrencySymbol(settings.getCurrencySymbol());
        rv.setAdapter(adapter);

        AppDatabase.getInstance(this).transactionDao().getAllTransactions()
                .observe(this, adapter::setTransactions);
    }
}
