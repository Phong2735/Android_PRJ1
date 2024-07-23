package com.example.prj_first.DSMatHang.Color;

public class Item_ProductColor {
    int lblColorAmount;
    String lblColorName;
    int remove,add ;

    public Item_ProductColor(String lblColorName, int lblColorAmount) {
        this.lblColorName = lblColorName;
        this.lblColorAmount = lblColorAmount;
        this.remove = remove;
        this.add = add;
    }

    public String getLblColorName() {
        return lblColorName;
    }

    public void setLblColorName(String lblColorName) {
        this.lblColorName = lblColorName;
    }

    public int getLblColorAmount() {
        return lblColorAmount;
    }

    public void setLblColorAmount(int lblColorAmount) {
        this.lblColorAmount = lblColorAmount;
    }

}
