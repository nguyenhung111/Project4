package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Adapter.SodiachiAdapter;
import com.example.mystore.Model.Dathang;
import com.example.mystore.Model.Diachi;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.Model.Server;
import com.example.mystore.TimKiem.TimKiem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sodiachi extends AppCompatActivity {
    private ListView lvdiachi;
    private Button btnthemdiachi;
    private Toolbar toolbar;
    ArrayList<Diachi> listdiachi;
    SodiachiAdapter sodiachiAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sodiachi);
        lvdiachi = findViewById(R.id.listdiachi);
        btnthemdiachi = findViewById(R.id.themdiachi);
        toolbar = findViewById(R.id.toolbarsodiachi);
        listdiachi = new ArrayList<>();
        sodiachiAdapter = new SodiachiAdapter(Sodiachi.this, listdiachi);
        lvdiachi.setAdapter(sodiachiAdapter);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Dathang.class));
            }
        });
        initShare();
        EventClick();
        GetDiachi();

    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void DeleteDiachi(int iddc) {
        final String query = "DELETE FROM diachigiaohang WHERE id = '" + iddc + "'";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.linkDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            Toast.makeText(Sodiachi.this, "Delete success!", Toast.LENGTH_LONG).show();
                            sodiachiAdapter.notifyDataSetChanged();
                            GetDiachi();
                        } else {
                            Toast.makeText(Sodiachi.this, "Delete Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Sodiachi.this, "Error!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("query", query);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void EventClick() {
        btnthemdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Themdiachi.class));
            }
        });
    }

    private void GetDiachi() {
        listdiachi.clear();
        final int matv = sharedPreferences.getInt("matv", 0);
        Log.e("matv",String.valueOf(matv));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Server.linkdiachi+matv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.contains("null")) {
                        Toast.makeText(Sodiachi.this, "Bạn chưa có địa chỉ nào? Vui lòng thêm địa chỉ!", Toast.LENGTH_LONG).show();
                    } else {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                int Id = jsonObject1.getInt("Id");
                                int Makh = jsonObject1.getInt("Matv");
                                String Hoten = jsonObject1.getString("Hoten");
                                String SDT = jsonObject1.getString("Sdt");
                                String Diachi = jsonObject1.getString("Diachi");

                                listdiachi.add(new Diachi(Id, Makh, Hoten, SDT, Diachi));
                                sodiachiAdapter.notifyDataSetChanged();
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
                hashMap.put("Matv", String.valueOf(matv));
                return hashMap;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
