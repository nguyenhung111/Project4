package com.example.mystore.Admin;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Adapter.Adapter;
import com.example.mystore.Model.DonHang;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Danggiao extends Fragment {
    ListView listView;
    ArrayList<DonHang> donHang;
    Adapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.viewdonhang, container, false);
    //    EventBus.getDefault().register(this);
        listView = (ListView) view.findViewById(R.id.ListViewDH);
        donHang = new ArrayList<>();
        initShare();
        init();
        sukien();
        return view;

    }

    public void init() {
        adapter = new Adapter(donHang, getContext());
        listView.setAdapter(adapter);
    }

//    @Subscriber(tag = "loginSuccess")
//    private void loginSuccess(boolean b) {
//        sukien();
//    }

    public void sukien() {
        int matv = sharedPreferences.getInt("matv", 0);
        if (matv == 1) {
           // String query = " SELECT * FROM donhang WHERE trangthai = 3 ORDER BY madonhang DESC ";
            GetHoaDon1();
        } else {
         //   String query = " SELECT * FROM donhang WHERE trangthai = 3 AND matv = '" + matv + "' ORDER BY madonhang DESC ";
            GetHoaDon();
        }
    }

    public void initShare() {
        sharedPreferences = getActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void GetHoaDon() {
        donHang.clear();
        final int matvget = sharedPreferences.getInt("matv",0);
        adapter.notifyDataSetChanged();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Server.gethoadonUser+3+"/"+matvget  , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("madonhang");
                            int matv = jsonObject.getInt("matv");
                            String tentv = jsonObject.getString("tentv");
                            int sdt = jsonObject.getInt("sdt");
                            String diachi = jsonObject.getString("diachi");
                            String ngaydat = jsonObject.getString("ngaydathang");
                            int tongtien = jsonObject.getInt("tongtien");
                            String trangthai = jsonObject.getString("trangthai");
                            donHang.add(new DonHang(id, matv, tentv, sdt, diachi, ngaydat, tongtien, trangthai));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //  EventBus.getDefault().post(true, "loginSuccess");
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
                hashMap.put("matv",String.valueOf(matvget));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void GetHoaDon1() {
        donHang.clear();
        int query =  3;
        adapter.notifyDataSetChanged();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Server.getHoaDonAdmin +query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("madonhang");
                            int matv = jsonObject.getInt("matv");
                            String tentv = jsonObject.getString("tentv");
                            int sdt = jsonObject.getInt("sdt");
                            String diachi = jsonObject.getString("diachi");
                            String ngaydat = jsonObject.getString("ngaydathang");
                            int tongtien = jsonObject.getInt("tongtien");
                            String trangthai = jsonObject.getString("trangthai");
                            donHang.add(new DonHang(id, matv, tentv, sdt, diachi, ngaydat, tongtien, trangthai));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //  EventBus.getDefault().post(true, "loginSuccess");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loiii", error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
}
