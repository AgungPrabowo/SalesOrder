package com.example.root.salesorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.root.salesorder.Model.ModelPelanggan;
import com.example.root.salesorder.util.BaseApiService;
import com.example.root.salesorder.util.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPelanggan extends AppCompatActivity{
    ListView listPelanggan;
    BaseApiService mApiService;
    List<ModelPelanggan> dataPelanggan;
    ArrayList<String> arrayPelanggan = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_daftar_pelanggan);
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper
        Toolbar toolbar = findViewById(R.id.toolbarPelanggan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getDataPelanggan();
    }

    private void getDataPelanggan() {
        mApiService.getPelanggan().enqueue(new Callback<List<ModelPelanggan>>() {
            @Override
            public void onResponse(Call<List<ModelPelanggan>> call, Response<List<ModelPelanggan>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: "+ response.code());
                    return;
                }
                dataPelanggan = new ArrayList<ModelPelanggan>();
                List<ModelPelanggan> pelanggans = response.body();

                for (int ii=0;ii < pelanggans.size();ii++) {
                    dataPelanggan.add(new ModelPelanggan(pelanggans.get(ii).getId(),pelanggans.get(ii).getNama(),
                            pelanggans.get(ii).getEmail(),pelanggans.get(ii).getAlamat(),pelanggans.get(ii).getPhone()));
                }

                for (int w = 0;w < dataPelanggan.size();w++) {
                    arrayPelanggan.add(dataPelanggan.get(w).getNama().toUpperCase());
                }

                listPelanggan = findViewById(R.id.daftar_pelanggan);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview_pelanggan,
                        R.id.textViewPelanggan, arrayPelanggan);
                listPelanggan.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelPelanggan>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
