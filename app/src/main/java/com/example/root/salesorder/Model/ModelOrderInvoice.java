package com.example.root.salesorder.Model;

import java.util.List;

public class ModelOrderInvoice {
    String plg, total;
    List<String> nmBrg, harga;

    public ModelOrderInvoice(String plg, String total, List nmBrg, List harga) {
        this.plg = plg;
        this.total = total;
        this.nmBrg = nmBrg;
        this.harga = harga;
    }

    public List<String> getHarga() {
        return harga;
    }

    public void setHarga(List<String> harga) {
        this.harga = harga;
    }

    public List<String> getNmBrg() {
        return nmBrg;
    }

    public void setNmBrg(List<String> nmBrg) {
        this.nmBrg = nmBrg;
    }

    public String getPlg() {
        return plg;
    }

    public void setPlg(String plg) {
        this.plg = plg;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
