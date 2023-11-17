package com.example.nhom10_duan1.SPINERADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.nhom10_duan1.LOPDTO.SanphamDTO;
import com.example.nhom10_duan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SanphamSpiner extends ArrayAdapter<SanphamDTO> {
    Context context;
    ArrayList<SanphamDTO> list;
    TextView tvTensanpham;

    public SanphamSpiner(@NonNull @NotNull Context context, ArrayList<SanphamDTO> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spiner_sanpham, null);
        }
        SanphamDTO sanpham = list.get(position);
        if (sanpham != null) {
            tvTensanpham = view.findViewById(R.id.tv_spiner_sanpham);
            tvTensanpham.setText(sanpham.getTensanpham());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spiner_sanpham, null);
        }
        SanphamDTO sanpham = list.get(position);
        if (sanpham != null) {
            tvTensanpham = view.findViewById(R.id.tv_spiner_sanpham);
            tvTensanpham.setText(sanpham.getTensanpham());
        }
        return view;
    }
}
