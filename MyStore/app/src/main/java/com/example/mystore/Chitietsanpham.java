package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mystore.Model.GioHang;
import com.example.mystore.Model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Chitietsanpham extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgView;
    TextView tvTen, tvGia, tvMota;
    Spinner spinner;
    Button btnThem;
     int id = 0;
     String tensanpham = "";
     int gia = 0 ;
     String hinhanh = "";
     int soluong = 0;
     String mota = "";
    int idsanpham = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        init();
        ActionTool();
        GetInfor();
        EventSpiner();
        EventButtonGH();
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        anhxa();
    }

    private void EventSpiner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }
    public void GetInfor(){
        Intent intent = (Intent) getIntent();
        Sanpham sanpham = (Sanpham) intent.getSerializableExtra("thongtinchitiet");
        id = sanpham.getId();
        tensanpham = sanpham.getTensanpham();
        gia = sanpham.getGia();
        hinhanh = sanpham.getHinhanh();
        soluong =sanpham.getSoluong();
        mota = sanpham.getMota();
        idsanpham = sanpham.getIdsanpham();

        tvTen.setText(tensanpham);
        tvTen.setMaxLines(2);
        tvTen.setEllipsize(TextUtils.TruncateAt.END);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGia.setText("" + decimalFormat.format(gia)+" Đ");
        tvMota.setText(mota);
        Picasso.get().load(hinhanh).placeholder(R.drawable.noimage).error(R.drawable.error).into(imgView);
    }
    private void ActionTool() {
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

    }
    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgView = (ImageView) findViewById(R.id.imgv);
        tvTen = (TextView) findViewById(R.id.tvTensp);
        tvGia = (TextView) findViewById(R.id.tvGia);
        tvMota = (TextView) findViewById(R.id.tvMota);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnThem = (Button) findViewById(R.id.btnThem);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButtonGH() {
        final Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinchitiet");
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangiohang.size() > 0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exite = false;
                    for(int i = 0; i< MainActivity.mangiohang.size();i++){
                        if(MainActivity.mangiohang.get(i).getIdsp()== id){
                            MainActivity.mangiohang.get(i).setSoluongsp(MainActivity.mangiohang.get(i).getSoluongsp()+sl);
                            if(MainActivity.mangiohang.get(i).getSoluongsp() >= sanpham.soluong){
                                MainActivity.mangiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangiohang.get(i).setGia(gia* MainActivity.mangiohang.get(i).getSoluongsp());
                            exite = true;
                        }
                    }
                    if(exite == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * gia;
                        MainActivity.mangiohang.add(new GioHang(id,tensanpham,Giamoi,hinhanh,soluong));
                    }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * gia;
                    MainActivity.mangiohang.add(new GioHang(id,tensanpham,Giamoi,hinhanh,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
            }
        });
    }
    public void anhxa(){
        String hoten = sharedPreferences.getString("tentv", "");
        int maloaitk = sharedPreferences.getInt("maloaitk",0);
        if (!TextUtils.isEmpty(hoten)) {
           btnThem.setVisibility(View.VISIBLE);
           spinner.setVisibility(View.VISIBLE);
            if(maloaitk == 1){
                btnThem.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
        } else {
                btnThem.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
            }
        }
    }
}
