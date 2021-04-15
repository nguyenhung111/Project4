package com.example.mystore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystore.Adapter.GiohangAdapter;
import com.example.mystore.DangNhap.FragmentDangNhap;
import com.example.mystore.DangNhap.Login;
import com.example.mystore.Fragment.HomeFragment;
import com.example.mystore.Model.CheckConnection;
import com.example.mystore.Model.Dathang;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.DecimalFormat;

public class Giohang extends AppCompatActivity {
    ListView lvgiohang;
    TextView txtthongbao;
    TextView txtAdd;
    public static TextView txttongtien;
    Button btnthanhtoan, btntieptuc;
    Toolbar toolbar;
    GiohangAdapter giohangAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FragmentDangNhap fragmentDangNhap;
    String hoten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        EventBus.getDefault().register(this);
        init();
        ActionToolbar();
        CheckData();
        EventUtil();
        Event();
        CatchonItemLisstView();
        EnvenCountine();
        initShare();
        EventThanhtoan();
        fragmentDangNhap = new FragmentDangNhap();
    }

    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b){
        EventThanhtoan();
    }
    public void EventThanhtoan() {
        hoten = sharedPreferences.getString("tentv", "");
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mangiohang.size() >0 ){
                    if(!TextUtils.isEmpty(hoten)){
                        startActivity(new Intent(getApplicationContext(), Dathang.class));
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        Toast.makeText(Giohang.this, "Bạn chưa đăng nhập vui lòng đăng nhập để mua sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    CheckConnection.ShowToast(getApplicationContext(),"Giỏ hàng chưa có sản phẩm");
                }
            }
        });
    }

    public void initShare() {
        sharedPreferences =getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void CatchonItemLisstView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.mangiohang.size() <= 0) {
                            txtthongbao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.mangiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUtil();
                            if (MainActivity.mangiohang.size() <= 0) {
                                txtthongbao.setVisibility(View.VISIBLE);
                                txtAdd.setVisibility(View.VISIBLE);
                            } else {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public void EnvenCountine() {
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void EventUtil() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.mangiohang.size(); i++) {
            tongtien += MainActivity.mangiohang.get(i).getGia();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + " Đ");
    }
    private void CheckData() {
        if (MainActivity.mangiohang.size() <= 0) {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            txtAdd.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        } else {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            txtAdd.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
    public void init() {
        lvgiohang = findViewById(R.id.listviewGH);
        txtthongbao = (TextView) findViewById(R.id.textThongbao);
        txttongtien = (TextView) findViewById(R.id.txttongtien);
        btnthanhtoan = (Button) findViewById(R.id.btnDathang);
        btntieptuc = (Button) findViewById(R.id.btnAdd);
        toolbar = (Toolbar) findViewById(R.id.toolbarGH);
        txtAdd = (TextView) findViewById(R.id.textAdd);

        giohangAdapter = new GiohangAdapter(this, MainActivity.mangiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }

    public void Event() {
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
