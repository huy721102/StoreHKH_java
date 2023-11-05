package com.example.storehkh.models;

import java.io.Serializable;

public class PopularProductsModel implements Serializable {
    String description;
    int giatien;
    String danhgia;
    String name;
    String img_url;

    public PopularProductsModel() {
    }

    public PopularProductsModel(String description, int giatien, String danhgia, String name, String img_url) {
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
}
