package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Adapter.SanphamAdapter;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.Model.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DanhMuc extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Sanpham> list;
    SanphamAdapter sanphamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        intit();
        ActionTool();

        Intent intent = getIntent();
        String DanhMuc = intent.getStringExtra("danhmuc");
        if(DanhMuc.equals("acer")){
            GetInfor(Server.GetAcer);
        }else if(DanhMuc.equals("asus")){
            GetInfor(Server.GetAsus);
        }else if(DanhMuc.equals("dell")){
            GetInfor(Server.GetDell);
        }else if(DanhMuc.equals("hp")){
            GetInfor(Server.GetHP);
        }else if(DanhMuc.equals("lenovo")){
            GetInfor(Server.GetLenovo);
        }else if(DanhMuc.equals("msi")){
            GetInfor(Server.GetMsi);
        }else if(DanhMuc.equals("gia1")){
            GetInfor(Server.GetGia1);
        }else if(DanhMuc.equals("gia2")){
            GetInfor(Server.GetGia2);
        }else if(DanhMuc.equals("gia3")){
            GetInfor(Server.GetGia3);
        }else if(DanhMuc.equals("gia4")){
            GetInfor(Server.GetGia4);
        }else if(DanhMuc.equals("gia5")){
            GetInfor(Server.GetGia5);
        }else if(DanhMuc.equals("gia6")) {
            GetInfor(Server.GetGia6);
        }
    }
    private void ActionTool() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void GetInfor(String link) {
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response1) {
                Log.e("getDataSuccess1", response1.length() + "ok");
                if (response1.length() > 0) {
                    for (int i = 0; i < response1.length(); i++) {
                        try {
                            JSONObject jsonObject1 = response1.getJSONObject(i);
                            int id = jsonObject1.getInt("Id");
                            String ten = jsonObject1.getString("tensp");
                            Integer gia = jsonObject1.getInt("gia");
                            String hinhanh = jsonObject1.getString("hinhanhsp");
                            int soluong = jsonObject1.getInt("soluong");
                            String motasp = jsonObject1.getString("mota");
                            int idsanpham = jsonObject1.getInt("idsanpham");

                            list.add(new Sanpham(id, ten, gia, hinhanh, soluong, motasp, idsanpham));
                            sanphamAdapter.notifyDataSetChanged();
                            //ok nhé

                        } catch (Exception e) {
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(jsonArrayRequest1);
        recyclerView.setAdapter(sanphamAdapter);
    }

    public void intit(){
        toolbar = (Toolbar) findViewById(R.id.tolDanhMuc);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerDanhmuc);
        list = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(this,list);
        recyclerView.setAdapter(sanphamAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
    }
}
