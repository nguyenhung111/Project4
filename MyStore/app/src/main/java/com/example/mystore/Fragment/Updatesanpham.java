package com.example.mystore.Fragment;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.Model.Server;
import com.example.mystore.QuanLySanPham;
import com.example.mystore.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Updatesanpham extends AppCompatActivity {
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
                String tensp = tvTen.getText().toString();
                String mota = tvMota.getText().toString();
                int giasp = Integer.parseInt(tvGia.getText().toString());
                String query = "UPDATE sanpham SET tensanpham = '"+tensp+"', mota = '"+mota+"', gia = '"+giasp+"' WHERE Id = '"+id+"'";
                Update(query);
                finish();
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
    public void Update(final String query) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.updateDonHang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            Toast.makeText(getApplication(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplication(), "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Error!", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);
    }
}
