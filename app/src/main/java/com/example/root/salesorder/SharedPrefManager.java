package com.example.root.salesorder;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_PRESENSI_APP = "Presensi";
    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";
//    public static final String SP_ROLE_ID = "spRole_id";
    public static final String SP_KARYAWAN_ID = "spKaryawan_id";
    public static final String SP_ACC_TOKEN = "spAccToken";
    public static final String SP_BUTTON_STATUS = "spButtonStatus";
    public static final String SP_ABSEN_ID = "spAbsenId";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
//    Context mContext;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPInteger(String keySP, Integer value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpAbsenId() {
        return sp.getString(SP_ABSEN_ID, "");
    }

    public Integer getSPButtonStatus() {
        return sp.getInt(SP_BUTTON_STATUS, 1);
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

//    public String getSPRoleID(){
//        return sp.getString(SP_ROLE_ID, "");
//    }

    public String getSPKaryawanID(){
        return sp.getString(SP_KARYAWAN_ID, "");
    }

    public String getSPAccToken() {
        return sp.getString(SP_ACC_TOKEN, "");
    }

}
