package com.example.nhom10_duan1.LOPDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.LOPDTO.HoadonDTO;
import com.example.nhom10_duan1.LOPDTO.SanphamDTO;
import com.example.nhom10_duan1.LOPDTO.nhanvienDTO;

import java.util.ArrayList;
import java.util.List;

public class HoadonDAO {
    SQLiteDatabase sqlite;
    Mydbhelper createData;

    public HoadonDAO(Context context) {
        createData = new Mydbhelper(context);
        sqlite = createData.getWritableDatabase();
    }

    public void OPEN() {
        sqlite = createData.getWritableDatabase();
    }

    public void Close() {
        createData.close();
    }

    public long addHoadon(HoadonDTO hoadon) {
        ContentValues values = new ContentValues();
        values.put(HoadonDTO.COL_TENSANPHAM, hoadon.getTenSanpham());
        values.put(HoadonDTO.COL_SOLUONG, hoadon.getSoLuong());
        values.put(HoadonDTO.COL_TONGTIEN, hoadon.getTongTien());
        values.put(HoadonDTO.COL_NGAYMUA, hoadon.getNgayMua());
        return sqlite.insert(HoadonDTO.TB_NAME, null, values);
    }

    public int DELETE(String mNV) {
        return sqlite.delete(HoadonDTO.TB_NAME, "maHoadon=?", new String[]{mNV});
    }

    public List<HoadonDTO> GETS() {
        String dl = "SELECT * FROM Sanpham";
        List<HoadonDTO> list = getdata(dl);
        return list;
    }

    public HoadonDTO getId(String id) {
        String sql = "SELECT * FROM Sanpham WHERE maSanpham=?";
        List<HoadonDTO> list = getdata(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<HoadonDTO> getdata(String dl, String... Arays /* có hoặc không nhiều phần tử*/) {
        List<HoadonDTO> list = new ArrayList<>();
        Cursor cursor = sqlite.rawQuery(dl, Arays);
        while (cursor.moveToNext()) {
            HoadonDTO hoadon = new HoadonDTO();
            hoadon.setMahoadon(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HoadonDTO.COL_MAHD))));
            hoadon.setTenSanpham(cursor.getString(cursor.getColumnIndex(HoadonDTO.COL_TENSANPHAM)));
            hoadon.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HoadonDTO.COL_SOLUONG))));
            hoadon.setTongTien(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HoadonDTO.COL_TONGTIEN))));
            hoadon.setNgayMua(String.valueOf(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HoadonDTO.COL_NGAYMUA)))));
            list.add(hoadon);
        }
        return list;
    }
}