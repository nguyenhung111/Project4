package com.example.mystore.Model;

public class GioHang {
    public  int idsp;
    public  String tensp;
    public long gia;
    public String hinhanhsp;
    public int soluongsp;

    public GioHang(int idsp, String tensp, long gia, String hinhanhsp, int soluongsp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.gia = gia;
        this.hinhanhsp = hinhanhsp;
        this.soluongsp = soluongsp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }
}
