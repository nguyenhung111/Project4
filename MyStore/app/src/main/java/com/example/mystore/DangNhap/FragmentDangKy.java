package com.example.mystore.DangNhap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class FragmentDangKy extends Fragment {
    EditText edtuser, edtpass, edthoten, edtpass2;
    Button btnDangKy;
    ProgressBar loading;
    FragmentDangNhap fragmentDangNhap;
    String taikhoan, matkhau;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_dang_ky, container, false);

        edthoten = (EditText) view.findViewById(R.id.editHoten);
        edtuser = (EditText) view.findViewById(R.id.editemailDK);
        edtpass = (EditText) view.findViewById(R.id.editpassDK);
        edtpass2 = (EditText) view.findViewById(R.id.editpass);
        btnDangKy = (Button) view.findViewById(R.id.btnDangKy);
        fragmentDangNhap = new FragmentDangNhap();
        loading = view.findViewById(R.id.loading);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        return view;
    }

    public void Register() {
        loading.setVisibility(View.VISIBLE);
        if (edthoten.length() < 6) {
            Toast.makeText(getContext(), "Bạn cần nhập đầy đủ họ tên", Toast.LENGTH_SHORT).show();
        } else if (edtuser.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Nhập lại email", Toast.LENGTH_SHORT).show();
        } else if (edtpass.length() < 6) {
            Toast.makeText(getContext(), "Mật khẩu phải có từ 6 ký tự trở lên", Toast.LENGTH_SHORT).show();
        }
        else if (edtpass2.length() < 6){
            Toast.makeText(getContext(), "Mật khẩu phải trùng mật khâu ", Toast.LENGTH_SHORT).show();
        }

        else {
            final String hoten = this.edthoten.getText().toString();
            taikhoan = this.edtuser.getText().toString().trim();
            matkhau = this.edtpass.getText().toString().trim();
            final String matkhau2 = this.edtpass2.getText().toString().trim();
            int result1;
            result1 = matkhau.compareTo(matkhau2);
            if (result1 < 0) {
                Toast.makeText(getContext(), "Mật khẩu không giống nhau ", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest request = new StringRequest(Request.Method.POST, Server.GetResgint,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.contains("register")) {
                                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    Login();
                                    getActivity().finish();
                                } else if (response.trim().contains("tontaitentk")) {
                                    loading.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Tên đănp nhập đã được sử dụng!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loading.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tentv", hoten);
                        params.put("tentaikhoan", taikhoan);
                        params.put("matkhau", matkhau);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(request);
            }
        }
    }

    public void Login() {
        loading.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, Server.GetLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.contains("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int id = object.getInt("matv");
                                    String hoten = object.getString("tentv").trim();
                                    String tendangnhap = object.getString("tendangnhap");
                                    String diachi = object.getString("diachi");
                                    String sdt = object.getString("sdt");
                                    int maloai = object.getInt("maloaitk");
                                    Toast.makeText(getContext(), "Bạn đăng nhập với tài khoản:\n" + hoten ,
                                            Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    updateCaced(getContext(), tendangnhap, diachi);
                                    updateCaced1(getContext(), id, maloai);
                                    updateCaced2(getContext(),hoten,diachi,sdt);
                                }
                                EventBus.getDefault().post(true,"loginSuccess");
                                getActivity().finish();
                            } else {
                                loading.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Erorr", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tentaikhoan", taikhoan);
                params.put("matkhau", matkhau);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    public void updateCaced(Context context,  String tendangnhap, String diachi) {
        SharedPreferences cachedangnhap = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cachedangnhap.edit();
        editor.putString("tendangnhap", tendangnhap);
        editor.putString("diachi", diachi);
        editor.putBoolean("checked", true);
        editor.commit();
    }

    public void updateCaced1(Context context, int matv, int maloaitk) {
        SharedPreferences cachedangnhap = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cachedangnhap.edit();
        editor.putInt("matv", matv);
        editor.putInt("maloaitk", maloaitk);
        editor.putBoolean("checked", true);
        editor.commit();
    }
    public void updateCaced2(Context context, String hoten, String diachi,  String sdt) {
        SharedPreferences cachedangnhap = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cachedangnhap.edit();
        editor.putString("tentv", hoten);
        editor.putString("diachi", diachi);
        editor.putString("sdt", sdt);
        editor.putBoolean("checked", true);
        editor.commit();
    }
}
