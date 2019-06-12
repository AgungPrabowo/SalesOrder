package com.example.root.salesorder.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.salesorder.Model.ModelOrderInvoice;
import com.example.root.salesorder.R;
import com.google.common.base.Joiner;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterOrderInvoice extends RecyclerView.Adapter<AdapterOrderInvoice.OrderInvoiceViewHolder> {

    private List<ModelOrderInvoice> dataOrderInvoice;

    public AdapterOrderInvoice(List<ModelOrderInvoice> dataOrderInvoice) {
        this.dataOrderInvoice = dataOrderInvoice;
    }

    @NonNull
    @Override
    public OrderInvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_order_invoice, parent, false);
        return new OrderInvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderInvoiceViewHolder holder, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        String brg = Joiner.on("\n").join(dataOrderInvoice.get(position).getNmBrg());
        String harga = Joiner.on("\n").join(dataOrderInvoice.get(position).getHarga());

        holder.txtPlg.setText(dataOrderInvoice.get(position).getPlg());
        holder.txtTotal.setText(formatRupiah.format(Integer.parseInt(dataOrderInvoice.get(position).getTotal())));
        holder.txtNmBrg.setText(brg);
        holder.txtHarga.setText(harga);
    }

    @Override
    public int getItemCount() {
        return dataOrderInvoice.size();
    }

    public class OrderInvoiceViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPlg, txtTotal, txtNmBrg, txtHarga;

        public OrderInvoiceViewHolder(View itemView) {
            super(itemView);

            txtPlg = itemView.findViewById(R.id.txt_plg);
            txtTotal = itemView.findViewById(R.id.txt_total);
            txtNmBrg = itemView.findViewById(R.id.txt_nmBrg);
            txtHarga = itemView.findViewById(R.id.txt_harga);
        }
    }
}
