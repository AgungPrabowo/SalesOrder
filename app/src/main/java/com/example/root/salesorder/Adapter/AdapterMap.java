package com.example.root.salesorder.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.salesorder.Map;
import com.example.root.salesorder.MapTrackingActivity;
import com.example.root.salesorder.R;
import com.example.root.salesorder.util.ModelOrder1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterMap extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    String[] items;
    List<String> latitude = new ArrayList<String>();
    List<String> longitude = new ArrayList<String>();
    List<ModelOrder1> modelOrder1;
    ProgressDialog progressDialog;

    public AdapterMap(Context context, String[] items, ProgressDialog progressDialog, List<ModelOrder1> modelOrder1) {
        this.context = context;
        this.items = items;
        this.progressDialog = progressDialog;
        this.modelOrder1 = modelOrder1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infalter = LayoutInflater.from(context);
        View row = infalter.inflate(R.layout.map_row, parent, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).textView.setText(items[position]);
        ((Item)holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setMessage("Mohon Tunggu...");
                getCoordinate(items[position]);
                progressDialog.dismiss();
//                System.out.println(latitude);
                String[] arrayLatitude = latitude.toArray(new String[latitude.size()]);
                String[] arrayLongitude = longitude.toArray(new String[longitude.size()]);
                System.out.println(Arrays.toString(arrayLatitude));
                System.out.println(Arrays.toString(arrayLongitude));
                Intent intent = new Intent(context, Map.class);
                intent.putExtra("latitude", arrayLatitude);
                intent.putExtra("longitude", arrayLongitude);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView textView;
        public Item(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item);
        }
    }

    public void getCoordinate(String dateOrder) {
        int countModelOrder = modelOrder1.size();
        latitude.clear();
        longitude.clear();

        for (int i = 0;i < countModelOrder;i++) {
            if (dateOrder.equals(modelOrder1.get(i).getCreated_at().substring(0, 10))) {
                latitude.add(modelOrder1.get(i).getLatitude());
                longitude.add(modelOrder1.get(i).getLongitude());
            }
        }
    }
}
