package com.example.prj_first.KhachHang;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj_first.R;

import java.util.List;

public class KhachHang_Adapter extends RecyclerView.Adapter<KhachHang_Adapter.KhachHangViewHolder> {
    private List<Item_KhachHang> list ;
    private clickItemListener iclickItemListener;
    public interface clickItemListener{
        void onClickButtonChosen(Item_KhachHang itemKhachHang);
        void onClickItem(Item_KhachHang itemKhachHang);
    }

    public KhachHang_Adapter(List<Item_KhachHang> list) {
        this.list = list;
    }
    public void updateAdapter(List<Item_KhachHang> newData)
    {
        this.list = newData;
        notifyDataSetChanged();
    }
    public KhachHang_Adapter(List<Item_KhachHang> list, clickItemListener listener ) {
        this.list = list;
        this.iclickItemListener = listener;

    }
    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khachhang,parent,false);
        return new KhachHangViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, int position) {
        final Item_KhachHang itemKhachHang = list.get(position);
        if(itemKhachHang==null)
            return;
        holder.tenkh.setText(itemKhachHang.getTenkh());
        holder.diachikh.setText(itemKhachHang.getDiachikh());
        holder.btnchosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iclickItemListener.onClickButtonChosen(itemKhachHang);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iclickItemListener.onClickItem(itemKhachHang);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        return 0;
    }
    public class KhachHangViewHolder extends RecyclerView.ViewHolder{
        private TextView tenkh,diachikh;
        private Button btnchosen;
        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tenkh = itemView.findViewById(R.id.tenkh);
            tenkh.setTypeface(tenkh.getTypeface(), Typeface.BOLD);
            diachikh = itemView.findViewById(R.id.diachikh);
            btnchosen = itemView.findViewById(R.id.btnChoose);
        }
    }
}
