package com.example.root.salesorder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.root.salesorder.Adapter.AdapterMap;
import com.example.root.salesorder.util.BaseApiService;
import com.example.root.salesorder.util.ModelOrder1;
import com.example.root.salesorder.util.UtilsApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapTrackingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BaseApiService mApiService;
    List<ModelOrder1> dataOrder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tracking);
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper
        getDataOrder();
    }

    public void getDataOrder() {
        mApiService.getOrder().enqueue(new Callback<List<ModelOrder1>>() {
            @Override
            public void onResponse(Call<List<ModelOrder1>> call, Response<List<ModelOrder1>> response) {
                dataOrder1 = new ArrayList<ModelOrder1>();
                List<ModelOrder1> orders = response.body();
                String[] dateOrder = new String[orders.size()];

                for (int i = 0;i < orders.size();i++) {
                    dataOrder1.add(new ModelOrder1(orders.get(i).getCreated_at(),
                            orders.get(i).getLatitude(), orders.get(i).getLongitude()));
                    // only get date
                    dateOrder[i] = orders.get(i).getCreated_at().substring(0, 10);
                }

                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(dateOrder));
                String[] dateOrderWithoutDuplicate = linkedHashSet.toArray(new String[] {});
                System.out.println(Arrays.toString(dateOrderWithoutDuplicate));

                ProgressDialog progressDialog = new ProgressDialog(MapTrackingActivity.this);
                recyclerView = (RecyclerView) findViewById(R.id.recyclerviewMap);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new AdapterMap(getApplicationContext(), dateOrderWithoutDuplicate, progressDialog, dataOrder1));
            }

            @Override
            public void onFailure(Call<List<ModelOrder1>> call, Throwable t) {

            }
        });
    }
}
