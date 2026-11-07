package com.example.stockmanager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnProductActionListener {
        void onProductClick(Product product);
        void onRestockClick(Product product);
        void onSaleClick(Product product);
    }

    private List<Product> productList = new ArrayList<>();
    private final OnProductActionListener listener;
    private int lowStockThreshold = 5;
    private String currencySymbol = "R";

    public ProductAdapter(OnProductActionListener listener) {
        this.listener = listener;
    }

    public void setProducts(List<Product> products) {
        this.productList = products != null ? products : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setLowStockThreshold(int threshold) {
        this.lowStockThreshold = threshold;
        notifyDataSetChanged();
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = productList.get(position);
        holder.tvName.setText(p.getName());
        holder.tvSkuChip.setText(p.getSku() != null ? p.getSku() : "SKU-" + p.getId());
        holder.tvCategory.setText(p.getCategory());
        holder.tvPrice.setText(String.format(java.util.Locale.getDefault(), "%s %.2f", currencySymbol, p.getSellingPrice()));
        holder.tvQuantity.setText(String.valueOf(p.getQuantity()));

        int targetThreshold = p.getMinStock() > 0 ? p.getMinStock() : lowStockThreshold;
        if (p.getQuantity() < targetThreshold) {
            holder.tvQuantity.setTextColor(Color.parseColor("#D32F2F"));
            holder.tvQuantity.setBackgroundColor(Color.parseColor("#FFEBEE"));
            holder.tvLowStock.setVisibility(View.VISIBLE);
        } else {
            holder.tvQuantity.setTextColor(Color.parseColor("#2E7D32"));
            holder.tvQuantity.setBackgroundColor(Color.parseColor("#E8F5E9"));
            holder.tvLowStock.setVisibility(View.GONE);
        }

        holder.card.setOnClickListener(v -> listener.onProductClick(p));
        holder.btnQuickRestock.setOnClickListener(v -> listener.onRestockClick(p));
        holder.btnQuickSale.setOnClickListener(v -> listener.onSaleClick(p));
    }

    @Override
    public int getItemCount() { return productList.size(); }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSkuChip, tvCategory, tvPrice, tvQuantity, tvLowStock;
        MaterialButton btnQuickRestock, btnQuickSale;
        CardView card;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvSkuChip = itemView.findViewById(R.id.tvSkuChip);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvLowStock = itemView.findViewById(R.id.tvLowStock);
            btnQuickRestock = itemView.findViewById(R.id.btnQuickRestock);
            btnQuickSale = itemView.findViewById(R.id.btnQuickSale);
            card = itemView.findViewById(R.id.productCard);
        }
    }
}
