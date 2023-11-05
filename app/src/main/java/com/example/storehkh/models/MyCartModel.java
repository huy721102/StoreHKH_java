package com.example.storehkh.models;

public class MyCartModel {
    String Giasanpham;
    String Gio;
    String Ngay;
    String Tensanpham;
    String Tongsoluong;
    int Tongtien;

    public MyCartModel() {
    }

    public MyCartModel(String giasanpham, String gio, String ngay, String tensanpham, String tongsoluong, int tongtien) {
        Giasanpham = giasanpham;
        Gio = gio;
        Ngay = ngay;
        Tensanpham = tensanpham;
        Tongsoluong = tongsoluong;
        Tongtien = tongtien;
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
}
