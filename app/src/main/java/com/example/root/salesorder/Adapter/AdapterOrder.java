package com.example.root.salesorder.Adapter;

import android.content.Context;
import android.graphics.ColorSpace;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.salesorder.ModelOrder;
import com.example.root.salesorder.R;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.OrderViewHolder> {

    private Context context;
    private List<ModelOrder> dataOrder;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;

    public AdapterOrder(Context context, List<ModelOrder> dataOrder) {
        this.context = context;
        this.dataOrder = dataOrder;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView barang, stock, harga, satuan;

        public OrderViewHolder(View itemView) {
            super(itemView);

            barang = (TextView) itemView.findViewById(R.id.barang);
            stock = (TextView) itemView.findViewById(R.id.stock);
            harga = (TextView) itemView.findViewById(R.id.harga);
            satuan = (TextView) itemView.findViewById(R.id.satuan);
        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order, parent, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(v);
        return orderViewHolder;
//        if(viewType == TYPE_ITEM) {
////            inflating recyle view item layout
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order, parent, false);
//            return new ItemViewHolder(itemView);
//        } else if(viewType == TYPE_HEADER) {
////            inflating header view
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_order, parent, false);
//            return new HeaderViewHolder(itemView);
//        } else if(viewType == TYPE_FOOTER) {
////            inflating footer view
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_order, parent, false);
//            return new FooterViewHolder(itemView);
//        } else return null;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        ModelOrder mo = dataOrder.get(position);

        holder.barang.setText(mo.getBarang());
        holder.satuan.setText(mo.getSatuan());
        holder.stock.setText(mo.getStock());
        holder.harga.setText(mo.getHarga());

//        if(holder instanceof HeaderViewHolder) {
//            HeaderViewHolder headerHolder = (HeaderViewHolder) holder
//        }

    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return TYPE_HEADER;
//        } else if (position == dataOrder.size() + 1) {
//
//        }
//    }

    @Override
    public int getItemCount() {
        return dataOrder.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View view) {
            super(view);


        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        CardView cardView;

        public HeaderViewHolder(View view) {
            super(view);
            this.nama = (TextView) itemView.findViewById(R.id.textView3);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }
}
