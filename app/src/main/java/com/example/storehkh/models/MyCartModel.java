package com.example.storehkh.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    String Giasanpham;
    String Gio;
    String Ngay;
    String Tensanpham;
    String Tongsoluong;
    int Tongtien;
     String MaSanPham;
     Integer Type;
    public MyCartModel() {
    }

    public MyCartModel(String giasanpham, String gio, String ngay, String tensanpham, String tongsoluong, int tongtien, String maSanPham, Integer type) {
        Giasanpham = giasanpham;
        Gio = gio;
        Ngay = ngay;
        Tensanpham = tensanpham;
        Tongsoluong = tongsoluong;
        Tongtien = tongtien;
        MaSanPham = maSanPham;
        Type = type;
    }


    public String getGiasanpham() {
        return Giasanpham;
    }

    public void setGiasanpham(String giasanpham) {
        Giasanpham = giasanpham;
    }

    public String getGio() {
        return Gio;
    }

    public void setGio(String gio) {
        Gio = gio;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public String getTongsoluong() {
        return Tongsoluong;
    }

    public void setTongsoluong(String tongsoluong) {
        Tongsoluong = tongsoluong;
    }

    public int getTongtien() {
        return Tongtien;
    }

    public void setTongtien(int tongtien) {
        Tongtien = tongtien;
    }


    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        MaSanPham = maSanPham;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }
}
