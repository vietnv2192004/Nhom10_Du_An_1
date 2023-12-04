package com.example.nhom10_duan1.LOPADAPTER;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_duan1.LOPDTO.HoadonDTO;
import com.example.nhom10_duan1.R;

import java.util.List;

public class HoadonAdapter extends RecyclerView.Adapter<HoadonAdapter.ViewHolder> {
    private List<HoadonDTO> hoaDonList;


    public HoadonAdapter(List<HoadonDTO> hoaDonList) {
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_hoadon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoadonDTO hoadonDTO = hoaDonList.get(position);


        holder.soLuongTextView.setText(String.valueOf(hoadonDTO.getSoLuong()));
        holder.tenSanPhamTextView.setText(hoadonDTO.getTenSanpham());
        holder.tongTienTextView.setText(String.valueOf(hoadonDTO.getTongTien()));
        holder.ngayMuaTextView.setText(hoadonDTO.getNgayMua());
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView soLuongTextView;
        private TextView tenSanPhamTextView;
        private TextView tongTienTextView;
        private TextView ngayMuaTextView;
 private ImageView ingxoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            soLuongTextView = itemView.findViewById(R.id.soLuongTextView);
            tenSanPhamTextView = itemView.findViewById(R.id.tenSanPhamTextView);
            tongTienTextView = itemView.findViewById(R.id.tongTienTextView);
            ngayMuaTextView = itemView.findViewById(R.id.ngayMuaTextView);
ingxoa=itemView.findViewById(R.id.imgxoa);
        }
    }
}

