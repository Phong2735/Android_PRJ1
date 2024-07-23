package com.example.prj_first.GioHang;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj_first.DBHelper;
import com.example.prj_first.DSMatHang.ChiTietSanPham;
import com.example.prj_first.R;
import com.example.prj_first.TrangChu.BottomMenuFragment;
import com.example.prj_first.TrangChu.MainActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GioHangFragment extends Fragment {
    MainActivity mainActivity;
    RecyclerView rcvgiohang;
    TextView lblchonsp,btnchonsp,soluong,total;
    Button btndeleteAll,btncreateOrder;
    RelativeLayout groupLayout;
    GioHang_Adapter adapter;
    List<ChiTietSanPham> chiTietSanPhamList = new ArrayList<>();
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        ImageView btnBack;
        lblchonsp = view.findViewById(R.id.lblchonsp);
        btnchonsp = view.findViewById(R.id.btnchonsp);
        soluong = view.findViewById(R.id.soluong);
        btndeleteAll = view.findViewById(R.id.btnDeleteAll);
        btncreateOrder = view.findViewById(R.id.btnCreatOrder);
        total = view.findViewById(R.id.total);
        dbHelper = new DBHelper(getActivity());
        sqLiteDatabase = dbHelper.getReadableDatabase();
        btndeleteAll.setOnClickListener(v->{
            deleteallGH();
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new BottomMenuFragment());
            fragmentTransaction.commit();
        });
        Cursor cursor = dbHelper.getData("Select * From cart");
        while (cursor.moveToNext())
        {
            ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
            int img = Integer.parseInt(cursor.getString(1));
            String tensp = cursor.getString(2);
            String soluong = cursor.getString(3);
            String tongtien = cursor.getString(4);
            chiTietSanPham.setImg(img);
            chiTietSanPham.setName(tensp);
            chiTietSanPham.setSl(soluong);
            chiTietSanPham.setTotal(tongtien);
            chiTietSanPhamList.add(chiTietSanPham);
        }
        if(cursor.getCount()!=0)
        {
            lblchonsp.setVisibility(View.GONE);
            btnchonsp.setVisibility(View.GONE);
            adapter = new GioHang_Adapter(chiTietSanPhamList,getParentFragmentManager());
            rcvgiohang = view.findViewById(R.id.rcvgiohang);
            rcvgiohang.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvgiohang.setAdapter(adapter);
        }
        else
        {
            btnchonsp.setOnClickListener(v -> {
               goTrangChu();
            });
            groupLayout = view.findViewById(R.id.groupLayout);
            groupLayout.setVisibility(View.GONE);
        }
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
        tinh(chiTietSanPhamList);
        btncreateOrder.setOnClickListener(v->{
            String tenkh = dbHelper.getCustomer();
            String ssoluong = soluong.getText().toString();
            String stotal = total.getText().toString();
            dbHelper.insertOder(tenkh,ssoluong,stotal);
            Toast.makeText(getContext(),"Create order success",Toast.LENGTH_SHORT).show();
            goTrangChu();
        });

        return view;
    }
    public void tinh(List<ChiTietSanPham> list)
    {
        int x=0,y=0;
        for (ChiTietSanPham ct: list) {
            int index = ct.getTotal().indexOf(":");
            String subtotal = ct.getTotal().substring((index+1));
            String total = subtotal.replace(" VND","").replace(",","").trim();
            int index1 = ct.getSl().indexOf(":");
            String subsl = ct.getSl().substring((index1+1)).trim();
            x+=Integer.parseInt(total);
            y+=Integer.parseInt(subsl);
        }
        soluong.setText(String.valueOf(y));
        total.setText(String.valueOf(formatPrice(x)));
    }
    private String formatPrice(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(amount) + " VND";
    }
    public void deleteGH(ChiTietSanPham chiTietSanPham)
    {
        String name = chiTietSanPham.getName();
        String sql = "Delete from cart where tensp = ?";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.bindString(1,name);
        sqLiteStatement.executeUpdateDelete();
        sqLiteStatement.close();
    }
    public void deleteallGH()
    {
        String sql = "Delete from cart";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.executeUpdateDelete();
        sqLiteStatement.close();
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();
        adapter.updateGH(chiTietSanPhams);
    }
    public void goTrangChu()
    {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,new BottomMenuFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}