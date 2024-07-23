package com.example.prj_first.KhachHang;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
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
import com.example.prj_first.GioHang.GioHangFragment;
import com.example.prj_first.R;
import com.example.prj_first.TrangChu.HomeFragment;
import com.example.prj_first.TrangChu.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class KhachHangFragment extends Fragment {
    TextView txtSearchInput;
    RecyclerView rcv;
    TextView fldChosenCustomer ;
    Button btnAddCustomer,btnSearchKH;
    ImageView btnCart,btnBack;
    DBHelper dbHelper ;
    SQLiteDatabase db;
    private MainActivity mainActivity;
    public List<Item_KhachHang> itemKhachHangs = new ArrayList<>();
    private KhachHang_Adapter adapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khach_hang, container, false);
        rcv = view.findViewById(R.id.rcvkhachhang);
        fldChosenCustomer = view.findViewById(R.id.fldChosenCustomer);
        fldChosenCustomer.setText("");
        btnAddCustomer = view.findViewById(R.id.btnAddCustomer);
        btnSearchKH = view.findViewById(R.id.btnSearchKH);
        btnBack = view.findViewById(R.id.btnBack);
        btnCart = view.findViewById(R.id.btnCart);
        txtSearchInput = view.findViewById(R.id.txtSearchInput);
        List<Item_KhachHang> listSearchKH = new ArrayList<>();
        mainActivity = (MainActivity) getActivity();
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        dbHelper.onCreate(db);
        Cursor cursor =dbHelper.getData("select * from db_kh");
        while(cursor.moveToNext())
        {
            Item_KhachHang khachHang = new Item_KhachHang();
            String makh = cursor.getString(0);
            String tenkh = cursor.getString(1);
            String sdt = cursor.getString(2);
            String diachi = cursor.getString(3);
            String email = cursor.getString(4);
            String mota = cursor.getString(5);
            khachHang.setMakh(makh);
            khachHang.setTenkh(tenkh);
            khachHang.setSdt(sdt);
            khachHang.setDiachikh(diachi);
            khachHang.setEmail(email);
            khachHang.setMota(mota);
            itemKhachHangs.add(khachHang);
        }
        cursor.close();
        adapter = new KhachHang_Adapter(itemKhachHangs, new KhachHang_Adapter.clickItemListener() {
            @Override
            public void onClickButtonChosen(Item_KhachHang itemKhachHang) {
                dbHelper.setCustomer(itemKhachHang.getTenkh());
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                HomeFragment fragment = new HomeFragment();
                fragmentTransaction.replace(R.id.frameLayout1,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            @Override
            public void onClickItem(Item_KhachHang itemKhachHang) {
                formUpdateKH(itemKhachHang);
            }
        });
        btnSearchKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtSearchInput!=null) {
                    listSearchKH.clear();
                    for (Item_KhachHang itemKhachHang : itemKhachHangs) {
                        if (itemKhachHang.getTenkh().toString().contains(txtSearchInput.getText())) {
                            listSearchKH.add(itemKhachHang);
                        }
                    }
                    if(listSearchKH.isEmpty())
                        Toast.makeText(getContext(),"Không có thông tin khách hàng này",Toast.LENGTH_LONG).show();
                    adapter.updateAdapter(listSearchKH);
                }
            }
        });
        btnBack.setOnClickListener(v->{
            getParentFragmentManager().popBackStack();
        });
        btnCart.setOnClickListener(v->{
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new GioHangFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(adapter);
        if(rcv!=null)
        {
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
            rcv.addItemDecoration(itemDecoration);
        }
        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formAddKH();
            }
        });
        String makh = dbHelper.getCustomer();
        if (makh != null) {
            for (Item_KhachHang kh : itemKhachHangs) {
                if (kh.getTenkh().equals(makh)) {
                    SpannableString boldten = new SpannableString(kh.getTenkh());
                    boldten.setSpan(new StyleSpan(Typeface.BOLD), 0, kh.getTenkh().length(), 0);
                    SpannableString diachikh = new SpannableString("\n" + kh.getDiachikh().toString());
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    spannableStringBuilder.append(boldten);
                    spannableStringBuilder.append(diachikh);
                    fldChosenCustomer.setText(spannableStringBuilder, TextView.BufferType.SPANNABLE);
                    break;
                }
            }
        }
        else
            fldChosenCustomer.setText("");
        return view;
    }
    private void formAddKH()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.fragment_thongtinkhachhang,null);
        TextView txtCode,txtName,txtPhone,txtAdress,txtEmail,txtDescription;
        Button btnAddCustomer,btnUpdate,btnDelete;
        ImageView btnBack;
        btnAddCustomer = view.findViewById(R.id.btnAdd);
        btnDelete=view.findViewById(R.id.btnDelete);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
        btnBack = view.findViewById(R.id.btnBack);
        txtCode = view.findViewById(R.id.txtCode);
        txtName = view.findViewById(R.id.txtName);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtAdress = view.findViewById(R.id.txtAdress);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtDescription = view.findViewById(R.id.txtDescription);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = txtCode.getText().toString();
                String name = txtName.getText().toString();
                String adress = txtAdress.getText().toString();
                String iphone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();
                String mota = txtDescription.getText().toString();
                String error="";
                if(code.isEmpty()||name.isEmpty()||iphone.isEmpty()||adress.isEmpty()) {
                    error = "Không được để trống";
                    if (code.isEmpty())
                        error = error + " mã khách hàng,";
                    if (name.isEmpty())
                        error = error + " tên khách hàng,";
                    if (iphone.isEmpty())
                        error = error + " số điện thoại,";
                    if (adress.isEmpty())
                        error = error + " địa chỉ";
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                }
                else
                {

                    Snackbar.make(view,"Thành công",Snackbar.LENGTH_LONG)
                            .setAction("Đồng ý", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dbHelper.insertKH(code,name,iphone,adress,email,mota);
                                    Item_KhachHang khachHang = new Item_KhachHang();
                                    khachHang.setMakh(code);
                                    khachHang.setTenkh(name);
                                    khachHang.setSdt(iphone);
                                    khachHang.setDiachikh(adress);
                                    khachHang.setEmail(email);
                                    khachHang.setMota(mota);
                                    itemKhachHangs.add(khachHang);
                                    adapter.notifyItemInserted(itemKhachHangs.size() - 1);
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void formUpdateKH(Item_KhachHang itemKhachHang)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.fragment_thongtinkhachhang,null);
        TextView txtCode,txtName,txtPhone,txtAdress,txtEmail,txtDescription;
        Button btnAddCustomer,btnUpdate,btnDelete;
        ImageView btnBack;
        btnBack = view.findViewById(R.id.btnBack);
        btnAddCustomer = view.findViewById(R.id.btnAdd);
        btnAddCustomer = view.findViewById(R.id.btnAdd);
        btnAddCustomer.setVisibility(View.GONE);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        txtCode = view.findViewById(R.id.txtCode);
        txtName = view.findViewById(R.id.txtName);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtAdress = view.findViewById(R.id.txtAdress);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtName.setText(itemKhachHang.getTenkh());
        txtCode.setText(itemKhachHang.getMakh());
        txtEmail.setText(itemKhachHang.getEmail());
        txtPhone.setText(itemKhachHang.getSdt());
        txtDescription.setText(itemKhachHang.getMota());
        txtAdress.setText(itemKhachHang.getDiachikh());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemKhachHang.setTenkh(txtName.getText().toString());
                itemKhachHang.setDiachikh(txtAdress.getText().toString());
                ContentValues values = new ContentValues();
                values.put("diachikh",""+txtAdress.getText().toString()+"");
                values.put("tenkh",""+txtName.getText().toString()+"");
                values.put("sdt",""+txtPhone.getText().toString()+"");
                values.put("email",""+txtEmail.getText().toString()+"");
                values.put("mota",""+txtDescription.getText().toString()+"");
                String whereClause = "makh = ?";
                String[] whereArgs = new String[]{"" + txtCode.getText().toString() + ""};
                db = dbHelper.getWritableDatabase();
                try {
                    int rowupdate = db.update("db_kh",values,whereClause,whereArgs);
                    if(rowupdate >0){
                        Snackbar.make(view,"Cập nhật thành công",Snackbar.LENGTH_LONG)
                                .setAction("Đồng ý", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else {
                        Log.e("Update error", "No row update");
                        Snackbar.make(view,"Cập nhật thất bại",Snackbar.LENGTH_LONG)
                                .setAction("Đồng ý", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                }
                catch (Exception e) {
                    Log.e("Database error","Can't update khách hàng",e);
                }
                adapter.updateAdapter(itemKhachHangs);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.queryData("Delete from db_kh where makh="+itemKhachHang.getMakh()+"");
                itemKhachHangs.remove(itemKhachHang);
                adapter.updateAdapter(itemKhachHangs);
                String makh = dbHelper.getCustomer();
                if(itemKhachHang.getMakh().equals(makh)) {
                    dbHelper.deleteCustomer(itemKhachHang.getMakh());
                    fldChosenCustomer.setText("");
                }
                dialog.dismiss();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}