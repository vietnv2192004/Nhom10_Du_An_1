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


import com.example.nhom10_duan1.LOPADAPTER.HangAdapter;
import com.example.nhom10_duan1.LOPDAO.HangDao;
import com.example.nhom10_duan1.LOPDTO.HangDTO;
import com.example.nhom10_duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Frag_hang extends Fragment {
    RecyclerView rcl_hang;

    HangDao hangdao;
    HangAdapter adapter;
    FloatingActionButton flb_addhang;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fraghang, null);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_hang = view.findViewById(R.id.rcl_hang);
        flb_addhang = view.findViewById(R.id.flb_ls);
        hangdao = new HangDao(getActivity());
        RecyclerView.LayoutManager lymanage = new LinearLayoutManager(getActivity());
        rcl_hang.setLayoutManager(lymanage);

        // Tải danh sách ban đầu
        List<HangDTO> hangList = hangdao.GETLS();
        adapter = new HangAdapter(getActivity(), hangList, hangdao);
        rcl_hang.setAdapter(adapter);

        flb_addhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.custom_add_hang, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view1);
                EditText ed_ls = (EditText) view1.findViewById(R.id.ed_edithang);

                builder.setTitle("                Thêm Hãng");
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HangDTO hangDTO = new HangDTO();
                        hangDTO.setTenHang(ed_ls.getText().toString());

                        long kq = hangdao.ADDLS(hangDTO);
                        if (kq > 0) {
                            ed_ls.setText("");
                            Toast.makeText(getActivity(), "Thêm Hãng Thành Công", Toast.LENGTH_SHORT).show();
                            // Tải lại danh sách sau khi thêm
                            List<HangDTO> updatedHangList = hangdao.GETLS();
                            adapter.updateList(updatedHangList);
                        } else {
                            Toast.makeText(getActivity(), "Tên Hãng Trùng Lặp \n Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
}