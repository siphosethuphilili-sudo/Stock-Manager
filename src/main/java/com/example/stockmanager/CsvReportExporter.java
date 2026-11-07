package com.example.stockmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class CsvReportExporter {

    public static void exportInventoryCsv(Context context, AppDatabase db, SettingsManager settings) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Product> products = db.productDao().getAllProductsList();
            if (products == null || products.isEmpty()) {
                runOnMain(context, () -> Toast.makeText(context, "No inventory products to export", Toast.LENGTH_SHORT).show());
                return;
            }

            try {
                String fileName = "Inventory_Report_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".csv";
                File exportDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Reports");
                if (!exportDir.exists()) exportDir.mkdirs();
                File csvFile = new File(exportDir, fileName);

                FileWriter writer = new FileWriter(csvFile);
                writer.append("ID,SKU,Product Name,Category,Quantity,Buying Price (").append(settings.getCurrencySymbol()).append("),Selling Price (").append(settings.getCurrencySymbol()).append("),Total Value (").append(settings.getCurrencySymbol()).append("),Supplier\n");

                for (Product p : products) {
                    writer.append(String.valueOf(p.getId())).append(",")
                            .append(escapeCsv(p.getSku())).append(",")
                            .append(escapeCsv(p.getName())).append(",")
                            .append(escapeCsv(p.getCategory())).append(",")
                            .append(String.valueOf(p.getQuantity())).append(",")
                            .append(String.format(Locale.US, "%.2f", p.getBuyingPrice())).append(",")
                            .append(String.format(Locale.US, "%.2f", p.getSellingPrice())).append(",")
                            .append(String.format(Locale.US, "%.2f", p.getTotalStockValue())).append(",")
                            .append(escapeCsv(p.getSupplier())).append("\n");
                }
                writer.flush();
                writer.close();

                runOnMain(context, () -> shareCsvFile(context, csvFile, "Share Inventory CSV Report"));
            } catch (Exception e) {
                runOnMain(context, () -> Toast.makeText(context, "Export failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private static String escapeCsv(String value) {
        if (value == null) return "\"\"";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private static void shareCsvFile(Context context, File file, String chooserTitle) {
        try {
            Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/csv");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, file.getName());
            sendIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent chooser = Intent.createChooser(sendIntent, chooserTitle);
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(chooser);
        } catch (Exception e) {
            Toast.makeText(context, "Failed to share report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private static void runOnMain(Context context, Runnable r) {
        new android.os.Handler(android.os.Looper.getMainLooper()).post(r);
    }
}
