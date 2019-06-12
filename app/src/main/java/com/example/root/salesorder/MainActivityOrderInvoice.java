package com.example.root.salesorder;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.root.salesorder.Adapter.AdapterOrderInvoice;
import com.example.root.salesorder.Model.ModelOrderInvoice;
import com.example.root.salesorder.util.BaseApiService;
import com.example.root.salesorder.util.UtilsApi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityOrderInvoice extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterOrderInvoice adapter;
    BaseApiService mApiService;
    String karyawan_id, tgl;
    Context mContext;
    private List<ModelOrderInvoice> dataorderInvoices;
//    SharedPrefManager spManager;
//    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_order_invoice);

//        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper
        Intent intent = getIntent();
        karyawan_id = intent.getStringExtra("karyawan_id");
        tgl = intent.getStringExtra("tgl");
        getOrderInvoice();
        Toolbar toolbar = findViewById(R.id.toolbarOrderPelanggan);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) { // you have to use actionBar (ActionBar Object)  instead.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void getOrderInvoice() {
//        spManager = new SharedPrefManager(this);
        System.out.println(karyawan_id+" karyawan id");

        mApiService.getOrderInvoice(karyawan_id, tgl).enqueue(new Callback<List<ModelOrderInvoice>>() {
            @Override
            public void onResponse(Call<List<ModelOrderInvoice>> call, Response<List<ModelOrderInvoice>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: "+ response.code());
                    return;
                }

                dataorderInvoices = new ArrayList<ModelOrderInvoice>();
                List<ModelOrderInvoice> orders = response.body();

                for(int i=0;i < orders.size();i++) {
                    dataorderInvoices.add(new ModelOrderInvoice(orders.get(i).getPlg(), orders.get(i).getTotal(),
                            orders.get(i).getNmBrg(), orders.get(i).getHarga()));
                }

                System.out.println(dataorderInvoices.get(0).getNmBrg().get(0));

                recyclerView = findViewById(R.id.recycler_view);
                adapter = new AdapterOrderInvoice(dataorderInvoices);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivityOrderInvoice.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ModelOrderInvoice>> call, Throwable t) {
                System.out.println("gagal get order invoice");
                System.out.println(t);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
