package com.example.mystore.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Model.DonHang;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter extends BaseAdapter {
    ArrayList<DonHang> arrayList;
    private Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Adapter(ArrayList<DonHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView tvgiatien, tvngaydat, tvmadonhang, tenkhachhang, trangthai, sdt, diachi;
        public ImageButton btnmenu;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (view == null) { //nếu chưa có chưa có dữ liệu
            viewHolder = new ViewHolder();
            //gán layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_don_hang, null);
            viewHolder.tvmadonhang = view.findViewById(R.id.Madonhang);
            viewHolder.tenkhachhang = view.findViewById(R.id.TenKhachHang);
            viewHolder.tvngaydat = view.findViewById(R.id.ngaydat);
            viewHolder.tvgiatien = view.findViewById(R.id.Tongtien);
            viewHolder.diachi = view.findViewById(R.id.diachi);
            viewHolder.sdt = view.findViewById(R.id.sdt);
            viewHolder.trangthai = view.findViewById(R.id.trangthai);
            viewHolder.btnmenu = view.findViewById(R.id.btnmenu);

            view.setTag(viewHolder);
        } else {//nếu có dữ liệu chỉ cần gán lại
            viewHolder = (ViewHolder) view.getTag();
        }
        final DonHang donHang = (DonHang) getItem(position);
        int madonhang = donHang.getMadonhang();
        viewHolder.tvmadonhang.setText(String.valueOf(madonhang));
        viewHolder.tenkhachhang.setText(donHang.getTentv());
        viewHolder.diachi.setText(donHang.getDiachi());
        int sdt = donHang.getSdt();
        viewHolder.sdt.setText("0"+String.valueOf(sdt));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvgiatien.setText(decimalFormat.format(donHang.getTongtien()) + " đ");
        viewHolder.tvngaydat.setText(donHang.getNgaydat());
        int trang = Integer.parseInt(donHang.getTrangthai());
        if(trang ==1){
            viewHolder.trangthai.setText("Chưa xác nhận");
        }if(trang ==2) {
            viewHolder.trangthai.setText("Xác nhận đơn");
        }if(trang ==3) {
            viewHolder.trangthai.setText("Đang giao hàng");
        }
        if(trang ==4) {
            viewHolder.trangthai.setText("Đã giao");
        }

        sharedPreferences = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        final int matv = sharedPreferences.getInt("matv", 0);
        if (matv == 1) {
            viewHolder.btnmenu.setVisibility(View.VISIBLE);
        } else {
            viewHolder.btnmenu.setVisibility(View.INVISIBLE);
        }
        if(trang==4){
            viewHolder.btnmenu.setVisibility(View.INVISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        finalViewHolder.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(context, finalViewHolder.btnmenu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.xacnhan:
                                finalViewHolder.trangthai.setText("Xác nhận đơn");
                                String ma = (String) finalViewHolder.tvmadonhang.getText();
                                final String query = "UPDATE donhang SET trangthai = '2' WHERE madonhang = '" + ma + "'";
                                Update(query);
                                arrayList.remove(position);
                                break;
                            case R.id.danggiao:
                                finalViewHolder.trangthai.setText("Đang giao hàng");
                                String ma1 = (String) finalViewHolder.tvmadonhang.getText();
                                final String query1 = "UPDATE donhang SET trangthai = '3' WHERE madonhang = '" + ma1 + "'";
                                Update(query1);
                                arrayList.remove(position);
                                break;
                            case R.id.hoanthanh:
                                finalViewHolder.trangthai.setText("Đã giao");
                                String ma2 = (String) finalViewHolder.tvmadonhang.getText();
                                final String query2 = "UPDATE donhang SET trangthai = '4' WHERE madonhang = '" + ma2 + "'";
                                Update(query2);
                                arrayList.remove(position);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        if( trang == 1 && matv !=1 ){
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Xoa(arrayList.get(position).getMadonhang(),position);
                    return true;
                }
            });
        } else
        if( trang != 4 && matv == 1 ) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Xoa(arrayList.get(position).getMadonhang(), position);
                    return true;
                }
            });
        }
        return view;
    }

    public void Update(final String query) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.updateDonHang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            Toast.makeText(context, "Thay đổi trạng thái thành công", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Thay đổi thất bại", Toast.LENGTH_LONG).show();
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
    private void Xoa(final int id,final int position){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn xác nhận hủy đơn hàng này");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Delete(id,position);
                DeleteCT(id,position);
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
        final String query = "DELETE FROM donhang WHERE madonhang = '" + id + "'";
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
    public void DeleteCT(int id, final int position){
        final String query = "DELETE FROM chitietdonhang WHERE madonhang = '" + id + "'";
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
