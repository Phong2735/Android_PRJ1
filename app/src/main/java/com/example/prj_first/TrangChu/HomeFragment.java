package com.example.prj_first.TrangChu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj_first.DBHelper;
import com.example.prj_first.DSMatHang.Adapter_SanPham;
import com.example.prj_first.DSMatHang.ChiTietSanPhamFragment;
import com.example.prj_first.DSMatHang.Item_SanPham;
import com.example.prj_first.DangNhap.DangNhapActivity;
import com.example.prj_first.GioHang.GioHangFragment;
import com.example.prj_first.KhachHang.KhachHangFragment;
import com.example.prj_first.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView rcv;
    Adapter_SanPham adapter;
    ImageView btnCart,btnBack;
    Item_SanPham itemSanPham;
    MainActivity mainActivity = (MainActivity) getActivity();
    DBHelper dbHelper;
    SQLiteDatabase db;
    private List<Item_SanPham> itemSanPhams = new ArrayList<>(Arrays.asList(
            new Item_SanPham("Caravat sọc siu mã 25-GTB 85","0 VND", R.drawable.img_1),
        new Item_SanPham("Dây thắt lưng mã 125-GTB125","0 VND", R.drawable.img),
        new Item_SanPham("Quần Jeans Nam GTB440","225,000 VND", R.drawable.img_2),
        new Item_SanPham("Nút Vũ Tuấn trắng","400 VND", R.drawable.img_4),
        new Item_SanPham("Áo sơ mi ngắn tay kẻ TC Regular Fit-345","200 VND", R.drawable.img_3)
        ));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView btnChoose;
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        TextView lblchonkh = view.findViewById(R.id.lblchonkh);
        btnChoose = view.findViewById(R.id.btnChooseCustomer);
        btnCart = view.findViewById(R.id.btnCart);
        btnBack = view.findViewById(R.id.btnBack);
        rcv = view.findViewById(R.id.rcvsanpham);
        rcv.setLayoutManager(new GridLayoutManager(getContext(),2));
        dbHelper = new DBHelper(getActivity());
        if(dbHelper.getCustomer()!=null){
            lblchonkh.setVisibility(View.GONE);
            btnChoose.setVisibility(View.GONE);
            adapter = new Adapter_SanPham(itemSanPhams);
            rcv.setAdapter(adapter);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout1,new KhachHangFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new GioHangFragment();
                fragmentTransaction.replace(R.id.frameLayout,fragment,"GioHang_Fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItem_SanPham(Item_SanPham event) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sanpham",event);
        Fragment fragment = new ChiTietSanPhamFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}