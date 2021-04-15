package com.example.mystore.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Adapter.DonHangAdapter;
import com.example.mystore.Model.DonHang;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.Model.Server;
import com.example.mystore.QuanLySanPham;
import com.example.mystore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanLyDonHang extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ArrayList<DonHang> donHang;
    public static DonHangAdapter donHangAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);
        init();
        actionToolbar();
        initShare();
        int matv = sharedPreferences.getInt("matv", 0);
        if (matv == 1) {
            GetAdmin();
        } else {
            String query = " SELECT * FROM donhang WHERE matv = '"+matv+"' ORDER BY madonhang DESC ";
            GetHoaDon(query);
        }

    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolDonHang);
        listView = (ListView) findViewById(R.id.ListViewDH);

        donHang = new ArrayList<>();
        donHangAdapter = new DonHangAdapter(donHang, QuanLyDonHang.this);
        listView.setAdapter(donHangAdapter);

    }

    public void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void GetAdmin() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getDonHang, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("getDataSuccess", response.length() + "ok");
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int id = jsonObject.getInt("madonhang");
                            int matv = jsonObject.getInt("matv");
                            String tentv = jsonObject.getString("tentv");
                            int sdt = jsonObject.getInt("sdt");
                            String diachi = jsonObject.getString("diachi");
                            String ngaydat = jsonObject.getString("ngaydat");
                            int tongtien = jsonObject.getInt("tongtien");
                            String trangthai = jsonObject.getString("trangthai");
                            donHang.add(new DonHang(id, matv, tentv, sdt, diachi, ngaydat, tongtien, trangthai));
                            donHangAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        listView.setAdapter(donHangAdapter);
    }

    public void GetHoaDon(final String query) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getNguoidung, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.contains("null")) {
                        Toast.makeText(QuanLyDonHang.this, "Bạn không có hóa đơn nào!", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int id = jsonObject.getInt("madonhang");
                                int matv = jsonObject.getInt("matv");
                                String tentv = jsonObject.getString("tentv");
                                int sdt = jsonObject.getInt("sdt");
                                String diachi = jsonObject.getString("diachi");
                                String ngaydat = jsonObject.getString("ngaydat");
                                int tongtien = jsonObject.getInt("tongtien");
                                String trangthai = jsonObject.getString("trangthai");
                                donHang.add(new DonHang(id, matv, tentv, sdt, diachi, ngaydat, tongtien, trangthai));
                                donHangAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loiii", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("query_user", query);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
