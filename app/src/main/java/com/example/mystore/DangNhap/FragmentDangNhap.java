package com.example.mystore.DangNhap;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Fragment.AccountFragment;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, Server.GetLogin + taikhoan + "/" + matkhau,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (response.length() > 0) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int id = jsonObject.getInt("matv");
                                    String hoten = jsonObject.getString("tentv");
                                    String tendangnhap = jsonObject.getString("tendangnhap");
                                    String diachi = jsonObject.getString("diachi");
                                    String sdt = jsonObject.getString("sdt");
                                    int maloai = jsonObject.getInt("maloaitk");
                                    Toast.makeText(getContext(), "Bạn đăng nhập với tài khoản:\n" + hoten,
                                            Toast.LENGTH_SHORT).show();
                                    if (checkBox.isChecked()) {
                                        updateCaced(getContext(), tendangnhap, matkhau, diachi, id, maloai, hoten, sdt);
                                    }
                                    EventBus.getDefault().post(true);
                                    getActivity().finish();
                                } else {
                                    loading.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getContext(), "Đăng nhập thất bại",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Erorr", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("tentaikhoan", taikhoan);
                MyData.put("matkhau", matkhau);
                return MyData;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        MyRequestQueue.add(MyStringRequest);
    }


    public String LayCached(Context context) {
        SharedPreferences cacheddanganhp = context.getSharedPreferences("datalogin", context.MODE_PRIVATE);
        String hoten = cacheddanganhp.getString("tentv", "");
        return hoten;
    }

    public void updateCaced(Context context, String tendangnhap, String matkhau, String diachi, int matv, int maloaitk, String hoten, String sdt) {
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
