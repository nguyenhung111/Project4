package com.example.mystore.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mystore.DanhMuc;
import com.example.mystore.R;

public class LatopFragment extends Fragment {
    ImageButton asus,msi,lenovo,dell,hp,acer;
    Button gia1,gia2,gia3,gia4,gia5,gia6;
    Toolbar toolbar;
    public LatopFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.latop_fragment, container, false);

        asus = (ImageButton) view.findViewById(R.id.asus);
        msi = (ImageButton) view.findViewById(R.id.msi);
        lenovo = (ImageButton) view.findViewById(R.id.lenovo);
        dell = (ImageButton) view.findViewById(R.id.dell);
        hp = (ImageButton) view.findViewById(R.id.hp);
        acer = (ImageButton) view.findViewById(R.id.acer);

        gia1 = (Button) view.findViewById(R.id.gia1);
        gia2 = (Button) view.findViewById(R.id.gia2);
        gia3 = (Button) view.findViewById(R.id.gia3);
        gia4 = (Button) view.findViewById(R.id.gia4);
        gia5 = (Button) view.findViewById(R.id.gia5);
        gia6 = (Button) view.findViewById(R.id.gia6);

        toolbar = (Toolbar) view.findViewById(R.id.toolbardanhmuc);

        Envent();

        return view;
    }
    public void Envent(){
        asus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","asus");
                startActivity(intent);
            }
        });
        acer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","acer");
                startActivity(intent);
            }
        });
        hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","hp");
                startActivity(intent);
            }
        });
        dell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","dell");
                startActivity(intent);
            }
        });
        lenovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","lenovo");
                startActivity(intent);
            }
        });
        msi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","msi");
                startActivity(intent);
            }
        });
        gia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","gia1");
                startActivity(intent);
            }
        });
        gia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","gia2");
                startActivity(intent);
            }
        });
        gia3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","gia3");
                startActivity(intent);
            }
        });
        gia4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","gia4");
                startActivity(intent);
            }
        });
        gia5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","gia5");
                startActivity(intent);
            }
        });
        gia6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DanhMuc.class);
                intent.putExtra("danhmuc","gia6");
                startActivity(intent);
            }
        });
    }
}
