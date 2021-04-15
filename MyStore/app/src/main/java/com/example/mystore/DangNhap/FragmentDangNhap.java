package com.example.mystore.DangNhap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Fragment.AccountFragment;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


public class FragmentDangNhap extends Fragment {
    EditText username, password;
    Button btnDangnhap;
    static CheckBox checkBox;
    public static ProgressBar loading;
    private static String taikhoan;
    private static String matkhau;
    TextView txtforget;
    AccountFragment accountFragment;
    public SharedPreferences luutaikhoan;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_fragment_dang_nhap, container, false);
        EventBus.getDefault().register(this);
        username = (EditText) view.findViewById(R.id.edituser);
        password = (EditText) view.findViewById(R.id.editpass);
        btnDangnhap = (Button) view.findViewById(R.id.btnDangNhap);
        checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        txtforget = (TextView) view.findViewById(R.id.txtforget);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        loading.setVisibility(View.INVISIBLE);
        luutaikhoan = getActivity().getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        accountFragment = new AccountFragment();
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = username.getText().toString().trim();
                matkhau = password.getText().toString().trim();
                if (!taikhoan.isEmpty() || !matkhau.isEmpty()) {
                    Login();
                } else {
                    username.setError("Vui lòng nhập tài khoản");
                    password.setError("Vui lòng nhập mật khẩu");
                }
            }
        });
        return view;
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
                                    String hoten = object.getString("tentv");
                                    String tendangnhap = object.getString("tendangnhap");
                                    String diachi = object.getString("diachi");
                                    String sdt = object.getString("sdt");
                                    int maloai = object.getInt("maloaitk");
                                    Toast.makeText(getContext(), "Bạn đăng nhập với tài khoản:\n" + hoten,
                                            Toast.LENGTH_SHORT).show();
                                    if (checkBox.isChecked()) {
                                        updateCaced(getContext(), tendangnhap, diachi);
                                        updateCaced1(getContext(), id, maloai);
                                        updateCaced2(getContext(),hoten,diachi,sdt);
                                    }
                                }
                                EventBus.getDefault().post(true, "loginSuccess");
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

    public String LayCached(Context context) {
        SharedPreferences cacheddanganhp = context.getSharedPreferences("datalogin", context.MODE_PRIVATE);
        String hoten = cacheddanganhp.getString("tentv", "");
        return hoten;
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