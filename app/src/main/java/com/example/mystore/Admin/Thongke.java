package com.example.mystore.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Adapter.AdapterThongKe;
import com.example.mystore.Model.Server;
import com.example.mystore.Model.ThongKe;
import com.example.mystore.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Thongke extends AppCompatActivity {
    BarChart barChart;
    Toolbar toolbar;
    Button button,thongkeNam,thongkeThang;
    RecyclerView recyclerView;
    ArrayList<ThongKe> arrayList;
    ArrayList<ThongKe> arrayList1;
    ArrayList<ThongKe> arrayList2;
    EditText dulieu;
    String thang = "";
    int tongtien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        init();
        Click();
        barChart = findViewById(R.id.barchart);

    }

    public void Click() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thongke();
            }
        });
        thongkeNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String tukhoa = dulieu.getText().toString().trim();
//                String query =
//                        "SELECT Year(ngaydathang) as thoigian, sum(tongtien) as tongtien FROM donhang WHERE trangthai = 4 AND Year(ngaydathang) >= "+tukhoa+ -4+" AND  Year(ngaydathang) <=  "+tukhoa+" GROUP BY Year(ngaydathang)";
                Nam();
            }
        });
        thongkeThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String tukhoa = dulieu.getText().toString().trim();
//                String query = "SELECT Month(ngaydathang) as thoigian, sum(tongtien) as tongtien FROM donhang WHERE trangthai = 4 AND Year(ngaydathang) =  "+tukhoa+" GROUP BY Month(ngaydathang)";
                Thang();
            }
        });
    }

    public void init() {
        dulieu = (EditText) findViewById(R.id.dulieu);
        toolbar = (Toolbar) findViewById(R.id.toolbartk);
        button = (Button) findViewById(R.id.btnthang);
        thongkeNam = (Button) findViewById(R.id.thongkeNam);
        thongkeThang = (Button) findViewById(R.id.thongkeThang);
        recyclerView = findViewById(R.id.recTK);

        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();
    }
    public void Thongke() {
        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.ketnoi+ year, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            thang = jsonObject.getString("thoigian");
                            tongtien = jsonObject.getInt("tongtien");
                            arrayList1.add(new ThongKe(thang, null, tongtien));
                            ArrayList<BarEntry> dataVal = new ArrayList<>();
                            for(int a = 0 ; a < arrayList1.size(); a++) {
                                dataVal.add(new BarEntry(Float.parseFloat(arrayList1.get(a).getThang()),arrayList1.get(a).getTien()));
                                BarDataSet barDataSet = new BarDataSet(dataVal, "Thống kê doanh thu tháng ");
                                barDataSet.setValueTextColor(Color.BLACK);
                                barDataSet.setValueTextSize(15f);
                                BarData barData = new BarData(barDataSet);
                                barChart.setFitBars(true);
                                barChart.setData(barData);
                                barChart.getDescription().setText("Biểu đồ doanh thu theo tháng");
                                barChart.animateY(4000);

                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                XAxis xAxis = barChart.getXAxis();
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setGranularity(1);
                                xAxis.setGranularityEnabled(true);
                                barChart.setDragEnabled(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    public void Thang(){
        arrayList2.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Server.ketnoithang + dulieu.getText().toString(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String thoigian = jsonObject.getString("thoigian");
                                int tongtiennam = jsonObject.getInt("tongtien");
                                arrayList2.add(new ThongKe(null,thoigian,tongtiennam));

                                ArrayList<BarEntry> dataValu = new ArrayList<>();
                                for(int x = 0 ; x < arrayList2.size();x++) {
                                    dataValu.add(new BarEntry(Float.parseFloat(arrayList2.get(x).getNam()),arrayList2.get(x).getTien()));
                                    BarDataSet barDataSet = new BarDataSet(dataValu, "Thống kê doanh thu theo tháng của năm  ");
                                    barDataSet.setValueTextColor(Color.BLACK);
                                    barDataSet.setValueTextSize(15f);
                                    BarData barData = new BarData(barDataSet);
                                    barChart.setFitBars(true);
                                    barChart.setData(barData);
                                    barChart.getDescription().setText("Biểu đồ doanh thu theo tháng của năm ");
                                    barChart.animateY(4000);

                                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                    XAxis xAxis = barChart.getXAxis();
                                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                    xAxis.setGranularity(1);
                                    xAxis.setGranularityEnabled(true);
                                    barChart.setDragEnabled(true);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                hashMap.put("year", dulieu.getText().toString());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void Nam(){
        arrayList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Server.ketnoinam+ dulieu.getText().toString(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String thoigian = jsonObject.getString("thoigian");
                            int tongtiennam = jsonObject.getInt("tongtien");

                            Toast.makeText(Thongke.this, "Năm " +thoigian, Toast.LENGTH_SHORT).show();
                            Toast.makeText(Thongke.this, "Tổng tiền  " +tongtiennam, Toast.LENGTH_SHORT).show();
                            arrayList.add(new ThongKe(null,thoigian,tongtiennam));
                            ArrayList<BarEntry> dataValu = new ArrayList<>();
                            for(int x = 0; x < arrayList.size();x++) {
                                dataValu.add(new BarEntry(x, arrayList.get(x).getTien()));
                                BarDataSet barDataSet = new BarDataSet(dataValu, "Thống kê doanh thu theo từng năm ");
                                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                barDataSet.setValueTextColor(Color.BLACK);
                                barDataSet.setValueTextSize(20f);

                                BarData barData = new BarData(barDataSet);
                                barChart.setFitBars(true);
                                barChart.setData(barData);
                                barChart.getDescription().setText("Biểu đồ doanh thu theo từng năm");
                                barChart.animateY(1000);

                                XAxis xAxis = barChart.getXAxis();
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setGranularity(1);
                                xAxis.setGranularityEnabled(true);
                                barChart.setDragEnabled(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                hashMap.put("year",dulieu.getText().toString() );
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}

