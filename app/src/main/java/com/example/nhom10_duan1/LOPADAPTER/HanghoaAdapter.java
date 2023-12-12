package com.example.nhom10_duan1.LOPADAPTER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.LOPDTO.HanghoaDTO;
import com.example.nhom10_duan1.R;

import java.util.List;

public class HanghoaAdapter extends RecyclerView.Adapter<HanghoaAdapter.ViewHolder> {
    private List<HanghoaDTO> hanghoaList;
    private Context context;
    private OnItemClickListener listener;

    public HanghoaAdapter(List<HanghoaDTO> hanghoaList, Context context) {
        this.hanghoaList = hanghoaList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_hoanghoa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HanghoaDTO hanghoa = hanghoaList.get(position);
        String tenSanpham = hanghoa.getTenHanghoa();
        int gia = getGiaFromSanphamTable(tenSanpham);
        holder.tvTenSanPham.setText(hanghoa.getTenHanghoa());
        holder.tvSoLuong.setText(String.valueOf(hanghoa.getSoLuong()));
        holder.tv_tien.setText(String.valueOf(hanghoa.getSoLuong() * gia));
    }

    @Override
    public int getItemCount() {
        return hanghoaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenSanPham;
        private TextView tvSoLuong;
        private TextView tv_tien;
        private Button xoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSanPham = itemView.findViewById(R.id.tv_tensanpham);
            tvSoLuong = itemView.findViewById(R.id.tv_soluong);
            tv_tien = itemView.findViewById(R.id.tv_tien);
            xoa = itemView.findViewById(R.id.img_delt);

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        deleteItem(position);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    private void deleteItem(int position) {
        if (position >= 0 && position < hanghoaList.size()) {
            HanghoaDTO deletedHanghoa = hanghoaList.get(position);
            hanghoaList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, hanghoaList.size());

            Mydbhelper dbHelper = new Mydbhelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.beginTransaction();
            try {
                int deletedRows = db.delete("Hanghoa", "maHanghoa = ?", new String[]{String.valueOf(deletedHanghoa.getId())});

                if (deletedRows > 0) {
                    db.setTransactionSuccessful();
                    // Xử lý sau khi xóa thành công
                } else {
                    // Xử lý khi không xóa được hàng hóa từ cơ sở dữ liệu
                }
            } finally {
                db.endTransaction();
            }

            db.close();
        }
    }

    private int getGiaFromSanphamTable(String tenSanpham) {
        Mydbhelper dbHelper = new Mydbhelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT gia FROM Sanpham WHERE tenSanpham = ?", new String[]{tenSanpham});
        int gia = 0;
        if (cursor.moveToFirst()) {
            gia = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return gia;
    }
}