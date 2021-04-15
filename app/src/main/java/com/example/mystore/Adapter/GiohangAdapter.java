package com.example.mystore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mystore.Giohang;
import com.example.mystore.MainActivity;
import com.example.mystore.Model.GioHang;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayList;
    ViewHoler viewHoler = null;

    public GiohangAdapter(Context context, ArrayList<GioHang> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txtGiohang, txtGia, btnvalus;
        public ImageView imgView;
        public Button btnTru, btnCong;

    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang, null);
            viewHoler.txtGiohang = (TextView) convertView.findViewById(R.id.tvGiohang);
            viewHoler.txtGia = (TextView) convertView.findViewById(R.id.tvGia);
            viewHoler.imgView = (ImageView) convertView.findViewById(R.id.imgvGiohang);
            viewHoler.btnTru = (Button) convertView.findViewById(R.id.btnTru);
            viewHoler.btnvalus = (TextView) convertView.findViewById(R.id.btnvalues);
            viewHoler.btnCong = (Button) convertView.findViewById(R.id.btnCong);
            convertView.setTag(viewHoler);

        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        final GioHang gioHang = (GioHang) getItem(i);
        viewHoler.txtGiohang.setText(gioHang.getTensp());
        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        viewHoler.txtGia.setText((decimalFormat.format(gioHang.getGia())) + " Đ");
        Picasso.get().load(gioHang.getHinhanhsp()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHoler.imgView);
        viewHoler.btnvalus.setText(gioHang.getSoluongsp() + "");
        final int sl = Integer.parseInt(viewHoler.btnvalus.getText().toString());
        if (sl >= 10) {
            viewHoler.btnCong.setVisibility(convertView.INVISIBLE);
            viewHoler.btnTru.setVisibility(convertView.VISIBLE);
        } else if (sl <= 1) {
            viewHoler.btnTru.setVisibility(convertView.INVISIBLE);
        } else if (sl >= 1) {
            viewHoler.btnTru.setVisibility(convertView.VISIBLE);
            viewHoler.btnCong.setVisibility(convertView.VISIBLE);
        }
        final ViewHoler finalviewHoler = viewHoler;
        viewHoler.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalviewHoler.btnvalus.getText().toString()) - 1;
                int slht = MainActivity.mangiohang.get(i).getSoluongsp();
                long giaht = MainActivity.mangiohang.get(i).getGia();
                MainActivity.mangiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.mangiohang.get(i).setGia(giamoinhat);
                long md = giamoinhat*slht/slmoinhat;
                DecimalFormat format = new DecimalFormat("###,###,###");
                finalviewHoler.txtGia.setText(format.format(gioHang.getGia()));
                Giohang.EventUtil();
                if (slmoinhat < 2) {
                    finalviewHoler.btnCong.setVisibility(View.VISIBLE);
                    finalviewHoler.btnTru.setVisibility(View.INVISIBLE);
                    finalviewHoler.btnvalus.setText(String.valueOf(slmoinhat));
                } else {
                    finalviewHoler.btnCong.setVisibility(View.VISIBLE);
                    finalviewHoler.btnTru.setVisibility(View.VISIBLE);
                    finalviewHoler.btnvalus.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHoler.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalviewHoler.btnvalus.getText().toString()) + 1;
                int slht = MainActivity.mangiohang.get(i).getSoluongsp();
                long giaht = MainActivity.mangiohang.get(i).getGia();
                MainActivity.mangiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.mangiohang.get(i).setGia(giamoinhat);
                DecimalFormat format = new DecimalFormat("###,###,###");
                long md = giamoinhat*slht/slmoinhat;
                finalviewHoler.txtGia.setText(format.format(md));
                Giohang.EventUtil();
                if (slmoinhat > 9) {
                    finalviewHoler.btnCong.setVisibility(View.INVISIBLE);
                    finalviewHoler.btnTru.setVisibility(View.VISIBLE);
                    finalviewHoler.btnvalus.setText(String.valueOf(slmoinhat));
                } else {
                    finalviewHoler.btnCong.setVisibility(View.VISIBLE);
                    finalviewHoler.btnTru.setVisibility(View.VISIBLE);
                    finalviewHoler.btnvalus.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }

}
