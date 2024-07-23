package com.example.prj_first.DSMatHang.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj_first.DSMatHang.ChiTietSanPhamFragment;
import com.example.prj_first.R;

import java.util.List;

public class Adapter_Color extends RecyclerView.Adapter<Adapter_Color.ColorViewHolder> {
    private List<Item_ProductColor> list;
    private int sl=0;
    ChiTietSanPhamFragment ct;
    public Adapter_Color(ChiTietSanPhamFragment ct, List<Item_ProductColor> list) {
        this.ct = ct;
        this.list = list;
        updateTotalAmount();
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
        if(ct!=null){
            ct.updateAmount();
        }
    }
    public void updateTotalAmount() {
        sl = 0;
        for (Item_ProductColor item : list) {
            sl += item.getLblColorAmount();
        }
        setSl(sl);
    }
    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productcolor,parent,false);
        return new ColorViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        Item_ProductColor itemProductColor = list.get(position);
        holder.name.setText(itemProductColor.getLblColorName());
        holder.amount.setText(String.valueOf(itemProductColor.getLblColorAmount()));
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluong = itemProductColor.getLblColorAmount();
                if(soluong>=1) {
                    itemProductColor.setLblColorAmount(soluong - 1);
                    holder.amount.setText(String.valueOf(itemProductColor.getLblColorAmount()));
                    updateTotalAmount();
                }
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluong = itemProductColor.getLblColorAmount();
                itemProductColor.setLblColorAmount(soluong+1);
                holder.amount.setText(String.valueOf(itemProductColor.getLblColorAmount()));
                updateTotalAmount();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        return 0;
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder{
        private TextView name,amount;
        private ImageView btnAdd,btnRemove;
        View view;
        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            name = itemView.findViewById(R.id.lblColorName);
            amount = itemView.findViewById(R.id.lblColorAmount);
            view = itemView.findViewById(R.id.view1);
        }
    }
}
