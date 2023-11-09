package com.example.storehkh.models;

import java.io.Serializable;

public class NewProductsModel implements Serializable {
    private String id;
    String description;
    int giatien;
    String danhgia;
    String name;
    String img_url;

    public NewProductsModel() {
    }

    public NewProductsModel(String description, int giatien, String danhgia, String name, String img_url) {
        this.description = description;
        this.giatien = giatien;
        this.danhgia = danhgia;
        this.name = name;
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGiatien() {
        return giatien;
    }
    // nếu muốn chuyển lại kiểu int(number trên firebase) thì chuyển tất cả chữ String gắn với getGiatien chuyển lại int là đc

    public void setGiatien(int giatien) {
        this.giatien = giatien;
    }

    public String getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(String danhgia) {
        this.danhgia = danhgia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
