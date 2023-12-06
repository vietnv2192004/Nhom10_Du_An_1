package com.example.nhom10_duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nhom10_duan1.Frag.Frag_doanhthu;
import com.example.nhom10_duan1.Frag.Frag_doimatkhau;
import com.example.nhom10_duan1.Frag.Frag_hang;
import com.example.nhom10_duan1.Frag.Frag_hoadon;
import com.example.nhom10_duan1.Frag.Frag_nhanvien;
import com.example.nhom10_duan1.Frag.Frag_quanlyduoidung;
import com.example.nhom10_duan1.Frag.Frag_sanpham;
import com.example.nhom10_duan1.Frag.Frag_top;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout layout_chinh_drawer;
    Toolbar mToolbar;
    NavigationView main_nav_view;

    Frag_doanhthu fragDoanhthu;
    Frag_doimatkhau fragDoimatkhau;
    Frag_hang fragHang;
    Frag_hoadon fragHoadon;
    Frag_nhanvien fragNhanvien;
    Frag_quanlyduoidung fragQuanlyduoidung;
    Frag_sanpham fragSanpham;
    Frag_top fragTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout_chinh_drawer = findViewById(R.id.layout_chinh);
        mToolbar = findViewById(R.id.mToolbar);
        main_nav_view = findViewById(R.id.main_nav_item);


        //gắn toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//ẩn tên ứng dụng
        //thiết lập cho drawer Navigation
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, layout_chinh_drawer,
                mToolbar,R.string.open,R.string.close);

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        layout_chinh_drawer.addDrawerListener(drawerToggle);
        Intent intent = getIntent();
        String loggedInUser = intent.getStringExtra("admintion");
        Menu menu = main_nav_view.getMenu();
        MenuItem quanLynguoidung = menu.findItem(R.id.nav_nguoidung);
        MenuItem quanLynhanvien = menu.findItem(R.id.quanlynhanvien);
        if (!"admin".equals(loggedInUser)) {
            quanLynguoidung.setVisible(false);
            quanLynhanvien.setVisible(false);
        }
        //thiết lập sự kiện click
        //khi vào ứng dụng cho hiển thị luôn fragment home
        fragDoanhthu = new Frag_doanhthu();
        fragDoimatkhau = new Frag_doimatkhau();
        fragHang = new Frag_hang();
        fragHoadon = new Frag_hoadon();
        fragNhanvien = new Frag_nhanvien();
        fragQuanlyduoidung= new Frag_quanlyduoidung();
        fragSanpham = new Frag_sanpham();
        fragTop = new Frag_top();

        getSupportFragmentManager().beginTransaction().add(R.id.frag_container01, fragHoadon).commit();

        main_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()== R.id.nav_home){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container01, fragHoadon).commit();
                    getSupportActionBar().setTitle(item.getTitle());

                }else if(item.getItemId() == R.id.nav_sanpham){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container01, fragSanpham).commit();
                    //hiển thị tiêu đề:
                    getSupportActionBar().setTitle(item.getTitle());
                }
                else if(item.getItemId() == R.id.nav_hang){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container01, fragHang).commit();
                    //hiển thị tiêu đề:
                    getSupportActionBar().setTitle(item.getTitle());
                }
                else if(item.getItemId() == R.id.quanlynhanvien){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container01, fragNhanvien).commit();
                    //hiển thị tiêu đề:
                    getSupportActionBar().setTitle(item.getTitle());
                }
                else if(item.getItemId() == R.id.nav_Topbanchay){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container01, fragTop).commit();
                    //hiển thị tiêu đề:
                    getSupportActionBar().setTitle(item.getTitle());
                }
                else if(item.getItemId() == R.id.nav_doangthu){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container01, fragDoanhthu).commit();
                    //hiển thị tiêu đề:
                    getSupportActionBar().setTitle(item.getTitle());
                }
                else if(item.getItemId() == R.id.nav_nguoidung){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container01, fragQuanlyduoidung).commit();
                    //hiển thị tiêu đề:
                    getSupportActionBar().setTitle(item.getTitle());
                }
                else if(item.getItemId() == R.id.nav_doimatkhau){

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container01, fragDoimatkhau).commit();
                    //hiển thị tiêu đề:
                    getSupportActionBar().setTitle(item.getTitle());

                }    else if (item.getItemId() == R.id.nav_dangxuat) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Xác nhận đăng xuất");
                    builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Xóa thông tin người dùng đã lưu trong SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("TenTaiKhoan", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();

                            // Chuyển về màn hình đăng nhập
                            Intent intent = new Intent(MainActivity.this, DANGNHAP.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    //mặc định trả về fragment home
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_container01, fragHoadon).commit();
                    getSupportActionBar().setTitle(item.getTitle());
                }

                layout_chinh_drawer.close();
                return  true;
            }
        });
    }
public static int getId(){
        return R.id.frag_container01;
}
}