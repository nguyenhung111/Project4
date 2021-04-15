package com.example.mystore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.Model.ThongKe;
import com.example.mystore.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterThongKe extends RecyclerView.Adapter<AdapterThongKe.ItemHoler> {
    Context context;
    ArrayList<ThongKe> arrayList;

    public AdapterThongKe(Context context, ArrayList<ThongKe> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongthongke, null);
        ItemHoler itemHolder = new ItemHoler(v);
        return itemHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterThongKe.ItemHoler holder, int position) {
        ThongKe thongKe = arrayList.get(position);
        holder.txtthang.setText("Tháng: " + thongKe.getThang());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txttien.setText("Tổng doanh thu của tháng: "+ decimalFormat.format(thongKe.getTien()) + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        public TextView txtthang, txttien;


        public ItemHoler(View itemView) {
            super(itemView);
            txtthang = (TextView) itemView.findViewById(R.id.txtthang);
            txttien = (TextView) itemView.findViewById(R.id.txttien);


        }
    }
}
