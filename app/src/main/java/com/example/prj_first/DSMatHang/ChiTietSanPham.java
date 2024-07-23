package com.example.prj_first.DSMatHang;

import java.io.Serializable;

public class ChiTietSanPham implements Serializable {
    int img;
    String name,sl,total;

    public ChiTietSanPham(int img, String name, String sl, String total) {
        this.img = img;
        this.name = name;
        this.sl = sl;
        this.total = total;
    }

    public ChiTietSanPham() {}
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
