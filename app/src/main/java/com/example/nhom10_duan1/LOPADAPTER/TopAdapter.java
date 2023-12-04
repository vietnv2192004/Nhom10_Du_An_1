package com.example.nhom10_duan1.LOPADAPTER;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_duan1.LOPDTO.TopDTO;
import com.example.nhom10_duan1.R;

import java.util.List;

public class TopAdapter  extends RecyclerView.Adapter<TopAdapter.ViewHolder> {
    private List<TopDTO> danhSachSanPham;

    public TopAdapter(List<TopDTO> danhSachSanPham) {
        this.danhSachSanPham = danhSachSanPham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_top, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopDTO sanPham = danhSachSanPham.get(position);
        holder.txtTenSanPham.setText(sanPham.getTenSanPham());
        holder.txtSoLuong.setText(String.valueOf(sanPham.getTongSoLuong()));
    }

    @Override
    public int getItemCount() {
        return danhSachSanPham.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSanPham;
        TextView txtSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSanPham = itemView.findViewById(R.id.txt_product_name);
            txtSoLuong = itemView.findViewById(R.id.txt_quantity);
        }
    }
}