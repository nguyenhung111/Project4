package com.example.mystore.Model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int id;
    public String tensanpham;
    public int gia;
    public String hinhanh;
    public int soluong;
    public String mota;
    public int idsanpham;

    public Sanpham(int id, String tensanpham, int gia, String hinhanh, int soluong, String mota, int idsanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.soluong = soluong;
        this.mota = mota;
        this.idsanpham = idsanpham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }
}
