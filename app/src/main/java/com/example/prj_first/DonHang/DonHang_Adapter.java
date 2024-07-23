package com.example.prj_first.DonHang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj_first.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DonHang_Adapter extends RecyclerView.Adapter<DonHang_Adapter.DonHangViewHolder> {
    List<Item_DonHang> itemDonHangs;
    FragmentManager fragmentManager;

    public DonHang_Adapter(FragmentManager fragmentManager, List<Item_DonHang> itemDonHangs) {
        this.fragmentManager = fragmentManager;
        this.itemDonHangs = itemDonHangs;
    }

    public DonHang_Adapter(List<Item_DonHang> itemDonHangs) {
        this.itemDonHangs = itemDonHangs;
    }

    public void updateDH(List<Item_DonHang> newData)
    {
        this.itemDonHangs = newData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonHangViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        final Item_DonHang itemDonHang = itemDonHangs.get(position);
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = currentDate.format(dateTimeFormatter);
        holder.OrderCreateDate.setText(formattedDate);
        holder.Oderstatus.setText("Đơn hàng mới");
        holder.Odercustomer.setText(itemDonHang.getNamekh());
        holder.Oderamount.setText(itemDonHang.getSoluong());
        holder.Odertotal.setText(itemDonHang.getTongtien());
        holder.btnCancelOrder.setOnClickListener(v->{
            DonHangFragment fragment;
            fragment = (DonHangFragment) fragmentManager.findFragmentByTag("DonHang_Fragment");
            if(fragment!=null) {
                fragment.deleteDH(itemDonHang);
                itemDonHangs.remove(position);
                updateDH(itemDonHangs);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemDonHangs.size();
    }

    public class DonHangViewHolder extends RecyclerView.ViewHolder {
        private Button btnCancelOrder;
        private TextView OrderCreateDate,Oderstatus,Odercustomer,Oderamount,Odertotal;
        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            btnCancelOrder = itemView.findViewById(R.id.btnCancelOder);
            Oderamount = itemView.findViewById(R.id.Oderamount);
            OrderCreateDate = itemView.findViewById(R.id.Odercreatedate);
            Oderstatus = itemView.findViewById(R.id.Oderstatus);
            Odercustomer = itemView.findViewById(R.id.Odercustomer);
            Odertotal = itemView.findViewById(R.id.Odertotal);
        }
    }
}
