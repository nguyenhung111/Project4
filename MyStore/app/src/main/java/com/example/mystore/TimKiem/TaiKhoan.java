package com.example.mystore.TimKiem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.DangNhap.FragmentDangNhap;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class TaiKhoan extends AppCompatActivity {
    EditText editTen,editSDT,editDiachi;
    TextView textTendangnhap;
    ImageButton imgLuu;
    Button doimatkhau;
    Toolbar toolbar;
    FragmentDangNhap fragmentDangNhap;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);
        EventBus.getDefault().register(this);
        fragmentDangNhap = new FragmentDangNhap();
        anhxa();
        init();
        getInfor();
        action();
        envent();
    }
    public void anhxa(){
        editTen = (EditText) findViewById(R.id.tenTV);
        editSDT = (EditText) findViewById(R.id.textSDT);
        editDiachi = (EditText) findViewById(R.id.textDiachi);
        textTendangnhap = (TextView) findViewById(R.id.tenDN);
        imgLuu = (ImageButton) findViewById(R.id.imgLuu);
        doimatkhau = (Button) findViewById(R.id.Doimatkhau);
        toolbar = (Toolbar) findViewById(R.id.toolacc);
    }
    public void envent(){
        imgLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int matv = sharedPreferences.getInt("matv",0);
                String ten = editTen.getText().toString();
                String dc = editDiachi.getText().toString();
                String sdt = editSDT.getText().toString();
                String query = " UPDATE thanhvien SET tentv = '"+ten+"', diachi = '"+dc+"', sdt = '" +sdt+"' WHERE matv = '"+matv+"' ";
                Update(query);
                if (!fragmentDangNhap.LayCached(getApplicationContext()).equals("")) {
                    fragmentDangNhap.updateCaced2(getApplicationContext(), ten,dc,sdt);
                }
                EventBus.getDefault().post(true, "loginSuccess");
                finish();
            }
        });
    }
    public void init(){
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
    }
    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b){
        getInfor();
        envent();
    }
    public void getInfor(){
        String hoten = sharedPreferences.getString("tentv","");
        String tendangnhap = sharedPreferences.getString("tendangnhap","");
        String diachi = sharedPreferences.getString("diachi","");
        String sdt = sharedPreferences.getString("sdt","");

        editTen.setText(hoten);
        textTendangnhap.setText(tendangnhap);
        editSDT.setText("0"+sdt);
        editDiachi.setText(diachi);
    }
    public void action(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void Update(final String query) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.updateDonHang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            Toast.makeText(getApplication(), "Hồ sơ đã được lưu", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplication(), "Update Error", Toast.LENGTH_LONG).show();
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
