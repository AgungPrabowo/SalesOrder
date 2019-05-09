package com.example.root.salesorder.Model;

public class ModelPelanggan {
    String id, nama, email, alamat, phone;

    public ModelPelanggan(String id, String nama, String email, String alamat, String phone) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.alamat = alamat;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
