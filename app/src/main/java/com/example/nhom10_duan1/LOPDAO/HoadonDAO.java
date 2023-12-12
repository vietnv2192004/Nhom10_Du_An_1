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

    public long addHoadon(HoadonDTO hoadonDTO) {
        ContentValues values = new ContentValues();
        values.put("soluong", hoadonDTO.getSoLuong());
        values.put("tenSanpham", hoadonDTO.getTenSanpham());
        values.put("Tongtien", hoadonDTO.getTongTien());
        values.put("Ngaymua", hoadonDTO.getNgayMua());

        SQLiteDatabase db = createData.getWritableDatabase();
        long result = db.insert("Hoadon", null, values);
        db.close();
        return result;
    }

    public void DELETE(String maHoadon) {
        sqlite.delete(HoadonDTO.TB_NAME, HoadonDTO.COL_TONGTIEN+ " = ?", new String[]{maHoadon});
    }

    public List<HoadonDTO> GETS() {
        String dl = "SELECT * FROM Hoadon";
        List<HoadonDTO> list = getdata(dl);
        return list;
    }

    public HoadonDTO getId(String id) {
        String sql = "SELECT * FROM Hoadon WHERE maHoadon=?";
        List<HoadonDTO> list = getdata(sql, id);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @SuppressLint("Range")
    private List<HoadonDTO> getdata(String dl, String... Arays) {
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