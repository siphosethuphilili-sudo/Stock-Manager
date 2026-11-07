package com.example.stockmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.SaleViewHolder> {

    private List<Sale> sales = new ArrayList<>();
    private String currencySymbol = "R";

    public void setSales(List<Sale> sales) {
        this.sales = sales != null ? sales : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale, parent, false);
        return new SaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleViewHolder holder, int position) {
        Sale s = sales.get(position);
        holder.tvName.setText(s.getProductName());
        holder.tvQty.setText("Qty: " + s.getQuantitySold());
        holder.tvTotal.setText(String.format(Locale.getDefault(), "%s %.2f", currencySymbol, s.getTotalPrice()));
        holder.tvDate.setText(DateUtils.formatDate(s.getDate()));
    }

    @Override
    public int getItemCount() { return sales.size(); }

    static class SaleViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQty, tvTotal, tvDate;
        SaleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvSaleProduct);
            tvQty = itemView.findViewById(R.id.tvSaleQty);
            tvTotal = itemView.findViewById(R.id.tvSaleTotal);
            tvDate = itemView.findViewById(R.id.tvSaleDate);
        }
    }
}
