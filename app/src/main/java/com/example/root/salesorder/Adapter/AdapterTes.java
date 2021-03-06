package com.example.root.salesorder.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.salesorder.MainActivity;
import com.example.root.salesorder.Model.ModelPelanggan;
import com.example.root.salesorder.ModelOrder;
import com.example.root.salesorder.R;
import com.example.root.salesorder.SharedPrefManager;
import com.example.root.salesorder.util.BaseApiService;
import com.example.root.salesorder.util.GPSTracker;
import com.example.root.salesorder.util.UtilsApi;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.content.ContextCompat.startActivity;

public class AdapterTes extends RecyclerView.Adapter<AdapterTes.MyViewHolder> {
    public static final int Header = 1;
    public static final int Normal = 2;
    public static final int Footer = 3;

    String nm, alamatClient;
    String pelanggan_id = "0";

    private LayoutInflater inflater;
    private List<Model> headerModelArrayList;
    private List<ModelOrder> dataOrder;
    List<ModelPelanggan> dataP;
    ArrayList<String> arrayPelanggan = new ArrayList<String>();

    private Context context;
    ProgressDialog loading;

    BaseApiService mApiService;



    public AdapterTes(Context mContext, List<Model> dataSet, List<ModelOrder> dataBarang, List<ModelPelanggan> dataPelanggan) {
//        this.dataSet = dataSet;
//        this.mContext = mContext;
//        total_types = dataSet.size();

        this.context = mContext;
        this.headerModelArrayList = dataSet;
        this.dataOrder = dataBarang;
        this.dataP = dataPelanggan;
        if(dataP!=null) {
            System.out.println("dataP ada di constructor");
        } else {
            System.out.println("dataP tidak ada di constructork");
        }
    }

   public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView barang, stock, harga, satuan;
        Button submit;
        EditText nama, alamat, jumlah;
       AutoCompleteTextView autoCompleteTextView;

        public MyViewHolder(final View itemView, int viewType) {
            super(itemView);

            if (viewType == Normal) {
                barang = (TextView) itemView.findViewById(R.id.barang);
                stock = (TextView) itemView.findViewById(R.id.stock);
                harga = (TextView) itemView.findViewById(R.id.harga);
                satuan = (TextView) itemView.findViewById(R.id.satuan);
                jumlah = (EditText) itemView.findViewById(R.id.jumlah);

            } else if (viewType == Header) {
//                nama = (EditText) itemView.findViewById(R.id.nm_client);
//                alamat = (EditText) itemView.findViewById(R.id.alamat_client);
                autoCompleteTextView = itemView.findViewById(R.id.nm_client);
//                final TextView nmV = itemView.findViewById(R.id.textView3);
//                nmV.setVisibility(View.INVISIBLE);
//                autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        nmV.setVisibility(View.VISIBLE);
//                    }
//                });

//                int countPelanggan = dataP.size();
                if(dataP!=null){
                    for (int w = 0;w < dataP.size();w++) {
                        arrayPelanggan.add(dataP.get(w).getNama());
                    }
                } else {
                    Toast.makeText(itemView.getContext(), "data pelanggan kosong header", Toast.LENGTH_SHORT).show();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(itemView.getContext(),android.R.layout.simple_list_item_1,arrayPelanggan);
                autoCompleteTextView.setAdapter(adapter);

            } else if (viewType == Footer) {
                submit = (Button) itemView.findViewById(R.id.submit_order);
                mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (nm.isEmpty()) {
                            Toast.makeText(itemView.getContext(), "Pelanggan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        } else {
                            getIdNama();
                            if(pelanggan_id.equals("0")){
                                Toast.makeText(itemView.getContext(), "Pelanggan tidak terdaftar", Toast.LENGTH_SHORT).show();
                            } else {
                                loading = ProgressDialog.show(itemView.getContext(), null, "Mohon Tunggu...", true, false);

                                String dataBarang[] = new String[dataOrder.size()-1];
                                String dataQty[] = new String[dataOrder.size()-1];
                                for (int i=0;i < dataOrder.size()-1;i++) {
                                    ModelOrder mo = dataOrder.get(i+1);
                                    dataBarang[i] = mo.getId();
                                    dataQty[i] = mo.getJumlah();
                                }
                                String dataQtyString = mytoString(dataQty, ", ");
                                String dataBarangString = mytoString(dataBarang, ", ");
                                String latitude = dataOrder.get(0).getLatitude();
                                String longitude = dataOrder.get(0).getLongitude();
                                Date datee = new Date(); // this object contains the current date value
                                SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
                                formatter.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
                                System.out.println(formatter.format(datee));
                                mApiService.postBarang(dataOrder.get(0).getId_karyawan(), dataBarangString,
                                        pelanggan_id, dataQtyString, Double.valueOf(latitude), Double.valueOf(longitude), formatter.format(datee))
                                        .enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                JSONObject jsonRESULTS;
                                                if (response.isSuccessful()) {
                                                    loading.dismiss();
//                                            arrayPelanggan.clear();
                                                    Toast.makeText(itemView.getContext(), "Berhasil input order", Toast.LENGTH_SHORT).show();
                                                    // update data recycler view
                                                    Intent intent = new Intent(itemView.getContext(), MainActivity.class);
                                                    itemView.getContext().startActivity(intent);
                                                } else {

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                            }
                                        });
                            }
                        }

//                        ModelOrder mo = dataOrder.get(1);
//                        Model model = new Model();

//                        System.out.println(dataQtyString);
//                        System.out.println(mo.getId());
//                        System.out.println(mo.getJumlah());
//                        System.out.println(nm);
//                        System.out.println(alamatClient);
//
//                        System.out.println(dataOrder.get(0).getLongitude());
//                        System.out.println(dataOrder.get(0).getLatitude());
//                        System.out.println(latitude+" "+longitude);
//                        System.out.println(dataOrder.get(0).getId_karyawan());
                    }
                });
            }
        }
    }

    public void getIdNama() {
        for (int k = 0;k < dataP.size();k++) {
            if (nm.equals(dataP.get(k).getNama())) {
                pelanggan_id = dataP.get(k).getId();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(headerModelArrayList.get(position).getViewType().equals("header")){
            return Header;
        } else if(headerModelArrayList.get(position).getViewType().equals("normal")){
            return Normal;
        } else {
            return Footer;
        }

    }

    @Override
    public AdapterTes.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view ;
        MyViewHolder holder;

        switch (viewType)
        {
            case Normal:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order, parent, false);
                holder = new MyViewHolder(view , Normal);
                break;

            case Header:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_order, parent, false);
                holder = new MyViewHolder(view , Header);
                break;

            case Footer:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_order, parent, false);
                holder = new MyViewHolder(view , Footer);
                break;

            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order, parent, false);
                holder = new MyViewHolder(view , Normal);
                break;
        }




        return holder;
    }
    //
    //        return null;
    ////        }
    ////                return new HeaderOrderViewHolder(view);
    ////                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_order, parent, false);
    ////            case Model.HEADER_TYPE:
    ////                return new ListOrderViewHolder(view);
    ////                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order, parent, false);
    ////            case Model.LIST_TYPE:
    ////        switch (viewType) {
    //        View view;
    //    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//    @Override

