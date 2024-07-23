package com.example.prj_first.DonHang;

public class Item_DonHang {
    String namekh,soluong,tongtien;
    public Item_DonHang(String namekh, String soluong, String tongtien) {
        this.namekh = namekh;
        this.soluong = soluong;
        this.tongtien = tongtien;
    }

    public Item_DonHang() {

    }

    public String getNamekh() {
        return namekh;
    }

    public void setNamekh(String namekh) {
        this.namekh = namekh;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }
}
