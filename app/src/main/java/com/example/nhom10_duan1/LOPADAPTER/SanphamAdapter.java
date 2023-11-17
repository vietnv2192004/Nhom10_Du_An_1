package com.example.nhom10_duan1.LOPADAPTER;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nhom10_duan1.LOPDAO.HangDao;
import com.example.nhom10_duan1.LOPDAO.SanphamDao;
import com.example.nhom10_duan1.LOPDTO.HangDTO;
import com.example.nhom10_duan1.LOPDTO.SanphamDTO;
import com.example.nhom10_duan1.R;
import com.example.nhom10_duan1.SPINERADAPTER.HangSpiner;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.SanphamHoder> implements Filterable {
    Context context;
    List<SanphamDTO> list;
    SanphamDao dao;
    int ms, mst;
    ArrayList<HangDTO> loaihang;
    HangDao hangDao;
    List<SanphamDTO> mlistOld;

    public SanphamAdapter(Context context, List<SanphamDTO> list, SanphamDao dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
        this.mlistOld = list;
    }

    @NonNull
    @NotNull
    @Override
    public SanphamHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_sanpham, parent, false);
        return new SanphamHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SanphamHoder holder, int position) {
        SanphamDTO sanpham = list.get(position);
        if (sanpham == null) {
            return;
        } else {
            String tenLoai="";
            try {
                HangDao loaiSachDao = new HangDao(context);
                HangDTO loaiSach = loaiSachDao.getId(String.valueOf(sanpham.getMahang()));
                if (loaiSach != null) {
                    tenLoai = loaiSach.getTenHang();
                } else {
                    tenLoai = "Đã xóa hãng thời trang này";
                }
            } catch (Exception e) {
                tenLoai = "Lỗi xảy ra ãng thời trang này";
                e.printStackTrace();
            }


            holder.tv_msanpham.setText("Mã Sản phẩm: " + sanpham.getMasanpham() + "");
            holder.tv_hang.setText("Hãng thời trang: " + tenLoai);
            holder.tv_tensanpham.setText("Tên sản phẩm: " + sanpham.getTensanpham());
            holder.tv_loai.setText("Loại: " + sanpham.getLoai());
            Locale locale = new Locale("nv", "VN");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
            String tien = numberFormat.format(sanpham.getGiasanpham());
            holder.tv_giasanpham.setText("Giá : " + tien);
        }
        holder.img_dels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete");
                builder.setIcon(R.drawable.ic_dele);
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao = new SanphamDao(context);
                        long kq = dao.DELETES(sanpham);
                        if (kq > 0) {
                            list.clear();
                            list.addAll(dao.GETS());
                            Toast.makeText(context.getApplicationContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            dialog.cancel();
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
        holder.img_edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.custom_edit_sanpham, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(view);
                builder.setTitle("                Sửa Sách");
                EditText ed_teneds = (EditText) view.findViewById(R.id.tensanphamed);
                Spinner spneds = (Spinner) view.findViewById(R.id.spin_hanged);
                EditText ed_giaeds = (EditText) view.findViewById(R.id.giasanphamed);
                EditText ed_loai = (EditText) view.findViewById(R.id.ed_loaied);
                ed_teneds.setText(sanpham.getTensanpham());
                ed_giaeds.setText(Integer.toString(sanpham.getGiasanpham()));
                ed_loai.setText(sanpham.getLoai());
                loaihang = new ArrayList<>();
                hangDao = new HangDao(view.getContext());
                loaihang = (ArrayList<HangDTO>) hangDao.GETLS();
                HangSpiner spiner = new HangSpiner(view.getContext(),    loaihang);
                spneds.setAdapter(spiner);
                spneds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ms = loaihang.get(position).getMaHang();
                        Toast.makeText(view.getContext(), "Chọn: " + loaihang.get(position).getTenHang(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mst = 0;
                for (int i = 0; i < loaihang.size(); i++) {
                    if (sanpham.getMahang() == loaihang.get(i).getMaHang()) {
                        mst = i;
                    }
                }
                spneds.setSelection(mst);
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (sanpham.getTensanpham().equals(ed_teneds.getText().toString()) && sanpham.getGiasanpham() == Integer.parseInt(ed_giaeds.getText().toString())
                                && sanpham.getMahang() == mst) {
                            Toast.makeText(context.getApplicationContext(), "Không Có Gì Thay Đổi \n   Sửa Thất Bại!", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                int giaThue = Integer.parseInt(ed_giaeds.getText().toString());
                                dao = new SanphamDao(context);
                                sanpham.setTensanpham(ed_teneds.getText().toString());
                                sanpham.setLoai(ed_loai.getText().toString());
                                sanpham.setGiasanpham(giaThue);
                                sanpham.setMahang(ms);
                                long kq = dao.UPDATES(sanpham);
                                if (kq > 0) {
                                    list.clear();
                                    list.addAll(dao.GETS());
                                    Toast.makeText(view.getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                                    ed_teneds.setText("");
                                    ed_giaeds.setText("");
                                    ed_loai.setText("");
                                    spneds.setSelection(0);
                                    notifyDataSetChanged();
                                    dialog.cancel();
                                } else {
                                    Toast.makeText(view.getContext(), "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(view.getContext(), "Giá thuê phải là số", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    list = mlistOld;
                } else {
                    List<SanphamDTO> listtv = new ArrayList<>();
                    for (SanphamDTO sanpham : mlistOld) {
                        if (sanpham .getTensanpham().toLowerCase().contains(strSearch.toLowerCase())) {
                            listtv.add(sanpham );
                        }
                        ;
                    }
                    list = listtv;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<SanphamDTO>) results.values;
                notifyDataSetChanged();
            }
        };

    }


    public class SanphamHoder extends RecyclerView.ViewHolder {
        TextView tv_msanpham, tv_hang, tv_tensanpham, tv_giasanpham, tv_loai;
        ImageView img_dels, img_edits;
        ConstraintLayout cns_lays;

        public SanphamHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_msanpham = itemView.findViewById(R.id.tv_masanpham);
            tv_hang = itemView.findViewById(R.id.tv_mahang);
            tv_tensanpham = itemView.findViewById(R.id.tv_tensanpham);
            tv_giasanpham = itemView.findViewById(R.id.tv_gia);
            tv_loai = itemView.findViewById(R.id.tv_loai);
            img_dels = itemView.findViewById(R.id.img_delt);
            img_edits = itemView.findViewById(R.id.img_edit);
            cns_lays = itemView.findViewById(R.id.conss);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition);
            cns_lays.setAnimation(animation);
        }
    }

}
