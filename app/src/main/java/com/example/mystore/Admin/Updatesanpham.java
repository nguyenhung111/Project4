package com.example.mystore.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.Model.Server;
import com.example.mystore.QuanLySanPham;
import com.example.mystore.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class  Updatesanpham extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgView,send;
    EditText tvTen, tvGia, tvMota;
    int id = 0;
    String tensanpham = "";
    int gia = 0 ;
    String hinhanh = "";
    int soluong = 0;
    String mota = "";
    int idsanpham = 0;
    QuanLySanPham quanLySanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatesanpham);
        init();
        ActionTool();
        final Intent intent = getIntent();
        Sanpham sanpham = (Sanpham) intent.getSerializableExtra("thongtinchitiet");
        quanLySanPham = new QuanLySanPham();
        id = sanpham.getId();
        tensanpham = sanpham.getTensanpham();
        gia = sanpham.getGia();
        hinhanh = sanpham.getHinhanh();
        soluong =sanpham.getSoluong();
        mota = sanpham.getMota();
        idsanpham = sanpham.getIdsanpham();

        tvTen.setText(tensanpham);
        tvTen.setMaxLines(2);
        tvTen.setEllipsize(TextUtils.TruncateAt.END);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGia.setText(String.valueOf(gia));
        tvMota.setText(mota);
        Picasso.get().load(hinhanh).placeholder(R.drawable.noimage).error(R.drawable.error).into(imgView);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
                startActivity(new Intent(getApplicationContext(), QuanLySanPham.class));
                
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
    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolUpdate);
        imgView = (ImageView) findViewById(R.id.imgv);
        tvTen = (EditText) findViewById(R.id.tvTensp);
        tvGia = (EditText) findViewById(R.id.tvGia);
        tvMota = (EditText) findViewById(R.id.tvMota);
        send = findViewById(R.id.send);
    }
    public void Update() {
     //   String postUrl = "http://192.168.43.55:45455/api/updatesanpham/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        JSONObject postData = new JSONObject();
        //tensp,gia,hinhanhsp,soluong,mota,idsanpham
        try {
            postData.put("Id", id);
            postData.put("tensp", tvTen.getText().toString());
            postData.put("gia", tvGia.getText().toString());
            postData.put("hinhanhsp", hinhanh);
            postData.put("soluong", soluong);
            postData.put("mota", tvMota.getText().toString());
            postData.put("idsanpham", idsanpham);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT , Server.UpdateSanPham+id, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}
