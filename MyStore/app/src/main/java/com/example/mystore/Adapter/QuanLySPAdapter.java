package com.example.mystore.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Chitietsanpham;
import com.example.mystore.Fragment.Updatesanpham;
import com.example.mystore.Model.CheckConnection;
import com.example.mystore.Model.Sanpham;
import com.example.mystore.Model.Server;
import com.example.mystore.QuanLySanPham;
import com.example.mystore.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class QuanLySPAdapter extends RecyclerView.Adapter<QuanLySPAdapter.ItemHoler> {
    private QuanLySanPham context;
    ArrayList<Sanpham> arrayList;
    QuanLySanPham quanLy;


    public QuanLySPAdapter(QuanLySanPham context, ArrayList<Sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_quanly, null);
        ItemHoler itemHoler = new ItemHoler(v);
        return itemHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHoler holder, final int position) {
        final Sanpham sanpham = arrayList.get(position);
        quanLy = new QuanLySanPham();
        holder.tensp.setMaxLines(3);
        holder.tensp.setEllipsize(TextUtils.TruncateAt.END);
        holder.tensp.setText(sanpham.getTensanpham());
        //    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        //   holder.giasp.setText("Giá : " + decimalFormat.format(sanpham.getGia()) + "Đ");
        Picasso.get().load(sanpham.getHinhanh()).into(holder.imghinhanhsp);
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xoa(arrayList.get(position).getId(),position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        public ImageView imghinhanhsp;
        public TextView tensp, giasp;
        public Button btnChinhsua, btnXoa;

        public ItemHoler(View view) {
            super(view);

            imghinhanhsp = (ImageView) view.findViewById(R.id.imgsanpham);
            tensp = (TextView) view.findViewById(R.id.txttensp);
            btnChinhsua = (Button) view.findViewById(R.id.btnChinhsua);
            btnXoa = (Button) view.findViewById(R.id.btnXoa);
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
            btnChinhsua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Updatesanpham.class);
                    intent.putExtra("thongtinchitiet", arrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context, arrayList.get(getPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });
        }
    }
    private void Xoa(final int id,final int position){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa sản phẩm này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Delete(id,position);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        dialogXoa.show();

    }
    public void Delete(int id, final int position){
        final String query = "DELETE FROM sanpham WHERE Id = '" + id + "'";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.linkDeleteSp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            arrayList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("query", query);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
