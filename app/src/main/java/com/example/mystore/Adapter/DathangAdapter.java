package com.example.mystore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mystore.Model.GioHang;
import com.example.mystore.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DathangAdapter extends BaseAdapter {
    private Context context;
    ArrayList<GioHang> listdathang;

    public DathangAdapter(Context context, ArrayList<GioHang> listdathang) {
        this.context = context;
        this.listdathang = listdathang;
    }

    @Override
    public int getCount() {
        return listdathang.size();
    }

    @Override
    public Object getItem(int position) {
        return listdathang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView tvtensp, tvgiatien, tvsoluong;
        public ImageView imgsanpham;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (view == null){ //nếu chưa có chưa có dữ liệu
            viewHolder = new ViewHolder();
            //gán layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dathang, null);
            viewHolder.tvtensp = view.findViewById(R.id.tendathang);
            viewHolder.tvgiatien = view.findViewById(R.id.giatien);
            viewHolder.imgsanpham = view.findViewById(R.id.imgdathang);
            viewHolder.tvsoluong = view.findViewById(R.id.soluong);
            view.setTag(viewHolder);
        }else {//nếu có dữ liệu chỉ cần gán lại
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang giohang = (GioHang) getItem(position);
        viewHolder.tvtensp.setText(giohang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.tvgiatien.setText(decimalFormat.format(giohang.getGia())+" đ");
        Picasso.get().load(giohang.getHinhanhsp()).into(viewHolder.imgsanpham);
        viewHolder.tvsoluong.setText(giohang.getSoluongsp()+"");

        return  view;
    }
}