//    }

    @Override
    public void onBindViewHolder(AdapterTes.MyViewHolder holder, final int position) {

        if(headerModelArrayList.get(position).getViewType().equals("header")){

            holder.autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    nm = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

//            holder.alamat.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    alamatClient = s.toString();
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

        }
        else if(headerModelArrayList.get(position).getViewType().equals("footer")){

        }
        else {
//            holder.tvProduct.setText(" Item No : " + headerModelArrayList.get(position).getText());

            ModelOrder mo = dataOrder.get(position);

            holder.barang.setText(mo.getBarang());
            holder.satuan.setText(mo.getSatuan());
            holder.stock.setText(mo.getStock());
            holder.harga.setText(mo.getHarga());

            holder.jumlah.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    dataOrder.get(position).setJumlah(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    //        }
    ////            }
    ////                    break;
    ////                    ((ListOrderViewHolder) holder).harga.setText(mo.getHarga());
    ////                    ((ListOrderViewHolder) holder).stock.setText(mo.getStock());
    ////                    ((ListOrderViewHolder) holder).satuan.setText(mo.getSatuan());
    ////                    ((ListOrderViewHolder) holder).barang.setText(mo.getBarang());
    ////
    ////                    ModelOrder mo = dataOrder.get(position);
    ////                case Model.LIST_TYPE:
    ////                    break;
    ////                    ((HeaderOrderViewHolder) holder).txtnama.setText("Tes Nama");
    ////                case Model.HEADER_TYPE:
    ////            switch (object.type) {
    //        if(object != null) {
    //        Model object = dataSet.get(position);
    //    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//    @Override

//    }

    @Override
    public int getItemCount() {
        return headerModelArrayList.size();
    }

//    public int getCountPel() {
//        return dataP.size();
//    }

    public void nmClient(String nm) {
        this.nm = nm;
    }

//    public void alamatClient(String alamat) {
//        this.alamatClient = alamat;
//    }

    private static String mytoString(String[] theAray, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < theAray.length; i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            String item = theAray[i];
            sb.append(item);
        }
        return sb.toString();
    }
}


