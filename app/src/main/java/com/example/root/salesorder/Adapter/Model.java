package com.example.root.salesorder.Adapter;

public class Model {

    private String viewType;
    private String text;
    private String nm_client;
    private String alamat_client;

    public String getViewType(){
        return viewType;
    }

    public void setViewType(String viewType){
        this.viewType = viewType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNm_client() {
        return nm_client;
    }

    public void setNm_client(String nm_client) {
        this.nm_client = nm_client;
    }

    public String getAlamat_client() {
        return alamat_client;
    }

    public void setAlamat_client(String alamat_client) {
        this.alamat_client = alamat_client;
    }
}
