package com.example.mystore.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystore.Model.DonHang;
import com.example.mystore.Model.Server;
import com.example.mystore.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
        if (view == null) { //n???u ch??a c?? ch??a c?? d??? li???u
            viewHolder = new ViewHolder();
            //g??n layout
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
        } else {//n???u c?? d??? li???u ch??? c???n g??n l???i
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
        viewHolder.tvgiatien.setText(decimalFormat.format(donHang.getTongtien()) + " ??");
        final int tien =  donHang.getTongtien();
        viewHolder.tvngaydat.setText(donHang.getNgaydat());
        int trang = Integer.parseInt(donHang.getTrangthai());
        if(trang ==1){
            viewHolder.trangthai.setText("Ch??a x??c nh???n");
        }if(trang ==2) {
            viewHolder.trangthai.setText("X??c nh???n ????n");
        }if(trang ==3) {
            viewHolder.trangthai.setText("??ang giao h??ng");
        }
        if(trang ==4) {
            viewHolder.trangthai.setText("???? giao");
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
                final int vitri = position;
                Log.e("vitr", String.valueOf(vitri));
                final PopupMenu popupMenu = new PopupMenu(context, finalViewHolder.btnmenu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.xacnhan:
                                finalViewHolder.trangthai.setText("X??c nh???n ????n");
                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                JSONObject postData = new JSONObject();
                                try {
                                    postData.put("madonhang",finalViewHolder.tvmadonhang.getText().toString());
                                    postData.put("matv", matv);
                                    postData.put("tentv", finalViewHolder.tenkhachhang.getText().toString());
                                    postData.put("sdt",finalViewHolder.sdt.getText().toString());
                                    postData.put("diachi", finalViewHolder.diachi.getText().toString());
                                    postData.put("ngaydathang", finalViewHolder.tvngaydat.getText().toString());
                                    postData.put("tongtien", tien);
                                    postData.put("trangthai", 2);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT , Server.UpdateDonhang+finalViewHolder.tvmadonhang.getText().toString(), postData,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                if(response.length()>0){
                                                    Toast.makeText(context, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });
                                requestQueue.add(jsonObjectRequest);
                                arrayList.remove(vitri);
                                break;
                            case R.id.danggiao:
                                finalViewHolder.trangthai.setText("??ang giao h??ng");
                                RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                                JSONObject postData1 = new JSONObject();

                                try {
                                    postData1.put("madonhang",finalViewHolder.tvmadonhang.getText().toString());
                                    postData1.put("matv", matv);
                                    postData1.put("tentv", finalViewHolder.tenkhachhang.getText().toString());
                                    postData1.put("sdt",finalViewHolder.sdt.getText().toString());
                                    postData1.put("diachi", finalViewHolder.diachi.getText().toString());
                                    postData1.put("ngaydathang", finalViewHolder.tvngaydat.getText().toString());
                                    postData1.put("tongtien", tien);
                                    postData1.put("trangthai", 3);

                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.PUT , Server.UpdateDonhang+finalViewHolder.tvmadonhang.getText().toString(), postData1,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                if(response.length()>0){
                                                    Toast.makeText(context, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });
                                requestQueue1.add(jsonObjectRequest1);
                                arrayList.remove(vitri);
                                break;

                            case R.id.hoanthanh:
                                finalViewHolder.trangthai.setText("???? giao");
                                RequestQueue requestQueue2 = Volley.newRequestQueue(context);
                                JSONObject postData2 = new JSONObject();

                                try {
                                    postData2.put("madonhang",finalViewHolder.tvmadonhang.getText().toString());
                                    postData2.put("matv", matv);
                                    postData2.put("tentv", finalViewHolder.tenkhachhang.getText().toString());
                                    postData2.put("sdt",finalViewHolder.sdt.getText().toString());
                                    postData2.put("diachi", finalViewHolder.diachi.getText().toString());
                                    postData2.put("ngaydathang", finalViewHolder.tvngaydat.getText().toString());
                                    postData2.put("tongtien", tien);
                                    postData2.put("trangthai", 4);

                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.PUT , Server.UpdateDonhang+finalViewHolder.tvmadonhang.getText().toString(), postData2,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                if(response.length()>0){
                                                    Toast.makeText(context, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });
                                requestQueue2.add(jsonObjectRequest2);
                                arrayList.remove(vitri);
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

    public void UpdateDH(final String query) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.UpdateDonhang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            Toast.makeText(context, "Thay ?????i tr???ng th??i th??nh c??ng", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Thay ?????i th???t b???i", Toast.LENGTH_LONG).show();
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
        dialogXoa.setMessage("B???n x??c nh???n h???y ????n h??ng n??y");
        dialogXoa.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Delete(id,position);
                DeleteCT(id,position);
            }
        });
        dialogXoa.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
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
                            Toast.makeText(context, "X??a th??nh c??ng", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(context, "X??a th???t b???i", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(context, "X??a th??nh c??ng", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(context, "X??a th???t b???i", Toast.LENGTH_LONG).show();
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
