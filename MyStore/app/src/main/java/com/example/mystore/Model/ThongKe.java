package com.example.mystore.Model;

public class ThongKe {
    String thang;
    String nam;
    int tien;

    public ThongKe(String thang, String nam, int tien) {
        this.thang = thang;
        this.nam = nam;
        this.tien = tien;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }
}
