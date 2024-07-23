package com.example.prj_first.TrangChu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prj_first.DonHang.DonHangFragment;
import com.example.prj_first.KhachHang.KhachHangFragment;
import com.example.prj_first.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenuFragment extends Fragment {

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);
        BottomNavigationView menu;
        menu = view.findViewById(R.id.bottomNavigationView);
        replaceChild(new HomeFragment());
        menu.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout1,new HomeFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            if (item.getItemId() == R.id.khachhang) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout1,new KhachHangFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            if(item.getItemId() == R.id.donhang) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout1,new DonHangFragment(),"DonHang_Fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            return true;
        });
        return view;
    }
    private void replaceChild(Fragment fragment)
    {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout1,new HomeFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}