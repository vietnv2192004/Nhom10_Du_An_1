package com.example.nhom10_duan1.Frag;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.nhom10_duan1.LOPADAPTER.nhanvienAdapter;
import com.example.nhom10_duan1.LOPDAO.nhanvienDAO;
import com.example.nhom10_duan1.LOPDTO.nhanvienDTO;
import com.example.nhom10_duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Frag_quanlyduoidung extends Fragment {
    nhanvienDAO dao;
    EditText edUser, edPass, edEnPass, edhoten,edsodt;
    ListView lv_nv;
    FloatingActionButton flb_nv;
    nhanvienAdapter adapter;
    List<nhanvienDTO> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragtaikhoan,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_nv = view.findViewById(R.id.lisv_taoidnv);
        dao = new nhanvienDAO(getActivity());
        dao.OPEN();
        list = new ArrayList<>();
        list = dao.GETNV();
        adapter = new nhanvienAdapter(list, getContext());
        lv_nv.setAdapter(adapter);

        flb_nv = view.findViewById(R.id.flonv);
        flb_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.custom_taonv, null);
                builder.setView(view);
                dao = new nhanvienDAO(getActivity());
                edUser = view.findViewById(R.id.edUser);
                edhoten = view.findViewById(R.id.edHoTen);
                edPass = view.findViewById(R.id.edPass);
                edEnPass = view.findViewById(R.id.edRePass);
                edsodt=view.findViewById(R.id.edRSodienthoai);
                builder.setTitle("                Tạo Tài Khoản");
                builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nhanvienDTO nhanVien = new nhanvienDTO();
                        nhanVien.setMaNV(edUser.getText().toString());
                        nhanVien.setHoTen(edhoten.getText().toString());
                        nhanVien.setMaKhau(edPass.getText().toString());
                        nhanVien.setSdt(edsodt.getText().toString());
                        if (checkrong() > 0) {
                            long kq = dao.ADDNV(nhanVien);
                            if (kq > 0) {
                                Toast.makeText(getActivity(), "Tạo Tài khoản thành công", Toast.LENGTH_SHORT).show();
                                edUser.setText("");
                                edhoten.setText("");
                                edPass.setText("");
                                edEnPass.setText("");
                                edsodt.setText("");
                                list.clear();
                                list.addAll(dao.GETNV());
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });

    }


    public int checkrong() {
        int check = 1;
        if (edUser.getText().length() == 0 || edhoten.getText().length() == 0 ||
                edPass.getText().length() == 0 || edEnPass.getText().length() == 0 || edsodt.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edPass.getText().toString();
            String rePass = edEnPass.getText().toString();
            String soDienThoai = edsodt.getText().toString();

            if (!pass.equals(rePass)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            } else if (!isNumeric(soDienThoai) || soDienThoai.length() < 10) {
                Toast.makeText(getContext(), "Số điện thoại phải là số và lớn hơn 9 kí tự", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

}
