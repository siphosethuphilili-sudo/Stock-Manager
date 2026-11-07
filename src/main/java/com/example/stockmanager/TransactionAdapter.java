package com.example.stockmanager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<StockTransaction> transactions = new ArrayList<>();
    private String currencySymbol = "R";

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions != null ? transactions : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        StockTransaction tx = transactions.get(position);
        holder.tvProductName.setText(tx.getProductName());
        holder.tvDate.setText(DateUtils.formatDate(tx.getTimestamp()));
        holder.tvTotal.setText(String.format(Locale.getDefault(), "%s %.2f", currencySymbol, tx.getTotalAmount()));

        if ("RESTOCK".equalsIgnoreCase(tx.getType())) {
            holder.tvBadge.setText("RESTOCK (+)");
            holder.tvBadge.setBackgroundColor(Color.parseColor("#14332A"));
            holder.tvBadge.setTextColor(Color.parseColor("#10B981"));

            holder.tvQty.setText("+" + tx.getQuantity() + " units");
            holder.tvQty.setTextColor(Color.parseColor("#10B981"));
        } else {
            holder.tvBadge.setText("SALE (-)");
            holder.tvBadge.setBackgroundColor(Color.parseColor("#3B1C24"));
            holder.tvBadge.setTextColor(Color.parseColor("#FF5252"));

            holder.tvQty.setText("-" + tx.getQuantity() + " units");
            holder.tvQty.setTextColor(Color.parseColor("#FF5252"));
        }
    }

    @Override
    public int getItemCount() { return transactions.size(); }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvBadge, tvProductName, tvDate, tvTotal, tvQty;

        TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBadge = itemView.findViewById(R.id.tvTxTypeBadge);
            tvProductName = itemView.findViewById(R.id.tvTxProductName);
            tvDate = itemView.findViewById(R.id.tvTxDate);
            tvTotal = itemView.findViewById(R.id.tvTxTotal);
            tvQty = itemView.findViewById(R.id.tvTxQty);
        }
    }
}
