package com.example.root.salesorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.salesorder.Adapter.AdapterMap;
import com.example.root.salesorder.Model.ModelPelanggan;
import com.example.root.salesorder.util.BaseApiService;
import com.example.root.salesorder.util.ModelOrder1;
import com.example.root.salesorder.util.UtilsApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    MapTrackingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BaseApiService mApiService;
    List<ModelOrder1> dataOrder1;
    ListView listOrder;
    List<String> latitude = new ArrayList<String>();
    List<String> longitude = new ArrayList<String>();
    SharedPrefManager spManager;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tracking);
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper
        sharedPrefManager = new SharedPrefManager(this);
        Toolbar toolbar = findViewById(R.id.toolbarMap);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getDataOrder();
    }

    private void getDataOrder() {
        spManager = new SharedPrefManager(this);
        mApiService.getOrder().enqueue(new Callback<List<ModelOrder1>>() {
            @Override
            public void onResponse(Call<List<ModelOrder1>> call, Response<List<ModelOrder1>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: "+ response.code());
                    return;
                }
                dataOrder1 = new ArrayList<ModelOrder1>();
                List<ModelOrder1> orders = response.body();
                ArrayList dateOrder = new ArrayList<String>();

                for (int i = 0;i < orders.size();i++) {
                    dataOrder1.add(new ModelOrder1(orders.get(i).getKaryawan_id(), orders.get(i).getCreated_at(),
                            orders.get(i).getLatitude(), orders.get(i).getLongitude()));
                    // only get date
                    if(spManager.getSPKaryawanID().equals(orders.get(i).getKaryawan_id())) {
                        dateOrder.add(orders.get(i).getCreated_at().substring(0, 10));
                    }
                }

                listOrder = findViewById(R.id.daftar_order);
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(dateOrder);
                final String[] dateOrderWithoutDuplicate = linkedHashSet.toArray(new String[] {});
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.map_row,
                            R.id.item, dateOrderWithoutDuplicate);
                listOrder.setAdapter(arrayAdapter);
                listOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ProgressDialog progressDialog = new ProgressDialog(MapTrackingActivity.this);
                            progressDialog.show();
                            progressDialog.setMessage("Mohon Tunggu...");
                            getCoordinate(dateOrderWithoutDuplicate[position]);
                            progressDialog.dismiss();
                            String[] arrayLatitude = latitude.toArray(new String[latitude.size()]);
                            String[] arrayLongitude = longitude.toArray(new String[longitude.size()]);
                            System.out.println(Arrays.toString(arrayLatitude));
                            System.out.println(Arrays.toString(arrayLongitude));
                            Intent intent = new Intent(view.getContext(), Map.class);
                            intent.putExtra("latitude", arrayLatitude);
                            intent.putExtra("longitude", arrayLongitude);
                            view.getContext().startActivity(intent);
                        }
                    });
            }

            @Override
            public void onFailure(Call<List<ModelOrder1>> call, Throwable t) {

            }
        });
    }

    public void getCoordinate(String dateOrder) {
        spManager = new SharedPrefManager(this);
        int countModelOrder = dataOrder1.size();
        latitude.clear();
        longitude.clear();

        for (int i = 0;i < countModelOrder;i++) {
            if (dateOrder.equals(dataOrder1.get(i).getCreated_at().substring(0, 10)) &&
                    spManager.getSPKaryawanID().equals(dataOrder1.get(i).getKaryawan_id())) {
                latitude.add(dataOrder1.get(i).getLatitude());
                longitude.add(dataOrder1.get(i).getLongitude());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
