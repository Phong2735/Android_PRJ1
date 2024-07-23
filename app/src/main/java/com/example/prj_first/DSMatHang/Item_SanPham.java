package com.example.prj_first.DSMatHang;

import java.io.Serializable;

public class Item_SanPham implements Serializable {
    String mota,price;
    int image;

    public Item_SanPham(String mota, String price, int image) {
        this.mota = mota;
        this.price = price;
        this.image = image;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
