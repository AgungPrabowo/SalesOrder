package com.example.root.salesorder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.common.permission.AndroidPermissionChecker;
import com.tomtom.online.sdk.common.permission.PermissionChecker;
import com.tomtom.online.sdk.location.LocationSource;
import com.tomtom.online.sdk.location.LocationSourceFactory;
import com.tomtom.online.sdk.location.LocationUpdateListener;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.Marker;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private TomtomMap tomtomMap;
    Double lat,longitude;
    private LocationSource locationSource;

    private static final int PERMISSION_REQUEST_LOCATION = 0;
    private LatLng latLngCurrentPosition;
    private LatLng latLng;

    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getAsyncMap(this);
    }

    @Override
    public void onMapReady(@NonNull TomtomMap tomtomMap) {
        Intent intent = getIntent();
        String[] latitude = intent.getStringArrayExtra("latitude");
        String[] longitude = intent.getStringArrayExtra("longitude");


        for (int i=0;i < latitude.length;i++) {
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(Double.valueOf(latitude[i]), Double.valueOf(longitude[i]), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LatLng location = new LatLng(Double.valueOf(latitude[i]), Double.valueOf(longitude[i]));
            SimpleMarkerBalloon balloon = new SimpleMarkerBalloon(addresses.get(0).getAddressLine(0));
            tomtomMap.addMarker(new MarkerBuilder(location).markerBalloon(balloon));
            tomtomMap.centerOn(CameraPosition.builder(location).zoom(11).build());
        }

//        tomtomMap.setMyLocationEnabled(true);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    private final OnMapReadyCallback onMapReadyCallback =
//            new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(TomtomMap map) {
//                    //Map is ready here
//                    tomtomMap = map;
//                    tomtomMap.setMyLocationEnabled(true);
//                }
//            };
}
