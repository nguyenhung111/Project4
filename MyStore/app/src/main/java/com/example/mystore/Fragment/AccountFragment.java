package com.example.mystore.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mystore.Admin.Donhang;
import com.example.mystore.Admin.QuanLyDonHang;
import com.example.mystore.Admin.Thongke;
import com.example.mystore.DangNhap.FragmentDangNhap;
import com.example.mystore.DangNhap.Login;
import com.example.mystore.QuanLySanPham;
import com.example.mystore.R;
import com.example.mystore.TimKiem.TaiKhoan;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;


public class AccountFragment extends Fragment {
    public static TextView tentv;
    TextView setting, login, quanlysp,thongke,qldh;
    FragmentDangNhap fragmentDangNhap;
    LinearLayout login1, logout1, quanly,LLquanlydh,bieudo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageButton choxacnhan,xacnhan,danggiao,dagiao;
    public AccountFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.account_fragment, container, false);
        EventBus.getDefault().register(this);
        tentv = (TextView) view.findViewById(R.id.tvKH);
        fragmentDangNhap = new FragmentDangNhap();
        login1 = (LinearLayout) view.findViewById(R.id.login1);
        logout1 = (LinearLayout) view.findViewById(R.id.logout1);
        setting = (TextView) view.findViewById(R.id.setting);
        login = (TextView) view.findViewById(R.id.login);
        quanlysp= (TextView) view.findViewById(R.id.quanlysp);
        quanly = (LinearLayout) view.findViewById(R.id.quanly);
        LLquanlydh = (LinearLayout) view.findViewById(R.id.dongdonhang);
        bieudo = (LinearLayout) view.findViewById(R.id.bieudo);
        thongke= (TextView) view.findViewById(R.id.thongke);
        choxacnhan = (ImageButton) view.findViewById(R.id.choxacnhan);
        xacnhan = (ImageButton) view.findViewById(R.id.xacnhan);
        danggiao = (ImageButton) view.findViewById(R.id.danggiao);
        dagiao = (ImageButton) view.findViewById(R.id.dagiao);
        qldh = view.findViewById(R.id.quanlydhh);
        initShare();
        checkdata();
        quanlysp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanLySanPham.class));
            }
        });
        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fragmentDangNhap.LayCached(getContext()).equals("")) {
                    fragmentDangNhap.updateCaced(getContext(), "","");
                    fragmentDangNhap.updateCaced1(getContext(), 0,0);
                    fragmentDangNhap.updateCaced2(getContext(),"","","");
                    tentv.setText("Tên khách hàng");
                    Toast.makeText(getContext(), " Bạn đã đăng xuất tài khoản", Toast.LENGTH_LONG).show();
                }
                if (!tentv.equals("")) {
                    login1.setVisibility(View.VISIBLE);
                    logout1.setVisibility(View.GONE);
                    LLquanlydh.setVisibility(View.GONE);
                    quanly.setVisibility(View.GONE);
                    bieudo.setVisibility(View.GONE);
                }
            }
        });
        thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Thongke.class));
            }
        });
        Envent();

        return view;

    }

    public void Envent() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getContext(), TaiKhoan.class));
            }
        });
        choxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Donhang.class));
            }
        });
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Donhang.class));
            }
        });
        danggiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Donhang.class));
            }
        });
        dagiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Donhang.class));
            }
        });
    }

    public void initShare() {
        sharedPreferences = getContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b){
        checkdata();
    }

    public void checkdata() {
        String hoten = sharedPreferences.getString("tentv", "");
        int maloaitk = sharedPreferences.getInt("maloaitk",0);
        if (!TextUtils.isEmpty(hoten)) {
            login1.setVisibility(View.GONE);
            logout1.setVisibility(View.VISIBLE);
            LLquanlydh.setVisibility(View.VISIBLE);
            tentv.setText(sharedPreferences.getString("tentv", ""));
            if(maloaitk == 1){
                quanly.setVisibility(View.VISIBLE);
                bieudo.setVisibility(View.VISIBLE);
            }else {
                bieudo.setVisibility(View.GONE);
                quanly.setVisibility(View.GONE);
            }
            qldh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), Donhang.class));
                }
            });

        } else {
            bieudo.setVisibility(View.GONE);
            login1.setVisibility(View.VISIBLE);
            logout1.setVisibility(View.GONE);
            LLquanlydh.setVisibility(View.GONE);
            quanly.setVisibility(View.GONE);
            qldh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Bạn phải đăng nhập để vào đơn hàng", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
