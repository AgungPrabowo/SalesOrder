package com.example.root.salesorder;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.salesorder.Adapter.AdapterTes;
import com.example.root.salesorder.Adapter.Model;
import com.example.root.salesorder.Model.ModelPelanggan;
import com.example.root.salesorder.util.BaseApiService;
import com.example.root.salesorder.util.ModelOrder1;
import com.example.root.salesorder.util.UtilsApi;
import android.location.LocationListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<ModelOrder> dataOrder;
    List<ModelPelanggan> dataPelanggan;
    private AdapterTes hfAdapter;
    private List<Model> headerModelArrayList;

    SharedPrefManager spManager;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context mContext;


    LocationManager locationmanager;
    String longitude, latitude;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper
//        ambil data barang
        getDataPelanggan();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataBarang();
            }
        }, 1000);
//
//        if (dataPelanggan == null) {
//            loading = ProgressDialog.show(this, null, "ambil data barang...",
//                    true, false);
//            getDataPelanggan();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    loading.dismiss();
//                    getDataBarang();
//                }
//            }, 1000);
//        }

//        if (dataPelanggan == null) {
//            getDataPelanggan();
//            loading = ProgressDialog.show(this, null, "ambil data barang...",
//                    true, false);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    loading.dismiss();
////                    getDataPelanggan();
//                    System.out.println("1");
//                    getDataBarang();
//                }
//            }, 1000);
//        }
//        if (dataPelanggan != null) {
//            getDataPelanggan();
//            System.out.println("2");
//            getDataBarang();
//        }
//        getDataPelanggan();
//        getDataBarang();
//
//        if (dataPelanggan != null) {
////            getDataPelanggan();
//            getDataBarang();
//        }
//        getDataPelanggan();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getDataBarang();
//            }
//        }).start();
        getDataPelanggan();
        getDataBarang();

        Model model = new Model();
        model.setText("tes");
        sharedPrefManager = new SharedPrefManager(this);

        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria cri = new Criteria();

        String provider = locationmanager.getBestProvider(cri, false);

        if (provider != null){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationmanager.getLastKnownLocation(provider);
            locationmanager.requestLocationUpdates(provider,2000,1, this);
            if(location!=null) {
                onLocationChanged(location);
            } else {
                Toast.makeText(getApplicationContext(),"location not found",Toast.LENGTH_LONG ).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Provider is null",Toast.LENGTH_LONG).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

        private void getDataBarang() {
            mApiService.getBarang().enqueue(new Callback<List<ModelOrder>>() {
            @Override
            public void onResponse(Call<List<ModelOrder>> call, Response<List<ModelOrder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: "+ response.code());
                    return;
                }
                String id_karyawan = sharedPrefManager.getSPKaryawanID();
                recyclerView = findViewById(R.id.recyclerview);

                dataOrder = new ArrayList<ModelOrder>();
                headerModelArrayList = new ArrayList<>();
                List<ModelOrder> barangs = response.body();

                int count = barangs.size()+1;

                for (int i = 0;i < count;i++) {

                    if (i == 0) {
                        Model model = new Model();
                        model.setViewType("header");
                        headerModelArrayList.add(model);
                        dataOrder.add(new ModelOrder(barangs.get(i).getId(), barangs.get(i).getBarang(),
                                barangs.get(i).getSatuan(), barangs.get(i).getHarga(), barangs.get(i).getStock(),
                                "0", longitude, latitude, id_karyawan));
                    } else if(i == count-1) {
                        Model model = new Model();
                        model.setViewType("footer");
                        headerModelArrayList.add(model);
                    }

                    if (i != count-1 && (!barangs.get(i).getStock().equals("0"))) {
                        Model model = new Model();
                        model.setViewType("normal");
                        headerModelArrayList.add(model);
                        dataOrder.add(new ModelOrder(barangs.get(i).getId(), barangs.get(i).getBarang(),
                                barangs.get(i).getSatuan(), barangs.get(i).getHarga(), barangs.get(i).getStock(),
                                "0", longitude, latitude, id_karyawan));
                    }

                }

                hfAdapter = new AdapterTes(mContext, headerModelArrayList, dataOrder, dataPelanggan);
                recyclerView.setAdapter(hfAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onFailure(Call<List<ModelOrder>> call, Throwable t) {
                System.out.println(t.getMessage()+" barang kosong");
            }
        });
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
                System.out.println(dataPelanggan.size()+"Tes count");
            }

            @Override
            public void onFailure(Call<List<ModelPelanggan>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        spManager = new SharedPrefManager(this);

        if (id == R.id.map) {
            startActivity(new Intent(MainActivity.this, MapTrackingActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.list_pelanggan) {
            startActivity(new Intent(MainActivity.this, MainActivityPelanggan.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.logout) {
            spManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

//        textView2.setText("Latitude"+location.getLatitude());
//
//        textView3.setText("Longitude"+ location.getLongitude());
        Double lat = location.getLatitude();
        Double longitude = location.getLongitude();
//        Toast.makeText(this, String.valueOf(lat), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, String.valueOf(longitude), Toast.LENGTH_SHORT).show();
        Log.i("lat", String.valueOf(lat));
        Log.i("long", String.valueOf(longitude));
        this.latitude = String.valueOf(lat);
        this.longitude = String.valueOf(longitude);
//        this.latitude = String.valueOf("65.9667");
//        this.longitude = String.valueOf("-18.5333");

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
