package com.example.root.salesorder.util;

import com.example.root.salesorder.Model.ModelPelanggan;
import com.example.root.salesorder.ModelOrder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface BaseApiService {
    // fungsi untuk memanggil api
    @FormUrlEncoded
    @POST("auth")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("attendance/in")
    Call<ResponseBody> attendanceInRequest(@Field("location_id") String location_id,
                                           @Field("employee_id") String employee_id,
                                           @Field("latitude") double latitude,
                                           @Field("longitude") double longitude,
                                           @Field("check") int check);
    @FormUrlEncoded
    @POST("attendance/out/{id}")
    Call<ResponseBody> attendanceOutRequest(@Field("location_id") String location_id,
                                            @Field("employee_id") String employee_id,
                                            @Field("latitude") double latitude,
                                            @Field("longitude") double longitude,
                                            @Field("check") int check,
                                            @Path("id") String id);

    @GET("barang")
    Call<List<ModelOrder>> getBarang();

    @GET("pelanggan")
    Call<List<ModelPelanggan>> getPelanggan();

    @GET("order")
    Call<List<ModelOrder1>> getOrder();

    @FormUrlEncoded
    @POST("order")
    Call<ResponseBody> postBarang(@Field("karyawan_id") String karyawan_id,
                                  @Field("barang_id") String barang_id,
                                  @Field("pelanggan_id") String pelanggan_id,
//                                  @Field("nama") String nama,
//                                  @Field("alamat") String alamat,
                                  @Field("qty") String qty,
                                  @Field("latitude") double latitude,
                                  @Field("longitude") double longitude);

}
