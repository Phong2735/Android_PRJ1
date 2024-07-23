package com.example.prj_first.KhachHang;

import java.io.Serializable;

public class Item_KhachHang implements Serializable {
    String makh,tenkh,diachikh,email,mota,sdt;

    public Item_KhachHang(String makh, String tenkh, String sdt, String diachi, String email, String mota) {
    }

    public Item_KhachHang() {
    }

    public String getDiachikh() {
        return diachikh;
    }

    public void setDiachikh(String diachikh) {
        this.diachikh = diachikh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

}
