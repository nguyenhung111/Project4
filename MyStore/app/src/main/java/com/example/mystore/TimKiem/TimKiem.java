package com.example.mystore.TimKiem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Adapter.SanphamAdapter;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.Model.Server;
import com.example.mystore.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimKiem extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText editText;
    ImageButton btnsearch;
    ArrayList<Sanpham> list;
    SanphamAdapter sanphamAdapter;
    String tukhoa;
    Sanpham sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        init();
        ActionTool();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbartk);
        recyclerView = (RecyclerView) findViewById(R.id.recyclertk);
        editText = (EditText) findViewById(R.id.edtsearch);
        btnsearch = (ImageButton) findViewById(R.id.btnSearch);

        list = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(this, list);
        recyclerView.setAdapter(sanphamAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                tukhoa = editText.getText().toString().toLowerCase().trim();
                if (!tukhoa.isEmpty()) {
                    String query = "SELECT * FROM sanpham WHERE tensanpham like '%"+ tukhoa +"%' ";
                    Log.d("tukhoa",query);
                    Load_user(query);
                } else {
                    Toast.makeText(getApplicationContext(), "Vui l??ng nh???p t??? kh??a", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Load_user(final String query) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.GetSearch, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("null")) {
                        Toast.makeText(TimKiem.this, "S???n ph???m b???n c???n t??m kh??ng c??!",Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                int id = jsonObject1.getInt("id");
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
                hashMap.put("query_user", query);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
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

}
