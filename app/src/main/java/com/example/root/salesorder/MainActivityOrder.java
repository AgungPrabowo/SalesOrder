package com.example.root.salesorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.salesorder.Model.ModelListOrder;
import com.example.root.salesorder.Model.ModelPelanggan;
import com.example.root.salesorder.util.BaseApiService;
import com.example.root.salesorder.util.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityOrder extends AppCompatActivity {
    BaseApiService mApiService;
    ListView listOrder;
    List<ModelListOrder> dataListOrder;
    ArrayList<String> arrayOrder = new ArrayList<String>();
    SharedPrefManager spManager;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_daftar_order);
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper
        sharedPrefManager = new SharedPrefManager(this);
        Toolbar toolbar = findViewById(R.id.toolbarOrder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getDataListOrder();
    }

    private void getDataListOrder() {
        spManager = new SharedPrefManager(this);
//        System.out.println("karyawan_id"+ spManager.getSPKaryawanID());
        mApiService.getListOrder(spManager.getSPKaryawanID()).enqueue(new Callback<List<ModelListOrder>>() {
            @Override
            public void onResponse(Call<List<ModelListOrder>> call, Response<List<ModelListOrder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: "+ response.code());
                    return;
                }
                dataListOrder = new ArrayList<ModelListOrder>();
                List<ModelListOrder> orders = response.body();

                for (int k=0;k < orders.size();k++) {
                    dataListOrder.add(new ModelListOrder(orders.get(k).getCreated_at()));
                }

                for (int w = 0;w < dataListOrder.size();w++) {
                    arrayOrder.add(dataListOrder.get(w).getCreated_at());
                }

                listOrder = findViewById(R.id.daftar_list_order);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview_order,
                        R.id.textViewOrder, arrayOrder);
                listOrder.setAdapter(arrayAdapter);
                listOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(MainActivityOrder.this, spManager.getSPKaryawanID()+" "+arrayOrder.get(position),
//                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), MainActivityOrderInvoice.class);
                        intent.putExtra("karyawan_id", spManager.getSPKaryawanID());
                        intent.putExtra("tgl", arrayOrder.get(position));
                        view.getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelListOrder>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
