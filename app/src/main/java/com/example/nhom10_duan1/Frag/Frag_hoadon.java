package com.example.nhom10_duan1.Frag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.HoadonActivity;
import com.example.nhom10_duan1.LOPADAPTER.HoadonAdapter;
import com.example.nhom10_duan1.LOPDAO.HoadonDAO;
import com.example.nhom10_duan1.LOPDTO.HoadonDTO;
import com.example.nhom10_duan1.MainActivity;
import com.example.nhom10_duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Frag_hoadon extends Fragment {
    private RecyclerView recyclerView;
    private HoadonAdapter adapter;
    private List<HoadonDTO> hoaDonList;
    private Mydbhelper dbHelper;
    HoadonDAO dao;

    private FloatingActionButton flb_hoadon;
    private FloatingActionButton flb_load;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fraghoadon, container, false);


        recyclerView = view.findViewById(R.id.rcl_hoadon);
        flb_hoadon = view.findViewById(R.id.fbl_adhoadon);
        flb_load = view.findViewById(R.id.fbl_loadlist);
        flb_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRecyclerView();
            }
        });
        dao = new HoadonDAO(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        flb_hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HoadonActivity.class);
                startActivity(intent);
            }
        });
        dbHelper = new Mydbhelper(getActivity());
        hoaDonList = new ArrayList<>();
        adapter = new HoadonAdapter(hoaDonList,getContext());
        recyclerView.setAdapter(adapter);


        updateRecyclerView();

        return view;

    }

    private void updateRecyclerView() {
        hoaDonList.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Hoadon", null);
        List<HoadonDTO> list=new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int soLuong = cursor.getInt(cursor.getColumnIndex("soluong"));
                @SuppressLint("Range") String tenSanPham = cursor.getString(cursor.getColumnIndex("tenSanpham"));
                @SuppressLint("Range") int tongTien = cursor.getInt(cursor.getColumnIndex("Tongtien"));
                @SuppressLint("Range") String ngayMua = cursor.getString(cursor.getColumnIndex("Ngaymua"));
list.add(new HoadonDTO(soLuong,tenSanPham,tongTien,ngayMua));


            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        hoaDonList.addAll(list);



        adapter.notifyDataSetChanged();
    }
    public void onActivityFinish(){
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.detach(this);
        fragmentTransaction.attach(this);
        fragmentTransaction.commit();
    }
}