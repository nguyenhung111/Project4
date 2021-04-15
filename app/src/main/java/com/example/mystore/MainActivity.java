package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.mystore.Adapter.SectionsPagerAdapter;
import com.example.mystore.Model.GioHang;
import com.example.mystore.TimKiem.TimKiem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AHBottomNavigation bottomNavigation;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewpager;
    public static ArrayList<GioHang> mangiohang;
    ImageButton btnGiohang,btnSearch;
    TextView edt;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        btnGiohang = (ImageButton) findViewById(R.id.btnGioHang);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(sectionsPagerAdapter);
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        createAhBottom();
        Event();
        if(mangiohang != null){

        }else {
            mangiohang = new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGH:
                Intent intent = new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    private void createAhBottom() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Danh Mục", R.drawable.ic_list_black_24dp);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Account", R.drawable.ic_account_box_black_24dp);
        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        // Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorWhite));

        //mau khi chon tab
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        //mau khi chua chon tab
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorBlack));

        // layout mặc định
        bottomNavigation.setCurrentItem(0);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                viewpager.setCurrentItem(position);
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }

    public void Event(){
        btnGiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Giohang.class);
                startActivity(intent);
            }
        });
        edt = (TextView) findViewById(R.id.edt_seach);
        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TimKiem.class);
                startActivity(intent);
            }
        });
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TimKiem.class);
                startActivity(intent);
            }
        });
    }
    }
