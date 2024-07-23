package com.example.prj_first.DSMatHang;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj_first.DBHelper;
import com.example.prj_first.DSMatHang.Color.Adapter_Color;
import com.example.prj_first.DSMatHang.Color.Item_ProductColor;
import com.example.prj_first.GioHang.GioHangFragment;
import com.example.prj_first.R;
import com.example.prj_first.TrangChu.BottomMenuFragment;
import com.example.prj_first.TrangChu.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ChiTietSanPhamFragment extends Fragment implements Serializable {
    RecyclerView rcvcolor;
    Adapter_Color adapter;
    ImageView btnBack,btnCart,slideImage;
    Button btnAddtoCart,btnCreateOrder;
    TextView lblProductAmount,lblProductName,lblProductPrice,lblPaymentAmount;
    ChiTietSanPham chiTietSanPham;
    MainActivity mainActivity = (MainActivity) getActivity();
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
    private Item_SanPham itemSanPham;
    public List<Item_ProductColor> itemProductColorLists = new ArrayList<>(Arrays.asList(
            new Item_ProductColor("FK6-G067",0),
            new Item_ProductColor("FK6-G079",0),
            new Item_ProductColor("FK6-G151",0),
            new Item_ProductColor("FK6-G153",0),
            new Item_ProductColor("FK6-G157",0),
            new Item_ProductColor("FK6-G167",0),
            new Item_ProductColor("FK6-G168",0),
            new Item_ProductColor("FK6-G174",0),
            new Item_ProductColor("FK6-G184",0),
            new Item_ProductColor("FK6-G185",0),
            new Item_ProductColor("FK6-G191",0),
            new Item_ProductColor("FK6-G196",0),
            new Item_ProductColor("FK6-G193",0)
            ));
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_chi_tiet_san_pham, container, false);
      rcvcolor = view.findViewById(R.id.rcvProductColor);
      btnBack = view.findViewById(R.id.btnBack);
      btnCart = view.findViewById(R.id.btnCart);
      btnCreateOrder = view.findViewById(R.id.btnCreatOrder);
      lblProductAmount = view.findViewById(R.id.lblProductAmount);
      btnAddtoCart = view.findViewById(R.id.btnAddtoCart);
      lblPaymentAmount = view.findViewById(R.id.lblPaymentAmount);
      lblProductPrice = view.findViewById(R.id.lblProductPrice);
      lblProductName = view.findViewById(R.id.lblProductName);
      adapter = new Adapter_Color(this,itemProductColorLists);
      rcvcolor.setLayoutManager(new LinearLayoutManager(getContext()));
      rcvcolor.setAdapter(adapter);
      updateAmount();
      dbHelper = new DBHelper(getActivity());
      sqLiteDatabase = dbHelper.getWritableDatabase();
      dbHelper.onCreate(sqLiteDatabase);
      if(rcvcolor!=null)
      {
          RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
          rcvcolor.addItemDecoration(itemDecoration);
      }
      btnBack.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
                getParentFragmentManager().popBackStack();
          }
      });
      btnCart.setOnClickListener(v->{
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction.replace(R.id.frameLayout,new GioHangFragment());
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
      });
      Bundle bundleReceive = getArguments();
      if(bundleReceive!=null)
      {
          Item_SanPham item_sanPham = (Item_SanPham) bundleReceive.get("sanpham");
          if(item_sanPham!=null)
          {
              slideImage = view.findViewById(R.id.slideImage);
              lblProductName.setText(item_sanPham.getMota());
              lblProductPrice.setText(item_sanPham.getPrice());
              slideImage.setImageResource(item_sanPham.getImage());
              updateAmount();
          }
      }
      btnAddtoCart.setOnClickListener(v->{
          int img = slideImage.getId();
          String tensp = lblProductName.getText().toString();
          String soluong = lblProductAmount.getText().toString();
          String tongtien = lblPaymentAmount.getText().toString();
          if(checkGH(tensp)==false) {
              dbHelper.insertCart(img, tensp, soluong, tongtien);
              FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
              Fragment fragment = new GioHangFragment();
              fragmentTransaction.replace(R.id.frameLayout, fragment, "GioHang_Fragment");
              fragmentTransaction.addToBackStack(null);
              fragmentTransaction.commit();
          }
          else {
              Toast.makeText(getContext(),"Đã có sản phẩm này trong giỏ hàng",Toast.LENGTH_SHORT).show();
          }
      });
      btnCreateOrder.setOnClickListener(v->{
          String tenkh = dbHelper.getCustomer();
          String soluong = lblProductAmount.getText().toString();
          String tongtien = lblPaymentAmount.getText().toString();
          String tensp = lblProductName.getText().toString();
          try {
              if(checkGH(tensp)==false) {
                  if (dbHelper.insertOder(tenkh, soluong, tongtien))
                      Snackbar.make(view, "Tạo đơn hàng thành công", Snackbar.LENGTH_LONG)
                              .setAction("Đồng ý", new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                      fragmentTransaction.replace(R.id.frameLayout, new BottomMenuFragment());
                                      fragmentTransaction.commit();
                                  }
                              }).show();
                  else
                      Toast.makeText(getContext(), "Thêm giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
              }
              else {
                  Toast.makeText(getContext(),"Đã có sản phẩm này trong giỏ hàng",Toast.LENGTH_SHORT).show();
              }
          }
          catch (Exception e){
              Log.e("Error create order","Error",e);
          }
      });
      return view;
    }
    public Boolean checkGH(String tensp)
    {
        Cursor cursor = dbHelper.getData("Select * From cart");
        Boolean check = false;
        while (cursor.moveToNext())
        {
            String tenspgh = cursor.getString(2);
            if(tenspgh.equals(tensp))
                check=true;
        }
        cursor.close();
        return check;
    }
    public void updateAmount() {
        if(adapter!=null) {
            lblProductAmount.setText("Tổng số lượng: " + adapter.getSl());
            String price = lblProductPrice.getText().toString().replace(",","").replace("VND","").trim();
            int iprice = Integer.parseInt(price);
            int itotal = adapter.getSl()*iprice;
            lblPaymentAmount.setText("Tổng tiền: "+ formatPrice(itotal));
            chiTietSanPham = new ChiTietSanPham();
            Bundle bundleReceive = getArguments();
            Item_SanPham item_sanPham = (Item_SanPham) bundleReceive.get("sanpham");
            chiTietSanPham.setTotal(lblPaymentAmount.getText().toString());
            chiTietSanPham.setSl(lblProductAmount.getText().toString());
            chiTietSanPham.setName(lblProductName.getText().toString());
            chiTietSanPham.setImg(item_sanPham.getImage());
        }
    }
    private String formatPrice(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(amount) + " VND";
    }
}