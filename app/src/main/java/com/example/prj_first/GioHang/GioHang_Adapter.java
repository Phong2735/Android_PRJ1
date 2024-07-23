package com.example.prj_first.GioHang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj_first.DSMatHang.ChiTietSanPham;
import com.example.prj_first.R;

import java.util.List;

public class GioHang_Adapter extends RecyclerView.Adapter<GioHang_Adapter.GioHangViewHolder>{
    List<ChiTietSanPham> chiTietSanPhams;
    public GioHang_Adapter(List<ChiTietSanPham> chiTietSanPhams, FragmentManager fragmentManager) {
        this.chiTietSanPhams = chiTietSanPhams;
        this.fragmentManager = fragmentManager;
    }
    FragmentManager fragmentManager;
    public void updateGH(List<ChiTietSanPham> newData)
    {
        this.chiTietSanPhams = newData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GioHangViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhams.get(position);
        holder.img.setImageResource(R.drawable.img);
        holder.name.setText(chiTietSanPham.getName());
        holder.sl.setText(chiTietSanPham.getSl());
        holder.total.setText(chiTietSanPham.getTotal());
        holder.btnDelete.setOnClickListener(v->{
            GioHangFragment fragment;
            fragment = (GioHangFragment) fragmentManager.findFragmentByTag("GioHang_Fragment");
            if(fragment!=null){
                fragment.deleteGH(chiTietSanPham);
                chiTietSanPhams.remove(position);
                updateGH(chiTietSanPhams);
                fragment.tinh(chiTietSanPhams);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chiTietSanPhams.size();
    }
    public class GioHangViewHolder extends RecyclerView.ViewHolder {
        private ImageView img,btnDelete;
        private TextView name,sl,total;
        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            sl = itemView.findViewById(R.id.amount);
            total = itemView.findViewById(R.id.total);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
