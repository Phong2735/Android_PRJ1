package com.example.prj_first.DSMatHang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj_first.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class Adapter_SanPham extends RecyclerView.Adapter<Adapter_SanPham.SanPhamViewHolder> {
    @NonNull
    List<Item_SanPham> items;
    public Adapter_SanPham( List<Item_SanPham> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          return new SanPhamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        Item_SanPham itemSanPham = items.get(position);
        holder.bind(itemSanPham);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mota,price;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                Item_SanPham itemSanPham = items.get(getBindingAdapterPosition());
                EventBus.getDefault().post(itemSanPham);
            });
        }
        public void bind(Item_SanPham itemSanPham){
            imageView = itemView.findViewById(R.id.imhang);
            imageView.setImageResource(itemSanPham.getImage());
            mota = itemView.findViewById(R.id.motahang);
            mota.setText(itemSanPham.getMota());
            price =itemView.findViewById(R.id.pricehang);
            price.setText(itemSanPham.getPrice());
        }
    }
}
