package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class Themdiachi extends AppCompatActivity {
    private EditText edthoten, edtsdt, edtdiachi;
    private Button btnthem;
    private Toolbar toolbar;
    private ProgressBar loading;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themdiachi);
        edthoten = findViewById(R.id.edthoten);
        edtsdt = findViewById(R.id.edtsdt);
        edtdiachi = findViewById(R.id.edtdiachi);
        btnthem = findViewById(R.id.btnthem);
        toolbar = findViewById(R.id.toolbarthemdiachi);
        loading = findViewById(R.id.loading);

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Themdiachi();
            }
        });
        EventToobar();
        initShare();
    }
    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void Themdiachi() {

         int matv = sharedPreferences.getInt("matv",0);
        if (edthoten.length() < 6) {
            Toast.makeText(this, "Bạn cần nhập đầy đủ họ tên", Toast.LENGTH_SHORT).show();
        } else if (edtsdt.length() > 10 || edtsdt.length() <= 9) {
            Toast.makeText(this, "Bạn chưa nhập hoặc nhập sai sdt", Toast.LENGTH_SHORT).show();
        } else if (edtdiachi.length() < 20) {
            Toast.makeText(this, "Địa chỉ cần ghi đầy đủ thôn xã", Toast.LENGTH_SHORT).show();
        } else {
            final String hoten = this.edthoten.getText().toString();
            final String sdt = this.edtsdt.getText().toString().trim();
            final String diachi = this.edtdiachi.getText().toString();
            String postUrl = Server.Postdiachi;
            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());

            JSONObject postData = new JSONObject();
            try {
                postData.put("Matv", String.valueOf(matv));
                postData.put("Hoten",hoten);
                postData.put("Sdt", sdt);
                postData.put("Diachi", diachi);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if(response.length()>0){
                                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }

    private void EventToobar() {
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
