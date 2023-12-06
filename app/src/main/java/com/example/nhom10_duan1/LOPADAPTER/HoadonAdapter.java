package com.example.nhom10_duan1.LOPADAPTER;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_duan1.LOPDAO.HoadonDAO;
import com.example.nhom10_duan1.LOPDAO.nhanvienDAO;
import com.example.nhom10_duan1.LOPDTO.HoadonDTO;
import com.example.nhom10_duan1.R;

import java.util.List;

public class HoadonAdapter extends RecyclerView.Adapter<HoadonAdapter.ViewHolder> {
    private List<HoadonDTO> hoaDonList;
    HoadonDAO hoadonDAO;
    Context context;

    public HoadonAdapter(List<HoadonDTO> hoaDonList, Context context) {
        this.hoaDonList = hoaDonList;
        this.context = context;

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
        holder.ingxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setIcon(R.drawable.ic_dele);
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         hoadonDAO = new HoadonDAO(context);
                        hoadonDAO.OPEN();
                        int kq = hoadonDAO.DELETE(String.valueOf(hoadonDTO.getMahoadon()));
                        if (kq > 0) {
                            hoaDonList.clear();
                            hoaDonList.addAll(hoadonDAO.GETS());
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Xóa Nhân Viên Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa Nhân Viên Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                        hoadonDAO.Close();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            }
        });
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

