package com.example.mystore.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private void EventClick() {

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChitiet();
            }
        });

        tvthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sodiachi.class));
            }
        });
    }

    public void addChitiet() {
        final int matv = sharedPreferences.getInt("matv", 0);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String strDate = df.format(new Date());
        Log.e("strDate", strDate);
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        JSONObject postData = new JSONObject();
        try {
            for (int i = 0; i < MainActivity.mangiohang.size(); i++) {
                postData.put("matv", matv);
                postData.put("tentv", hoten);
                postData.put("sdt", sdt);
                postData.put("diachi", diachikh);
                postData.put("ngaydathang", strDate);
                postData.put("tongtien", thanhtien);
                postData.put("trangthai", 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.Postdonhang, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.length()>0){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
//                        for (int i = 0; i < response.length(); i++);
                        try {
                            int id = response.getInt("madonhang");
                            for (int i = 0; i < MainActivity.mangiohang.size(); i++) {
                                JSONObject postData = new JSONObject();
                                try {

                                    Log.e("madib", String.valueOf(id));
                                    postData.put("madonhang", id);
                                    postData.put("Id", MainActivity.mangiohang.get(i).getIdsp());
                                    postData.put("tensp", MainActivity.mangiohang.get(i).getTensp());
                                    postData.put("soluong", MainActivity.mangiohang.get(i).getSoluongsp());
                                    postData.put("thanhtien", MainActivity.mangiohang.get(i).getGia());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.Postchitietdonhang, postData,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response1) {
                                                if (response1.length() > 0) {
                                                    Toast.makeText(Dathang.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                    MainActivity.mangiohang.clear();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                requestQueue.add(jsonObjectRequest);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);

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
