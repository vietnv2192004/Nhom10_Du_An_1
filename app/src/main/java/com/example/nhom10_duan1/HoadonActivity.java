package com.example.nhom10_duan1;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.Frag.Frag_hoadon;
import com.example.nhom10_duan1.LOPADAPTER.HanghoaAdapter;
import com.example.nhom10_duan1.LOPDAO.HoadonDAO;
import com.example.nhom10_duan1.LOPDTO.HanghoaDTO;
import com.example.nhom10_duan1.LOPDTO.HoadonDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HoadonActivity extends AppCompatActivity {
    private RecyclerView rcl_hanghoa;
    Frag_hoadon fragHoadon;
    private Button add_hanghoa;
    private Button btn_luuhoadon;
    private TextView tv_soluonghanghoa, tv_tongtien, tv_ngaymua;
    private RecyclerView.LayoutManager layoutManager;
    private HanghoaAdapter hanghoaAdapter;
    private List<HanghoaDTO> hanghoaList;
    private HashMap<String, Integer> hanghoaMap;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);
        Button huy = findViewById(R.id.btn_huy);
        fragmentManager = getSupportFragmentManager();
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rcl_hanghoa = findViewById(R.id.rcv_hanghoa1);
        add_hanghoa = findViewById(R.id.btn_themhanghoa);
        btn_luuhoadon = findViewById(R.id.btn_luuhoadon);
        tv_soluonghanghoa = findViewById(R.id.TV_soluonghanghoa);
        tv_tongtien = findViewById(R.id.TV_Tongtien);
        tv_ngaymua = findViewById(R.id.TV_ngay);
        layoutManager = new LinearLayoutManager(this);
        rcl_hanghoa.setLayoutManager(layoutManager);
        fragHoadon = new Frag_hoadon();

        hanghoaList = new ArrayList<>();
        hanghoaAdapter = new HanghoaAdapter(hanghoaList, this);
        rcl_hanghoa.setAdapter(hanghoaAdapter);

        // Khởi tạo HashMap
        hanghoaMap = new HashMap<>();

        add_hanghoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddHanghoaDialog();
            }
        });

        btn_luuhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder hangHoaStringBuilder = new StringBuilder();
                for (HanghoaDTO hanghoa : hanghoaList) {
                    String tenHanghoa = hanghoa.getTenHanghoa();
                    int soLuong = hanghoa.getSoLuong();
                    hangHoaStringBuilder.append(tenHanghoa).append(" - số lượng:").append(soLuong).append("\n");
                }

                String thongTinHangHoa = hangHoaStringBuilder.toString().trim();
                if (thongTinHangHoa.isEmpty()) {
                    Toast.makeText(HoadonActivity.this, "Vui lòng nhập thông tin hàng hóa", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lấy các giá trị khác từ giao diện
                int tongSoLuong = Integer.parseInt(tv_soluonghanghoa.getText().toString());
                int tongTien = Integer.parseInt(tv_tongtien.getText().toString());
                String ngayMua = tv_ngaymua.getText().toString();

                // Tạo đối tượng Hoadon và gán giá trị
                HoadonDTO hoadon = new HoadonDTO();
                hoadon.setTenSanpham(thongTinHangHoa);
                hoadon.setSoLuong(tongSoLuong);
                hoadon.setTongTien(tongTien);
                hoadon.setNgayMua(ngayMua);

                HoadonDAO hoadonDAO = new HoadonDAO(HoadonActivity.this);
                hoadonDAO.addHoadon(hoadon);

                Toast.makeText(HoadonActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                setResult(AppCompatActivity.RESULT_OK);
                finish();





            }
        });
    }

    private void showAddHanghoaDialog() {
        LayoutInflater inflater = LayoutInflater.from(HoadonActivity.this);
        View view1 = inflater.inflate(R.layout.custom_add_hanghoa, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(HoadonActivity.this);
        builder.setView(view1);

        Spinner spn_hanghoa = view1.findViewById(R.id.spin_hanghoa1);
        EditText soluong = view1.findViewById(R.id.soluong);
        List<String> productNames = getProductInfoFromDatabase();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(HoadonActivity.this, android.R.layout.simple_spinner_item, productNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_hanghoa.setAdapter(spinnerAdapter);

        // Lấy ngày và giờ hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        tv_ngaymua.setText(currentDate);

        builder.setTitle("Thêm hàng hóa");
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenHanghoa = spn_hanghoa.getSelectedItem().toString();
                String strSoLuong = soluong.getText().toString();

                if (strSoLuong.isEmpty()) {
                    Toast.makeText(HoadonActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!strSoLuong.matches("\\d+")) {
                    Toast.makeText(HoadonActivity.this, "Số lượng phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }
                int soLuong = Integer.parseInt(strSoLuong);


                if (hanghoaMap.containsKey(tenHanghoa)) {

                    int tongSoLuong = hanghoaMap.get(tenHanghoa) + soLuong;
                    hanghoaMap.put(tenHanghoa, tongSoLuong);
                } else {
                    // Nếu chưa tồn tại, thêm tên hàng và số lượng vào HashMap
                    hanghoaMap.put(tenHanghoa, soLuong);
                }

                Mydbhelper dbHelper = new Mydbhelper(HoadonActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("tenHanghoa", tenHanghoa);
                values.put("soluong", soLuong);

                db.insert("Hanghoa", null, values);

                db.close();

                Toast.makeText(HoadonActivity.this, "Thêm hàng thành công", Toast.LENGTH_SHORT).show();

                // Xóa danh sách hàng hóa cũ
                hanghoaList.clear();

                // Lặp qua HashMap để thêm các hàng hóa vào danh sách
                for (Map.Entry<String, Integer> entry : hanghoaMap.entrySet()) {
                    String tenHanghoaEntry = entry.getKey();
                    int soLuongEntry = entry.getValue();
                    HanghoaDTO hanghoa = new HanghoaDTO(tenHanghoaEntry, soLuongEntry);
                    hanghoaList.add(hanghoa);
                }


                int tongSoLuong = 0;
                int tongTien = 0;
                for (HanghoaDTO hanghoa : hanghoaList) {
                    tongSoLuong += hanghoa.getSoLuong();

                    int soLuong1 = hanghoa.getSoLuong();
                    int gia = getProductPriceFromDatabase(hanghoa.getTenHanghoa());
                    int thanhTien = soLuong1 * gia;

                    tongTien += thanhTien;
                }

                tv_soluonghanghoa.setText("" + tongSoLuong);
                tv_tongtien.setText("" + tongTien);
                hanghoaAdapter.notifyDataSetChanged();
            }
        });
        builder.create().show();
    }

    private List<String> getProductInfoFromDatabase() {
        Mydbhelper dbHelper = new Mydbhelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("Sanpham", new String[]{"tenSanpham", "gia"}, null, null, null, null, null);
        List<String> productInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String productName = cursor.getString(cursor.getColumnIndexOrThrow("tenSanpham"));

            String productInfo = productName;
            productInfoList.add(productInfo);
        }

        cursor.close();
        db.close();

        return productInfoList;
    }

    private int getProductPriceFromDatabase(String productName) {
        Mydbhelper dbHelper = new Mydbhelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "tenSanpham=?";
        String[] selectionArgs = {productName};

        Cursor cursor = db.query("Sanpham", new String[]{"gia"}, selection, selectionArgs, null, null, null);
        int productPrice = 0;
        if (cursor.moveToFirst()) {
            productPrice = cursor.getInt(cursor.getColumnIndexOrThrow("gia"));
        }

        cursor.close();
        db.close();

        return productPrice;
    }

}