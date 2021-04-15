package com.example.mystore.DangNhap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDangKy extends Fragment {
    EditText edtuser, edtpass, edthoten, edtpass2;
    Button btnDangKy;
    ProgressBar loading;
    FragmentDangNhap fragmentDangNhap;
    String taikhoan, matkhau, hoten;

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
                PostRegister();
            }
        });
        return view;
    }

    public void PostRegister() {
        loading.setVisibility(View.VISIBLE);
        if (edthoten.length() < 6) {
            Toast.makeText(getContext(), "Bạn cần nhập đầy đủ họ tên", Toast.LENGTH_SHORT).show();
        } else if (edtuser.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Nhập lại email", Toast.LENGTH_SHORT).show();
        } else if (edtpass.length() < 6) {
            Toast.makeText(getContext(), "Mật khẩu phải có từ 6 ký tự trở lên", Toast.LENGTH_SHORT).show();
        } else if (edtpass2.length() < 6) {
            Toast.makeText(getContext(), "Mật khẩu phải trùng mật khâu ", Toast.LENGTH_SHORT).show();
        } else {
            String postUrl = Server.PostResgint;
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            JSONObject postData = new JSONObject();
            try {
                postData.put("tentv", edthoten.getText().toString());
                postData.put("tendangnhap", edtuser.getText().toString());
                postData.put("matkhau", edtpass.getText().toString());
                postData.put("diachi", "");
                postData.put("sdt", "");
                postData.put("maloaitk", 2);


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (response.length() > 0) {
                                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    Login();
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    //
    public void Login() {
        loading.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET,
                Server.GetLogin + edtuser.getText().toString() + "/" + edtpass.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int id = jsonObject.getInt("matv");
                                String hoten = jsonObject.getString("tentv");
                                String tendangnhap = jsonObject.getString("tendangnhap");
                                String diachi = jsonObject.getString("diachi");
                                String sdt = jsonObject.getString("sdt");
                                int maloai = jsonObject.getInt("maloaitk");
                                Toast.makeText(getContext(), "Bạn đăng nhập với tài khoản:\n" + hoten, Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                updateCaced(getContext(), tendangnhap, diachi);
                                updateCaced1(getContext(), id, maloai);
                                updateCaced2(getContext(), hoten, diachi, sdt);
                            }
                            EventBus.getDefault().post(true);
                            getActivity().finish();
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
                params.put("tentaikhoan", edtuser.getText().toString());
                params.put("matkhau", edtpass.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    public void updateCaced(Context context, String tendangnhap, String diachi) {
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

    public void updateCaced2(Context context, String hoten, String diachi, String sdt) {
        SharedPreferences cachedangnhap = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cachedangnhap.edit();
        editor.putString("tentv", hoten);
        editor.putString("diachi", diachi);
        editor.putString("sdt", sdt);
        editor.putBoolean("checked", true);
        editor.commit();
    }
}
