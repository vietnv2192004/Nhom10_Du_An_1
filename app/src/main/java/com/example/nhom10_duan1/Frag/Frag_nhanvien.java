package com.example.nhom10_duan1.Frag;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nhom10_duan1.LOPADAPTER.CongviecAdapter;
import com.example.nhom10_duan1.LOPDAO.CongviecDAO;
import com.example.nhom10_duan1.LOPDTO.CongviecDTO;
import com.example.nhom10_duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Frag_nhanvien extends Fragment {
    RecyclerView rcl_congviec;

    CongviecDAO congviecdao;
    CongviecAdapter adapter;
    FloatingActionButton flb_addcv;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragnhanvien, null);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_congviec = view.findViewById(R.id.rcl_congviec);
        flb_addcv = view.findViewById(R.id.flb_congviec);
        congviecdao = new CongviecDAO(getActivity());
        RecyclerView.LayoutManager lymanage = new LinearLayoutManager(getActivity());
        rcl_congviec.setLayoutManager(lymanage);

        // Tải danh sách ban đầu
        List<CongviecDTO> congviecList = congviecdao.GETLS();
        adapter = new CongviecAdapter(getActivity(), congviecList, congviecdao);
        rcl_congviec.setAdapter(adapter);

        flb_addcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.custom_add_congviec, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view1);
                EditText ed_tennv = (EditText) view1.findViewById(R.id.ed_editTennhanvien);
                EditText ed_gio = (EditText) view1.findViewById(R.id.ed_editsolam);
                EditText ed_luong = (EditText) view1.findViewById(R.id.ed_editluong);
                builder.setTitle("                Thêm Công việc");
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CongviecDTO congviecDTO = new CongviecDTO();
                        congviecDTO.setTenNhanvien(ed_tennv.getText().toString());
                        String gio = ed_gio.getText().toString();
                        String luong = ed_luong.getText().toString();

                        // Check if gio and luong contain only numbers
                        if (isNumeric(gio) && isNumeric(luong)) {
                            congviecDTO.setSogio(gio);
                            congviecDTO.setLuong(luong);

                            long kq = congviecdao.ADDLS(congviecDTO);
                            if (kq > 0) {
                                ed_tennv.setText("");
                                ed_gio.setText("");
                                ed_luong.setText("");
                                Toast.makeText(getActivity(), "Thêm Hãng Thành Công", Toast.LENGTH_SHORT).show();
                                // Tải lại danh sách sau khi thêm
                                List<CongviecDTO> updatedHangList = congviecdao.GETLS();
                                adapter.updateList(updatedHangList);
                            } else {
                                Toast.makeText(getActivity(), "Tên Hãng Trùng Lặp \n Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Giờ và lương phải là số", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

// Helper meth
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

}