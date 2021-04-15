package com.example.mystore.Model;

public class Server {
    public static  String localhost = "192.168.43.55:45455";
    public static  String GetAll = "http://"+localhost+"/CuaHang/GetAll";
    public static  String GetLaptop = "http://"+localhost+"/CuaHang/getSP_Maloai/1";
    public static  String GetPC = "http://"+localhost+"/CuaHang/getSP_Maloai/2";
    public static  String GetNewLap = "http://"+localhost+"/CuaHang/getnewSP/1";
    public static  String GetNewPC = "http://"+localhost+"/CuaHang/getnewSP/2";
    public static  String GetLogin = "http://"+localhost+"/CuaHang/dangnhap/";
    public static  String UpdateSanPham = "http://"+localhost+"/api/updatesanpham/";
    public static  String UpdateDonhang = "http://"+localhost+"/api/updatedonhang/";
    public static  String PostResgint = "http://"+localhost+"/api/addthanhvien";
    public static  String UpdateTK = "http://"+localhost+"/api/updateTK/";
    public static  String PostSanPham = "http://"+localhost+"/api/addsanpham";
    public static  String GetTen = "http://"+localhost+"/CuaHang/getSP_ten/";
    public static  String GetAsus = "http://"+localhost+"/CuaHang/getSP_ten/Asus";
    public static  String GetAcer = "http://"+localhost+"/CuaHang/getSP_ten/Acer";
    public static  String GetDell = "http://"+localhost+"/CuaHang/getSP_ten/Dell";
    public static  String GetHP = "http://"+localhost+"/CuaHang/getSP_ten/HP";
    public static  String GetLenovo = "http://"+localhost+"/CuaHang/getSP_ten/Lenovo";
    public static  String GetMsi = "http://"+localhost+"/CuaHang/getSP_ten/Msi";
    public static  String GetGia1 = "http://"+localhost+"/CuaHang/getSP_giaMinMax/0/10000000";
    public static  String GetGia2 = "http://"+localhost+"/CuaHang/getSP_giaMinMax/10000000/20000000";
    public static  String GetGia3 = "http://"+localhost+"/CuaHang/getSP_giaMinMax/20000000/30000000";
    public static  String GetGia4 = "http://"+localhost+"/CuaHang/getSP_giaMinMax/30000000/40000000";
    public static  String GetGia5 = "http://"+localhost+"/CuaHang/getSP_giaMinMax/40000000/50000000";
    public static  String GetGia6 = "http://"+localhost+"/CuaHang/getSP_giaMinMax/50000000/1000000000";
    public static  String linkDeleteSp = "http://"+localhost+"/api/deletesanpham/";
    public static String getHoaDonAdmin = "http://"+localhost+"/CuaHang/get_hoadonAdmin/";
    public static String gethoadonUser = "http://"+localhost+"/CuaHang/get_hoadonUser/";
    public static String ketnoi = "http://"+localhost+"/CuaHang/thongkethang/";
    public static String ketnoinam = "http://"+localhost+"/CuaHang/thongkenam/";
    public static String ketnoithang = "http://"+localhost+"/CuaHang/thongkethang/";
    public static  String linkdiachi = "http://"+localhost+"/CuaHang/getdiachi/";
    public static  String Postdiachi = "http://"+localhost+"/api/adddiachi/";
    public static  String Postchitietdonhang = "http://"+localhost+"/api/addchitiet";
    public static  String Postdonhang = "http://"+localhost+"/api/adddonhang";
    public static  String Getnewdh = "http://"+localhost+"/CuaHang/getnewdonhang";



    public static  String linkthemdiachi = "http://"+localhost+"/api/";
    public static  String linkDelete = "http://"+localhost+"/server/linkDelete.php";
    public static String getDonHang = "http://"+localhost+"/server/getDonHang.php";
    public static String getNguoidung = "http://"+localhost+"/server/getNguoidung.php";
    public static String getDiachi = "http://"+localhost+"/server/timkiemDC.php";


}

