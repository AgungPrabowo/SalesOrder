package com.example.root.salesorder.util;

public class ModelOrder1 {
    String karyawan_id;
    String created_at;
    String latitude;
    String longitude;


    public ModelOrder1(String karyawan_id, String created_at, String latitude, String longitude) {
        this.created_at = created_at;
        this.latitude = latitude;
        this.longitude = longitude;
        this.karyawan_id = karyawan_id;
    }

    public String getKaryawan_id() {
        return karyawan_id;
    }

    public void setKaryawan_id(String karyawan_id) {
        this.karyawan_id = karyawan_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
