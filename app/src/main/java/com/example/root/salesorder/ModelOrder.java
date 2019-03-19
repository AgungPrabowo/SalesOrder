package com.example.root.salesorder;

import com.example.root.salesorder.Adapter.Model;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class ModelOrder extends Model {
    String nama;
    String jenis;
    String harga;
    String stok;
    String jumlah;
    String id;
    String longitude;
    String latitude;
    String id_karyawan;

    public ModelOrder(String id, String barang, String satuan, String harga, String stok, String jumlah, String longitude, String latitude, String id_karyawan) {
        this.nama = barang;
        this.jenis = satuan;
        this.harga = harga;
        this.stok = stok;
        this.jumlah = jumlah;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id_karyawan = id_karyawan;
    }

    public String getId_karyawan() {
        return id_karyawan;
    }

    public void setId_karyawan(String id_karyawan) {
        this.id_karyawan = id_karyawan;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @SerializedName("body")
    private String text;


    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getBarang() {
        return nama;
    }

    public void setBarang(String barang) {
        this.nama = barang;
    }

    public String getSatuan() {
        return jenis;
    }

    public void setSatuan(String satuan) {
        this.jenis = satuan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStock() {
        return stok;
    }

    public void setStock(String stock) {
        this.stok = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
