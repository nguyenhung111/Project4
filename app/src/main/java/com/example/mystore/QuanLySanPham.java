
package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.mystore.Adapter.PCAdapter;
import com.example.mystore.Adapter.QuanLySPAdapter;
import com.example.mystore.Admin.Themsanpham;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.Model.Server;
import com.example.mystore.TimKiem.TimKiem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanLySanPham extends AppCompatActivity {
    Toolbar toolbar;
    EditText editText;
    ImageButton btnSearch,themsp;
    String tukhoa;
    public static RecyclerView recyclerView;
    public static ArrayList<Sanpham> list;
    public static QuanLySPAdapter sanphamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        intit();
        GetInfor();
        ActionTool();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                tukhoa = editText.getText().toString();
                if (!tukhoa.isEmpty()) {
                    Search();
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập từ khóa", Toast.LENGTH_LONG).show();
                }
            }
        });
        themsp = findViewById(R.id.themsp);
        themsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Themsanpham.class));
            }
        });
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

    public void GetInfor() {
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Server.GetAll, new Response.Listener<JSONArray>() {
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
        toolbar = (Toolbar) findViewById(R.id.toolQuanLy);
        recyclerView = (RecyclerView) findViewById(R.id.recQuanLy);
        editText = (EditText) findViewById(R.id.edtsearch);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        tukhoa = editText.getText().toString();
        list = new ArrayList<>();
        sanphamAdapter = new QuanLySPAdapter (this,list);
        recyclerView.setAdapter(sanphamAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
    }
    public void Search(){
        tukhoa = editText.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.GetTen;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + tukhoa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("null")) {
                        Toast.makeText(getApplication(), "Sản phẩm bạn cần tìm không có!",Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                int id = jsonObject1.getInt("Id");
                                String ten = jsonObject1.getString("tensp");
                                Integer gia = jsonObject1.getInt("gia");
                                String hinhanh = jsonObject1.getString("hinhanhsp");
                                int soluong = jsonObject1.getInt("soluong");
                                String motasp = jsonObject1.getString("mota");
                                int idsanpham = jsonObject1.getInt("idsanpham");
                                list.add(new Sanpham(id, ten, gia, hinhanh, soluong, motasp, idsanpham));
                                sanphamAdapter.notifyDataSetChanged();
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
                hashMap.put("ten", tukhoa);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

}
