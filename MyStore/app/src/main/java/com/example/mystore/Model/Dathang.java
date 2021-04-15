package com.example.mystore.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Adapter.DathangAdapter;
import com.example.mystore.Giohang;
import com.example.mystore.MainActivity;
import com.example.mystore.R;
import com.example.mystore.Sodiachi;
import com.example.mystore.Themdiachi;
import com.example.mystore.TimKiem.TimKiem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Dathang extends AppCompatActivity {
    private ListView lvdathang;
    private TextView tvtenkh, tvsdtkh, tvdiachi, tvthaydoi, tvphiship;
    private TextView tvtamtinh, tvthanhtien;
    private Button btnxacnhan;
    private Toolbar toolbar;

    String hoten = "";
    String sdt = "";
    String diachikh = "";
    long tongtien = 0;
    long thanhtien = 0;
    long phiship = 0;

    DathangAdapter dathangAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);

        Anhxa();
        initShare();
        ActionToolbar();
        EventData();
        EventClick();
        GetDiachi();
    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void GetDiachi() {
        Intent intent = getIntent();
        hoten = intent.getStringExtra("hoten");
        sdt = intent.getStringExtra("sdt");
        diachikh = intent.getStringExtra("diachi");

        tvtenkh.setText(hoten);
        tvsdtkh.setText(sdt);
        tvdiachi.setText(diachikh);
    }

//    private void Diachi() {
//        String ten = "";
//        if (ten.equals("")) {
//            int matv = sharedPreferences.getInt("matv", 0);
//            final String query = "SELECT * FROM diachigiaohang WHERE  matv  = '" + matv + "' ";
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.linkdiachi, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        if (response.contains("null")) {
//                            Toast.makeText(Dathang.this, "Bạn chưa có địa chỉ nào? Vui lòng thêm địa chỉ!", Toast.LENGTH_LONG).show();
//                        } else {
//                            JSONArray jsonArray = new JSONArray(response);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                try {
//                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                    int Id = jsonObject1.getInt("Id");
//                                    int Makh = jsonObject1.getInt("Matv");
//                                    String Hoten = jsonObject1.getString("Hoten");
//                                    String SDT = jsonObject1.getString("Sdt");
//                                    String Diachi = jsonObject1.getString("Diachi");
//                                    tvtenkh.setText(Hoten);
//                                    tvsdtkh.setText(SDT);
//                                    tvdiachi.setText(Diachi);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.d("loiii", error.toString());
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    HashMap<String, String> hashMap = new HashMap<>();
//                    hashMap.put("query", query);
//                    return hashMap;
//                }
//
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            requestQueue.add(stringRequest);
//        } else if(!TextUtils.isEmpty(ten)) {
//            tvtenkh.setText("0");
//            tvdiachi.setText("0");
//            tvsdtkh.setText("0");
//            GetDiachi();
//
//        }
//    }

    private void EventClick() {
        final int makh = sharedPreferences.getInt("matv", 0);

        btnxacnhan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.linkdonhang, new Response.Listener<String>() {

                    @Override
                    public void onResponse(final String madonhang) {
                        if (Integer.parseInt(madonhang) > 0) {
                            StringRequest request = new StringRequest(Request.Method.POST, Server.linkchitietdonhang,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.contains("thanhcong")) {
                                                MainActivity.mangiohang.clear();
                                                Toast.makeText(Dathang.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Dathang.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(Dathang.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    JSONArray jsonArray = new JSONArray();
                                    for (int i = 0; i < MainActivity.mangiohang.size(); i++) {
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("madonhang", madonhang);
                                            jsonObject.put("masp", MainActivity.mangiohang.get(i).getIdsp());
                                            Log.e("masanpham", String.valueOf(MainActivity.mangiohang.get(i).getIdsp()));
                                            jsonObject.put("tensp", MainActivity.mangiohang.get(i).getTensp());
                                            Log.e("tensp", MainActivity.mangiohang.get(i).getTensp());
                                            jsonObject.put("soluong", MainActivity.mangiohang.get(i).getSoluongsp());
                                            Log.e("soluong", String.valueOf(MainActivity.mangiohang.get(i).getSoluongsp()));
                                            jsonObject.put("thanhtien", MainActivity.mangiohang.get(i).getGia() + 30000);
                                            Log.e("thanh tien", String.valueOf(MainActivity.mangiohang.get(i).getGia()));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(jsonObject);

                                    }
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("json", jsonArray.toString());
                                    return hashMap;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);
                        }
                        Log.d("madonhang", madonhang);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("makh", String.valueOf(makh));
                        params.put("hoten", hoten);
                        params.put("sdt", sdt);
                        params.put("diachi", diachikh);
                        params.put("tongtien", String.valueOf(thanhtien));
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });

        tvthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sodiachi.class));
            }
        });
    }

    public void EventData() {
        for (int i = 0; i < MainActivity.mangiohang.size(); i++) {
            tongtien += MainActivity.mangiohang.get(i).getGia();
            if (MainActivity.mangiohang.size() > 1 || MainActivity.mangiohang.get(i).getSoluongsp() > 1) {
                phiship = 0;
                thanhtien = tongtien + phiship;
            } else {
                phiship = 30000;
                thanhtien = tongtien + phiship;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvtamtinh.setText(decimalFormat.format(tongtien) + " đ");
        tvphiship.setText(decimalFormat.format(phiship) + " đ");
        tvthanhtien.setText(decimalFormat.format(thanhtien) + " đ");
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//set hình <-
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Giohang.class));
            }
        });
    }

    private void Anhxa() {
        tvtenkh = findViewById(R.id.tvtenkh);
        tvsdtkh = findViewById(R.id.tvsdtkh);
        tvdiachi = findViewById(R.id.tvdiachikh);
        tvthaydoi = findViewById(R.id.tvthaydoi);
        tvtamtinh = findViewById(R.id.tamtinh);
        tvphiship = findViewById(R.id.phiship);
        tvthanhtien = findViewById(R.id.thanhtien);
        btnxacnhan = findViewById(R.id.xacnhan);
        lvdathang = findViewById(R.id.listdathang);
        toolbar = findViewById(R.id.toolbardathang);

        dathangAdapter = new DathangAdapter(Dathang.this, MainActivity.mangiohang);
        lvdathang.setAdapter(dathangAdapter);
    }
}
