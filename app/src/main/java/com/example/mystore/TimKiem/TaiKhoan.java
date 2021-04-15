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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.DangNhap.FragmentDangNhap;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class TaiKhoan extends AppCompatActivity {
    EditText editTen,editSDT,editDiachi,edtmatkhau;
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
        edtmatkhau = (EditText) findViewById(R.id.matkhau);
    }
    public void envent(){
        imgLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
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
        String matkhau = sharedPreferences.getString("matkhau","");
        String diachi = sharedPreferences.getString("diachi","");
        String sdt = sharedPreferences.getString("sdt","");

        editTen.setText(hoten);
        textTendangnhap.setText(tendangnhap);
        edtmatkhau.setText(matkhau);
        editSDT.setText(sdt);
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
    public void Update() {
        final int matv = sharedPreferences.getInt("matv",0);
        final String ten = editTen.getText().toString();
        final String mk = edtmatkhau.getText().toString();
        final String dc = editDiachi.getText().toString();
        final String sdt = editSDT.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        JSONObject postData = new JSONObject();

        try {
            postData.put("matv", matv);
            postData.put("tentv", ten);
            postData.put("tendangnhap", textTendangnhap.getText().toString());
            postData.put("matkhau", mk);
            postData.put("diachi", dc);
            postData.put("sdt", sdt);
            postData.put("maloaitk", 2);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT , Server.UpdateTK+matv, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.length()>0){
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            updateCaced(getApplicationContext(), textTendangnhap.getText().toString(),mk, dc,matv,2,ten,sdt);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    public void updateCaced(Context context, String tendangnhap,String matkhau, String diachi, int matv, int maloaitk, String hoten, String sdt) {
        SharedPreferences cachedangnhap = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cachedangnhap.edit();
        editor.putString("tendangnhap", tendangnhap);
        editor.putString("matkhau", matkhau);
        editor.putString("diachi", diachi);
        editor.putInt("matv", matv);
        editor.putInt("maloaitk", maloaitk);
        editor.putString("tentv", hoten);
        editor.putString("diachi", diachi);
        editor.putString("sdt", sdt);
        editor.putBoolean("checked", true);
        editor.commit();
    }
}
