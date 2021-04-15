package com.example.mystore.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.mystore.Model.Dathang;
import com.example.mystore.Model.Diachi;
import com.example.mystore.R;
import com.example.mystore.Sodiachi;

import java.util.ArrayList;

public class SodiachiAdapter extends BaseAdapter {
    private Sodiachi context;
    ArrayList<Diachi> listdiachi;

    public SodiachiAdapter(Sodiachi context, ArrayList<Diachi> listdiachi) {
        this.context = context;
        this.listdiachi = listdiachi;
    }

    @Override
    public int getCount() {
        return listdiachi.size();
    }

    @Override
    public Object getItem(int position) {
        return listdiachi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        private TextView tvhotenkh, tvsdt,tvcaidat, tvdiachi;
        private LinearLayout listdiachi ;


    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (view == null){ //nếu chưa có chưa có dữ liệu
            viewHolder = new ViewHolder();
            //gán layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_diachi, null);
            viewHolder.tvhotenkh = view.findViewById(R.id.tvhotenkh);
            viewHolder.tvsdt = view.findViewById(R.id.tvsdtkh);
            viewHolder.tvdiachi = view.findViewById(R.id.tvdiachikh);
            viewHolder.tvcaidat = view.findViewById(R.id.caidat);
            viewHolder.listdiachi = view.findViewById(R.id.listdiachi);

            view.setTag(viewHolder);
        }else {//nếu có dữ liệu chỉ cần gán lại
            viewHolder = (ViewHolder) view.getTag();
        }
        Diachi diachi = (Diachi) getItem(position);
        viewHolder.tvhotenkh.setText(diachi.getHoten());
        viewHolder.tvsdt.setText(diachi.getSdt());
        viewHolder.tvdiachi.setText(diachi.getDiachi());

        viewHolder.listdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Dathang.class);
                intent.putExtra("hoten", listdiachi.get(position).getHoten());
                intent.putExtra("sdt", listdiachi.get(position).getSdt());
                intent.putExtra("diachi", listdiachi.get(position).getDiachi());
                context.startActivity(intent);
            }
        });
        viewHolder.tvcaidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacnhanXoa(listdiachi.get(position).getId());
            }
        });

        return  view;
    }
    private void XacnhanXoa(final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa địa chỉ này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                context.DeleteDiachi(id);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        dialogXoa.show();
    }
}
