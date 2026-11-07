package com.example.stockmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.Executors;

public class StockDialogHelper {

    public static void showRestockDialog(Activity activity, AppDatabase db, SettingsManager settings, Product product, Runnable onComplete) {
        if (product == null) return;
        View v = LayoutInflater.from(activity).inflate(R.layout.dialog_record_sale, null);
        EditText etQty = v.findViewById(R.id.etSaleQty);
        TextView tvTotal = v.findViewById(R.id.tvSaleTotal);

        etQty.setHint("Quantity to add (Restock)");

        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("📦 Restock Inventory: " + product.getName())
                .setView(v)
                .setPositiveButton("Add Stock", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button pos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            pos.setOnClickListener(x -> {
                String s = etQty.getText().toString().trim();
                if (s.isEmpty()) { etQty.setError("Enter quantity"); return; }
                int qty;
                try { qty = Integer.parseInt(s); }
                catch (NumberFormatException e) { etQty.setError("Invalid number"); return; }

                if (qty <= 0) { etQty.setError("Quantity must be > 0"); return; }

                int newQty = product.getQuantity() + qty;
                double totalCost = qty * product.getBuyingPrice();

                Executors.newSingleThreadExecutor().execute(() -> {
                    db.productDao().updateQuantity(product.getId(), newQty);
                    StockTransaction tx = new StockTransaction(
                            product.getId(), product.getName(), "RESTOCK", qty,
                            product.getBuyingPrice(), totalCost, System.currentTimeMillis(), "Stock Restock"
                    );
                    db.transactionDao().insert(tx);

                    activity.runOnUiThread(() -> {
                        Toast.makeText(activity, "Restocked +" + qty + " units of " + product.getName(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        if (onComplete != null) onComplete.run();
                    });
                });
            });
        });

        etQty.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) {
                try {
                    int q = Integer.parseInt(s.toString());
                    tvTotal.setText("Cost: " + settings.formatCurrency(q * product.getBuyingPrice()));
                } catch (Exception e) { tvTotal.setText("Cost: " + settings.formatCurrency(0.0)); }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        dialog.show();
    }

    public static void showSaleDialog(Activity activity, AppDatabase db, SettingsManager settings, Product product, Runnable onComplete) {
        if (product == null) return;
        View v = LayoutInflater.from(activity).inflate(R.layout.dialog_record_sale, null);
        EditText etQty = v.findViewById(R.id.etSaleQty);
        TextView tvTotal = v.findViewById(R.id.tvSaleTotal);

        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("💰 Record Sale: " + product.getName())
                .setView(v)
                .setPositiveButton("Confirm Sale", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button pos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            pos.setOnClickListener(x -> {
                String s = etQty.getText().toString().trim();
                if (s.isEmpty()) { etQty.setError("Enter quantity"); return; }
                int qty;
                try { qty = Integer.parseInt(s); }
                catch (NumberFormatException e) { etQty.setError("Invalid number"); return; }

                if (qty <= 0) { etQty.setError("Must be > 0"); return; }
                if (qty > product.getQuantity()) {
                    etQty.setError("Only " + product.getQuantity() + " available in stock");
                    return;
                }

                double total = qty * product.getSellingPrice();
                Executors.newSingleThreadExecutor().execute(() -> {
                    db.productDao().updateQuantity(product.getId(), product.getQuantity() - qty);
                    Sale sale = new Sale(product.getId(), product.getName(), qty, total, System.currentTimeMillis());
                    db.saleDao().insert(sale);

                    StockTransaction tx = new StockTransaction(
                            product.getId(), product.getName(), "SALE", qty,
                            product.getSellingPrice(), total, System.currentTimeMillis(), "Customer Sale"
                    );
                    db.transactionDao().insert(tx);

                    activity.runOnUiThread(() -> {
                        Toast.makeText(activity, "Sale recorded: " + settings.formatCurrency(total), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        if (onComplete != null) onComplete.run();
                    });
                });
            });
        });

        etQty.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) {
                try {
                    int q = Integer.parseInt(s.toString());
                    tvTotal.setText(settings.formatCurrency(q * product.getSellingPrice()));
                } catch (Exception e) { tvTotal.setText(settings.formatCurrency(0.0)); }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        dialog.show();
    }
}
