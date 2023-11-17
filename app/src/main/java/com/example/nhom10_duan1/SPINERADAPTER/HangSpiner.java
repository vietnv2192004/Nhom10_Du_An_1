package com.example.nhom10_duan1.SPINERADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.nhom10_duan1.LOPDTO.HangDTO;
import com.example.nhom10_duan1.R;

import java.util.ArrayList;

public class HangSpiner extends BaseAdapter {
    Context context;
    ArrayList<HangDTO> list;

    public HangSpiner(Context context, ArrayList<HangDTO> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_spiner_hang, parent, false);
            holder = new ViewHolder();
            holder.tvTenHang = convertView.findViewById(R.id.tv_ten);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HangDTO hangDTO = list.get(position);
        if (hangDTO != null) {
            holder.tvTenHang.setText(hangDTO.getTenHang());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTenHang;
    }
}