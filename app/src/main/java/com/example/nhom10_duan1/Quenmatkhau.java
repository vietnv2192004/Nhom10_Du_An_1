package com.example.nhom10_duan1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom10_duan1.DBHELPER.Mydbhelper;


public class Quenmatkhau extends AppCompatActivity {

    private Mydbhelper dbHelper;
    private EditText ed_Tentaikhoan;
    private EditText ed_sodienthoai;
    private EditText ed_Matkhau;
    private EditText ed_Nhaplaimatkhau;
    private Button btn_taolaimatkhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quenmatkhau);

        dbHelper = new Mydbhelper(this);
        ed_Tentaikhoan = findViewById(R.id.ed_taikhoan);
        ed_sodienthoai = findViewById(R.id.ed_sodienthoai);
        ed_Matkhau = findViewById(R.id.ed_Nhapmatkhaumoi);
        ed_Nhaplaimatkhau = findViewById(R.id.ed_Nhaplaimatkhaumoi);
        btn_taolaimatkhau = findViewById(R.id.btn_Tao);

        btn_taolaimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNV = ed_Tentaikhoan.getText().toString();
                String soDT = ed_sodienthoai.getText().toString();
                String matKhauMoi = ed_Matkhau.getText().toString();
                String nhapLaiMatKhau = ed_Nhaplaimatkhau.getText().toString();

                if (dbHelper.checkMaNVExists(maNV) && dbHelper.checkSoDTExists(soDT)) {

                    if (!maNV.isEmpty() && !soDT.isEmpty() && !matKhauMoi.isEmpty() && !nhapLaiMatKhau.isEmpty()) {
                        if (TextUtils.isDigitsOnly(soDT)) {
                            if (matKhauMoi.equals(nhapLaiMatKhau)) {
                                dbHelper.updateMatKhau(maNV, matKhauMoi);
                                Toast.makeText(Quenmatkhau.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                ed_Tentaikhoan.setText("");
                                ed_sodienthoai.setText("");
                                ed_Matkhau.setText("");
                                ed_Nhaplaimatkhau.setText("");

                            } else {
                                Toast.makeText(Quenmatkhau.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Quenmatkhau.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Quenmatkhau.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Quenmatkhau.this, "Tên đăng nhập hoặc số điện thoại không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}