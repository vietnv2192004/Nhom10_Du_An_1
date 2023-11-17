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


import com.example.nhom10_duan1.LOPDAO.HangDao;
import com.example.nhom10_duan1.LOPDTO.HangDTO;
import com.example.nhom10_duan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HangAdapter  extends RecyclerView.Adapter<HangAdapter.Hanghoder> {
    Context context;
    List<HangDTO> hangList;
   HangDao hangDao;

    public HangAdapter(Context context, List<HangDTO> hangList, HangDao hangDao) {
        this.context = context;
        this.hangList = hangList;
        this.hangDao = hangDao;
        // commit

    }

    @NonNull
    @NotNull
    @Override
    public Hanghoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_hang, parent, false);
        return new Hanghoder(view);
    }

    // ok
    @Override
    public void onBindViewHolder(@NonNull @NotNull Hanghoder holderls, int position) {
        HangDTO hangDTO = hangList.get(position);
        if (hangDTO == null) {
            return;
        } else {
            holderls.tv_IDhang.setText("Mã Loại Hãng: " + hangDTO.getMaHang() + "");
            holderls.tv_tenhang.setText("Tên Loại Hãng: " + hangDTO.getTenHang());

        }

        holderls.imgdel.setOnClickListener(new View.OnClickListener() {
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
                        hangDao = new HangDao(context);
                        long kq = hangDao.DELETELS(hangDTO);
                        if (kq > 0) {
                            hangList.clear();
                            hangList.addAll(hangDao.GETLS());
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
        holderls.imgdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.custom_edit_hang, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(view);
                builder.setTitle("                Sửa Hãng");
                EditText ed_loasc = (EditText) view.findViewById(R.id.ed_edithang);

                ed_loasc.setText(hangDTO.getTenHang());

                builder.setCancelable(true);
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (hangDTO.getTenHang().equals(ed_loasc.getText().toString()) ) {
                            Toast.makeText(context.getApplicationContext(), "Không Có Gì Thay Đổi \n   Sửa Thất Bại!", Toast.LENGTH_SHORT).show();

                        } else {
                            hangDao = new HangDao(context);
                          hangDTO.setTenHang(ed_loasc.getText().toString());

                            long kq = hangDao.UPDATELS(hangDTO);
                            if (kq > 0) {
                                hangList.clear();
                                hangList.addAll(hangDao.GETLS());
                                notifyDataSetChanged();
                                ed_loasc.setText("");

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
    public void updateList(List<HangDTO> updatedHangList) {
        hangList = updatedHangList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (hangList != null) {
            return hangList.size();
        }
        return 0;
    }

    public class Hanghoder extends RecyclerView.ViewHolder {
        TextView tv_IDhang, tv_tenhang;
        ImageView imgdel, imgdit;
        CardView cardView;

        public Hanghoder(@NonNull @NotNull View view) {
            super(view);
            tv_IDhang = (TextView) view.findViewById(R.id.tv_id_hang);
            tv_tenhang = (TextView) view.findViewById(R.id.tv_ten_hang);
            imgdel = (ImageView) view.findViewById(R.id.imgdells);
            imgdit = (ImageView) view.findViewById(R.id.imgditls);
            cardView = view.findViewById(R.id.cns_ls);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition);
            cardView.setAnimation(animation);
        }

    }
}
