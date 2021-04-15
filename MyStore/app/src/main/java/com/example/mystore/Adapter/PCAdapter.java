package com.example.mystore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.Chitietsanpham;
import com.example.mystore.Model.CheckConnection;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PCAdapter extends RecyclerView.Adapter<PCAdapter.ItemHoler> {
    Context context;
    ArrayList<Sanpham> arrayList;

    public PCAdapter(Context context, ArrayList<Sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PCAdapter.ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_san_pham_moi, null);
        PCAdapter.ItemHoler itemHoler = new PCAdapter.ItemHoler(v);
        return itemHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHoler holder, int position) {
        final Sanpham sanpham = arrayList.get(position);
        holder.tensp.setMaxLines(2);
        holder.tensp.setEllipsize(TextUtils.TruncateAt.END);
        holder.tensp.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giasp.setText("Giá : " + decimalFormat.format(sanpham.getGia()) + "Đ");
        Picasso.get().load(sanpham.getHinhanh()).into(holder.imghinhanhsp);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        public ImageView imghinhanhsp;
        public TextView tensp, giasp;
        public Button btnChinhSua,btnXoa;

        public ItemHoler(View view) {
            super(view);
            imghinhanhsp = (ImageView) view.findViewById(R.id.imgsanpham);
            tensp = (TextView) view.findViewById(R.id.txttensp);
            giasp = (TextView) view.findViewById(R.id.txtgiasp);
            btnChinhSua = (Button) view.findViewById(R.id.btnTru);
            btnXoa = (Button) view.findViewById(R.id.btnCong);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Chitietsanpham.class);
                    intent.putExtra("thongtinchitiet", arrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context, arrayList.get(getPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });
        }
    }
}
