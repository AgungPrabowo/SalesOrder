package com.example.root.salesorder.Model;

public class ModelOrderInvoice {
    String plg, total;

    public ModelOrderInvoice(String plg, String total) {
        this.plg = plg;
        this.total = total;
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
