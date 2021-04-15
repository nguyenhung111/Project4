package com.example.mystore.Model;

import java.io.Serializable;

public class DonHang implements Serializable {

    int madonhang;
    int matv;
    String tentv;
    int sdt;
    String diachi;
    String ngaydat;
    int tongtien;
    String trangthai;

    public DonHang(int madonhang, int matv, String tentv, int sdt, String diachi, String ngaydat, int tongtien, String trangthai) {
        this.madonhang = madonhang;
        this.matv = matv;
        this.tentv = tentv;
        this.sdt = sdt;
        this.diachi = diachi;
        this.ngaydat = ngaydat;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public DonHang() {
    }


    public String getTentv() {
        return tentv;
    }

    public void setTentv(String tentv) {
        this.tentv = tentv;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public int getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(int madonhang) {
        this.madonhang = madonhang;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
