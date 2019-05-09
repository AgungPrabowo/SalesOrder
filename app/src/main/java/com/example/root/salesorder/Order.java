package com.example.root.salesorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.salesorder.util.BaseApiService;
import com.example.root.salesorder.util.UtilsApi;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Order extends AppCompatActivity {
    @BindView(R.id.nm_client) EditText nm_client;
//    @BindView(R.id.alamat_client) EditText alamat_pembeli;
    @BindView(R.id.submit_order) Button submit_order;
    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order);
//        setContentView(R.layout.footer_order);
//
//        ButterKnife.bind(this);
//        mContext = this;
//        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper
//
//        submit_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loading = ProgressDialog.show(mContext, null, "Mohon Tunggu...", true, false);
//                validateForm();
//            }
//        });
    }

    private void postOrder() {

    }

    private void validateForm() {
        if (nm_client.getText() == null) {
            Toast.makeText(mContext, "Nama Client tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
    }
}
