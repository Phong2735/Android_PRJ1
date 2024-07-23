package com.example.prj_first.DonHang;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj_first.DBHelper;
import com.example.prj_first.GioHang.GioHangFragment;
import com.example.prj_first.R;

import java.util.ArrayList;
import java.util.List;

public class DonHangFragment extends Fragment {
    RecyclerView rcvdonhang;
    ImageView btnBack,btnCart;
    DonHang_Adapter adapter;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    List<Item_DonHang> itemDonHangList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getActivity());
        View view = inflater.inflate(R.layout.fragment_don_hang, container, false);
        rcvdonhang = view.findViewById(R.id.rcvdonhang);
        btnCart = view.findViewById(R.id.btnCart);
        btnBack = view.findViewById(R.id.btnBack);
        rcvdonhang.setLayoutManager(new LinearLayoutManager(getContext()));
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getData("Select * from donhang");
        while (cursor.moveToNext())
        {
            Item_DonHang itemDonHang = new Item_DonHang();
            String tenkh = cursor.getString(1);
            String soluong = cursor.getString(2);
            String tongtien = cursor.getString(3);
            itemDonHang.setNamekh(tenkh);
            itemDonHang.setSoluong(soluong);
            itemDonHang.setTongtien(tongtien);
            itemDonHangList.add(itemDonHang);
        }
        cursor.close();
        adapter = new DonHang_Adapter(getParentFragmentManager(),itemDonHangList);
        rcvdonhang.setAdapter(adapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
        btnCart.setOnClickListener(v->{
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new GioHangFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        return view;
    }
    public void deleteDH(Item_DonHang itemDonHang)
    {
        String name = itemDonHang.getNamekh();
        String sql = "Delete from donhang where tenkh = ?";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.bindString(1,name);
        sqLiteStatement.executeUpdateDelete();
        sqLiteStatement.close();
    }
}