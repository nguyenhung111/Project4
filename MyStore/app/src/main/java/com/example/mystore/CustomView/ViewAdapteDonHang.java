package com.example.mystore.CustomView;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mystore.Admin.Choxacnhan;
import com.example.mystore.Admin.Dagiao;
import com.example.mystore.Admin.Danggiao;
import com.example.mystore.Admin.Xacnhan;

public class ViewAdapteDonHang extends FragmentPagerAdapter {
    public ViewAdapteDonHang(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                    Choxacnhan choxacnhan = new Choxacnhan();
                    return choxacnhan;
            case 1:
                    Xacnhan xacnhan = new Xacnhan();
                    return xacnhan;
            case 2:
                Danggiao danggiao = new Danggiao();
                return danggiao;
            case 3:
                Dagiao dagiao = new Dagiao();
                return dagiao;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chờ xác nhận";
            case 1:
                return "Xác nhận";
            case 2:
                return "Đang giao";
            case 3:
                return "Đã giao";
        }
        return null;
    }
}
