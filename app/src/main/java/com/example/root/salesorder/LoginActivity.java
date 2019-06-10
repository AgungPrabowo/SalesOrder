package com.example.root.salesorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.salesorder.Adapter.Model;
import com.example.root.salesorder.util.BaseApiService;
import com.example.root.salesorder.util.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.btn_login) Button btn_login;
    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    SharedPrefManager sharedPrefManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
//        getSupportActionBar().hide();

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper
        sharedPrefManager = new SharedPrefManager(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("")){
                    Toast.makeText(mContext, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals("")){
                    Toast.makeText(mContext, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    loading = ProgressDialog.show(mContext, null, "Mohon Tunggu...", true, false);
                    requestLogin();
                }
            }
        });

        // berfungsi untuk mengecek session, Jika session true ( sudah login )
        // maka langsung memulai MainActivity.
        if (sharedPrefManager.getSPSudahLogin()){
            Model model = new Model();
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

    }

    private void requestLogin() {
        mApiService.loginRequest(email.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONObject jsonRESULTS;
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                jsonRESULTS = new JSONObject(response.body().string());
//                                get array data
                                if (jsonRESULTS.getString("msg").equals("success")){
                                    JSONArray array = jsonRESULTS.getJSONArray("data");
                                    JSONObject jsonObject = array.getJSONObject(0);
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.
                                    Toast.makeText(mContext,     "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                    // Shared Pref ini berfungsi untuk menjadi trigger session login
                                    String karyawan_id = jsonObject.getString("id");
                                    Log.d("tes", karyawan_id);
//                                    String role_id = jsonObject.getString("role_id");
//                                    String acc_token = jsonRESULTS.getString("access_token");
//                                    String name = jsonRESULTS.getJSONObject("user").getString("name");
                                    String name = jsonObject.getString("nama_depan");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_KARYAWAN_ID, karyawan_id);
//                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLE_ID, role_id);
//                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ACC_TOKEN, acc_token);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, name);
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                    startActivity(new Intent(mContext, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                            // Jika login gagal
                            Toast.makeText(mContext, "Login Gagal   ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }

}
