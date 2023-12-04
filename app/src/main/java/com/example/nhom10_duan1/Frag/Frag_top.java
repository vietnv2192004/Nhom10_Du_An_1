package com.example.nhom10_duan1.Frag;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.LOPADAPTER.TopAdapter;
import com.example.nhom10_duan1.LOPDTO.TopDTO;
import com.example.nhom10_duan1.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Frag_top extends Fragment {
    private RecyclerView rcl_topbanchay;
    private List<TopDTO> danhSachSanPham;
    private TopAdapter productAdapter;
    private Mydbhelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragtopbanchay, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_topbanchay = view.findViewById(R.id.rcl_topbanchay);

        // Khởi tạo đối tượng dbHelper
        dbHelper = new Mydbhelper(requireContext());

        // Lấy dữ liệu sản phẩm từ bảng Hanghoa và tính toán tổng số lượng
        layDuLieuSanPham();

        // Sắp xếp danh sách sản phẩm theo tổng số lượng giảm dần
        sapXepDanhSachSanPham();

        // Tạo đối tượng adapter và đặt nó vào RecyclerView
        productAdapter = new TopAdapter(danhSachSanPham);
        rcl_topbanchay.setLayoutManager(new LinearLayoutManager(requireContext()));
        rcl_topbanchay.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    private void layDuLieuSanPham() {
        danhSachSanPham = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenHanghoa, soluong FROM Hanghoa", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tenHanghoa = cursor.getString(cursor.getColumnIndex("tenHanghoa"));
                @SuppressLint("Range") int soluong = cursor.getInt(cursor.getColumnIndex("soluong"));

                // Tìm sản phẩm trong danh sách
                TopDTO sanPham = null;
                for (TopDTO item : danhSachSanPham) {
                    if (item.getTenSanPham().equals(tenHanghoa)) {
                        sanPham = item;
                        break;
                    }
                }

                // Nếu sản phẩm đã tồn tại trong danh sách, cập nhật tổng số lượng
                if (sanPham != null) {
                    sanPham.setTongSoLuong(sanPham.getTongSoLuong() + soluong);
                } else {
                    // Nếu sản phẩm chưa tồn tại, thêm mới vào danh sách
                    danhSachSanPham.add(new TopDTO(tenHanghoa, soluong));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void sapXepDanhSachSanPham() {
        Collections.sort(danhSachSanPham, new Comparator<TopDTO>() {
            @Override
            public int compare(TopDTO item1, TopDTO item2) {
                return Integer.compare(item2.getTongSoLuong(), item1.getTongSoLuong());
            }
        });
    }
}
