package com.example.nhom10_duan1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom10_duan1.LOPDAO.nhanvienDAO;
import com.example.nhom10_duan1.LOPDTO.nhanvienDTO;


public class DANGNHAP extends AppCompatActivity {
    Button btn_login;
    EditText ed_user, ed_pass;
    Intent intent;
    nhanvienDAO nvdao;
    TextView quenmatkhau;
    CheckBox chk_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        btn_login = findViewById(R.id.login_btn);
        ed_user = findViewById(R.id.ed_user);
        ed_pass = findViewById(R.id.ed_pass);
        chk_remember = findViewById(R.id.chk_remember);
        quenmatkhau = findViewById(R.id.quenmatkhau);
        nvdao = new nhanvienDAO(this);

        nvdao.OPEN();
        if (nvdao.getUserName("admin") < 0) {
            nvdao.ADDNV(new nhanvienDTO("admin", "admin1", "admin", "0987684823"));
        }

        // Đọc Sharepreferences
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        ed_user.setText(preferences.getString("USERNAME", ""));
        ed_pass.setText(preferences.getString("PASSWORD", ""));
        chk_remember.setChecked(preferences.getBoolean("REMEMBER", false));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        quenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DANGNHAP.this, Quenmatkhau.class);
                startActivity(intent);
            }
        });
    }

    public void checkLogin() {
        String usered = ed_user.getText().toString();
        String passed = ed_pass.getText().toString();

        if (usered.isEmpty() || passed.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (nvdao.getlogin(usered, passed) > 0 || (usered.equalsIgnoreCase("admin") && passed.equalsIgnoreCase("admin"))
                    || (usered.equalsIgnoreCase("user") && passed.equalsIgnoreCase("user"))) {
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(usered, passed, chk_remember.isChecked());
                Intent intent = new Intent(DANGNHAP.this, MainActivity.class);
                intent.putExtra("admintion", usered);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Đăng nhập thất bại!\nSai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String user, String pass, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if (!status) {
            // Xóa lưu trữ trước đó
            editor.clear();
        } else {
            // Lưu dữ liệu
            editor.putString("USERNAME", user);
            editor.putString("PASSWORD", pass);
            editor.putBoolean("REMEMBER", status);
        }

        // Lưu lại toàn bộ dữ liệu
        editor.apply();
    }
}