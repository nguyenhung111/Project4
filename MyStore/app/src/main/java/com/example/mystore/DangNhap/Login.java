package com.example.mystore.DangNhap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.mystore.Adapter.ViewpaperAdapterDangNhap;
import com.example.mystore.R;
import com.google.android.material.tabs.TabLayout;

public class Login extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.tabDangNhap);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabDN);
        viewPager = (ViewPager) findViewById(R.id.viewPaper);

        ViewpaperAdapterDangNhap viewpaperAdapterDangNhap = new ViewpaperAdapterDangNhap(getSupportFragmentManager());

        viewPager.setAdapter(viewpaperAdapterDangNhap);
        viewpaperAdapterDangNhap.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }
}
