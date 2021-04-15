package com.example.mystore.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Adapter.PCAdapter;
import com.example.mystore.Adapter.SanphamAdapter;
import com.example.mystore.Giohang;
import com.example.mystore.Model.CheckConnection;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView, recyclerView1;
    ArrayList<Sanpham> listsp;
    ArrayList<Sanpham> list;
    SanphamAdapter sanphamAdapter;
    PCAdapter PCadapter;
    TextView btnlaptop,btnPC;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        listsp = new ArrayList<>();
        list = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getContext(), listsp);
        PCadapter = new PCAdapter(getContext(), list);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler1);
        btnlaptop = (TextView) view.findViewById(R.id.btnlaptop);
        btnPC = (TextView) view.findViewById(R.id.btnPC);
        if(CheckConnection.CheckNetwork(getContext())) {
            Getlap();
            GetPC();
            click();
        }else{
            CheckConnection.ShowToast(getContext(),"Không có kết nối. Vui lòng kiểm tra kết nối mạng");
        }
        return view;
    }


    private void GetPC() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.GetNewPC, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("getDataSuccess", response.length() + "ok");
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int id = jsonObject.getInt("Id");
                            String ten = jsonObject.getString("tensp");
                            Integer gia = jsonObject.getInt("gia");
                            String hinhanh = jsonObject.getString("hinhanhsp");
                            int soluong = jsonObject.getInt("soluong");
                            String motasp = jsonObject.getString("mota");
                            int idsanpham = jsonObject.getInt("idsanpham");

                            list.add(new Sanpham(id, ten, gia, hinhanh, soluong, motasp, idsanpham));
                            PCadapter.notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(PCadapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager);
    }

    private void Getlap() {
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Server.GetNewLap, new Response.Listener<JSONArray>() {
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

                            listsp.add(new Sanpham(id, ten, gia, hinhanh, soluong, motasp, idsanpham));
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(jsonArrayRequest1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(sanphamAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
    public void click(){
        btnlaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AllLaptop.class);
                startActivity(intent);
            }
        });
        btnPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AllPC.class);
                startActivity(intent);
            }
        });
    }
}
