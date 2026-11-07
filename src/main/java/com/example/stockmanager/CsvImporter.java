package com.example.stockmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class CsvImporter {

    public interface OnImportCompleteListener {
        void onImportFinished(int importedCount);
    }

    public static void importProductsFromUri(Context context, Uri uri, AppDatabase db, SettingsManager settings, OnImportCompleteListener listener) {
        Executors.newSingleThreadExecutor().execute(() -> {
            int importedCount = 0;
            try {
                InputStream is = context.getContentResolver().openInputStream(uri);
                if (is == null) {
                    runOnMain(() -> Toast.makeText(context, "Cannot open file", Toast.LENGTH_SHORT).show());
                    return;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                boolean isHeader = true;
                List<Product> products = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }

                    String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    if (tokens.length >= 5) {
                        try {
                            String name = cleanToken(tokens[0]);
                            String category = cleanToken(tokens[1]);
                            int qty = Integer.parseInt(cleanToken(tokens[2]));
                            double buy = Double.parseDouble(cleanToken(tokens[3]));
                            double sell = Double.parseDouble(cleanToken(tokens[4]));

                            String sku = tokens.length > 5 ? cleanToken(tokens[5]) : "";
                            String supplier = tokens.length > 6 ? cleanToken(tokens[6]) : "Supplier";

                            Product p = new Product(name, category, buy, sell, qty, sku, settings.getLowStockThreshold(), supplier);
                            products.add(p);
                            importedCount++;
                        } catch (Exception ignored) {}
                    }
                }
                reader.close();

                if (!products.isEmpty()) {
                    db.productDao().insertAll(products);
                }

                int finalCount = importedCount;
                runOnMain(() -> {
                    Toast.makeText(context, "Successfully imported " + finalCount + " products!", Toast.LENGTH_SHORT).show();
                    if (listener != null) listener.onImportFinished(finalCount);
                });

            } catch (Exception e) {
                runOnMain(() -> Toast.makeText(context, "Import failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private static String cleanToken(String token) {
        if (token == null) return "";
        token = token.trim();
        if (token.startsWith("\"") && token.endsWith("\"")) {
            token = token.substring(1, token.length() - 1).replace("\"\"", "\"");
        }
        return token;
    }

    private static void runOnMain(Runnable r) {
        new Handler(Looper.getMainLooper()).post(r);
    }
}
