package com.example.nhom10_duan1.Frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.R;


public class Frag_doimatkhau extends Fragment {
    private Mydbhelper dbHelper;
    private EditText ed_Tentaikhoan;
    private EditText ed_matkhaucu;
    private EditText ed_Matkhaumoi;
    private EditText ed_Nhaplaimatkhaumoi;
    private Button btn_doimatkhau;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragdoimatkhau,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_Tentaikhoan = view.findViewById(R.id.ed_taikhoanfg);
        ed_matkhaucu = view.findViewById(R.id.ed_matkhaucufg);
        ed_Matkhaumoi = view.findViewById(R.id.ed_Nhapmatkhaumoifg);
        ed_Nhaplaimatkhaumoi = view.findViewById(R.id.ed_Nhaplaimatkhaumoifg);
        btn_doimatkhau = view.findViewById(R.id.btn_doimatkhau);
        btn_doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNV = ed_Tentaikhoan.getText().toString();
                String matKhauCu = ed_matkhaucu.getText().toString();
                String matKhauMoi = ed_Matkhaumoi.getText().toString();
                String nhapLaiMatKhauMoi = ed_Nhaplaimatkhaumoi.getText().toString();

                if (matKhauMoi.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (matKhauMoi.equals(nhapLaiMatKhauMoi)) {
                    dbHelper = new Mydbhelper(getContext());

                    if (dbHelper.checkMaNVExists(maNV)) {
                        if (dbHelper.checkMATKHAUExists(matKhauCu)) {
                            dbHelper.updateMatKhau(maNV, matKhauMoi);
                            ed_Tentaikhoan.setText("");
                            ed_matkhaucu.setText("");
                            ed_Matkhaumoi.setText("");
                            ed_Nhaplaimatkhaumoi.setText("");
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Tên tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
