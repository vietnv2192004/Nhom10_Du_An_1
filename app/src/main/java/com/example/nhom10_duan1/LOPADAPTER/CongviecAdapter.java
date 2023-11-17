package com.example.nhom10_duan1.LOPADAPTER;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nhom10_duan1.LOPDAO.CongviecDAO;
import com.example.nhom10_duan1.LOPDTO.CongviecDTO;
import com.example.nhom10_duan1.R;

import java.util.List;

public class CongviecAdapter extends RecyclerView.Adapter<CongviecAdapter.CongviecViewHolder> {
    Context context;
    List<CongviecDTO> CongviecList;
    CongviecDAO congviecDao;


    public CongviecAdapter(Context context, List<CongviecDTO> CVList, CongviecDAO CVDao) {
        this.context = context;
        this.CongviecList = CVList;
        this.congviecDao = CVDao;
    }

    @NonNull
    @Override
    public CongviecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_congviec, parent, false);
        return new CongviecViewHolder(view);
    }

    // ok
    @Override
    public void onBindViewHolder(@NonNull CongviecViewHolder holder, int position) {
        CongviecDTO congviecDTO = CongviecList.get(position);
        if (congviecDTO == null) {
            return;
        } else {
            holder.tv_IDCongviec.setText("Mã Công Việc: " + congviecDTO.getMaCongviec() + "");
            holder.tv_tennv.setText("Tên Nhân Viên: " + congviecDTO.getTenNhanvien());
            holder.tv_sogio.setText("Số giờ làm việc: " + congviecDTO.getSogio()+ "Giờ");
            holder.tv_luong.setText("Lương: " + congviecDTO.getLuong());
        }

        holder.imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete");
                builder.setIcon(R.drawable.ic_dele);
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        congviecDao = new CongviecDAO(context);
                        long kq = congviecDao.DELETELS(congviecDTO);
                        if (kq > 0) {
                            CongviecList.clear();
                            CongviecList.addAll(congviecDao.GETLS());
                            // load dữ liệu
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context.getApplicationContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();

                        }
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
        holder.imgdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.custom_edit_congviec, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(view);
                builder.setTitle("Sửa Công việc");
                EditText ed_tennv = view.findViewById(R.id.ed_edittennhanvien1);
                EditText ed_gio = view.findViewById(R.id.ed_editgio1);
                EditText ed_luong = view.findViewById(R.id.ed_editluong1);

                ed_tennv.setText(congviecDTO.getTenNhanvien());
                ed_gio.setText(congviecDTO.getSogio());
                ed_luong.setText(congviecDTO.getLuong());
                builder.setCancelable(true);
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String gio = ed_gio.getText().toString();
                        String luong = ed_luong.getText().toString();

                        if (!isValidNumber(gio) || !isValidNumber(luong)) {
                            Toast.makeText(context.getApplicationContext(), "Vui lòng nhập số giờ và lương hợp lệ!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (congviecDTO.getTenNhanvien().equals(ed_tennv.getText().toString()) &&
                                congviecDTO.getSogio().equals(gio) &&
                                congviecDTO.getLuong().equals(luong)) {
                            Toast.makeText(context.getApplicationContext(), "Không có gì thay đổi. Sửa thất bại!", Toast.LENGTH_SHORT).show();
                        } else {
                            congviecDao = new CongviecDAO(context);
                            congviecDTO.setTenNhanvien(ed_tennv.getText().toString());
                            congviecDTO.setSogio(gio);
                            congviecDTO.setLuong(luong);

                            long kq = congviecDao.UPDATELS(congviecDTO);
                            if (kq > 0) {
                                CongviecList.clear();
                                CongviecList.addAll(congviecDao.GETLS());
                                notifyDataSetChanged();
                                ed_tennv.setText("");
                                ed_gio.setText("");
                                ed_luong.setText("");
                                dialog.dismiss();
                                Toast.makeText(context.getApplicationContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context.getApplicationContext(), "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });



    }
    public void updateList(List<CongviecDTO> updatedcvList) {
        CongviecList = updatedcvList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (CongviecList != null) {
            return CongviecList.size();
        }
        return 0;
    }

    public class CongviecViewHolder extends RecyclerView.ViewHolder {
        TextView tv_IDCongviec, tv_tennv, tv_sogio, tv_luong;
        ImageView imgdel, imgdit;
        CardView cardView;

        public CongviecViewHolder(@NonNull View view) {
            super(view);
            tv_IDCongviec = view.findViewById(R.id.tv_id_congviec);
            tv_tennv = view.findViewById(R.id.tv_ten_Nhanvien);
            tv_sogio = view.findViewById(R.id.tv_sogio);
            tv_luong = view.findViewById(R.id.tv_luong);
            imgdel = view.findViewById(R.id.imgdells);
            imgdit = view.findViewById(R.id.imgditls);
            cardView = view.findViewById(R.id.cns_ls);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition);
            cardView.setAnimation(animation);
        }
    }
    private boolean isValidNumber(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}