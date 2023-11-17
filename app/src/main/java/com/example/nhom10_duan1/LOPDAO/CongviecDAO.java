package com.example.nhom10_duan1.LOPDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.LOPDTO.CongviecDTO;

import java.util.ArrayList;
import java.util.List;

public class CongviecDAO {
    private Mydbhelper createData;
    private SQLiteDatabase liteDatabase;

    public CongviecDAO(Context context) {
        createData = new Mydbhelper(context);
        liteDatabase = createData.getWritableDatabase();
    }

    public long ADDLS(CongviecDTO congviec) {
        ContentValues values = new ContentValues();
        values.put(CongviecDTO.COL_NAME_TENNHANVIEN, congviec.getTenNhanvien());
        values.put(CongviecDTO.COL_NAME_SOGIO, congviec.getSogio());
        values.put(CongviecDTO.COL_NAME_LUONG, congviec.getLuong());
        return liteDatabase.insert(CongviecDTO.TB_NAME, null, values);
    }

    public int UPDATELS(CongviecDTO congviec) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CongviecDTO.COL_NAME_TENNHANVIEN, congviec.getTenNhanvien());
        contentValues.put(CongviecDTO.COL_NAME_SOGIO, congviec.getSogio());
        contentValues.put(CongviecDTO.COL_NAME_LUONG, congviec.getLuong());
        return liteDatabase.update(CongviecDTO.TB_NAME, contentValues, "maCongviec=?", new String[]{String.valueOf(congviec.getMaCongviec())});
    }

    public int DELETELS(CongviecDTO congviec) {
        return liteDatabase.delete(CongviecDTO.TB_NAME, "maCongviec=?", new String[]{String.valueOf(congviec.getMaCongviec())});
    }

    public List<CongviecDTO> GETLS() {
        String dl = "SELECT * FROM Congviec";
        List<CongviecDTO> list = getdata(dl);
        return list;
    }

    public CongviecDTO getId(String id) {
        String sql = "SELECT * FROM Congviec WHERE maCongviec=?";
        List<CongviecDTO> list = getdata(sql, id);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @SuppressLint("Range")
    private List<CongviecDTO> getdata(String dl, String... Arays) {
        List<CongviecDTO> list = new ArrayList<>();
        Cursor cursor = liteDatabase.rawQuery(dl, Arays);
        while (cursor.moveToNext()) {
            CongviecDTO congviec = new CongviecDTO();
            congviec.setMaCongviec(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CongviecDTO.COL_NAME_MACONGVIEC))));
            congviec.setTenNhanvien(cursor.getString(cursor.getColumnIndex(CongviecDTO.COL_NAME_TENNHANVIEN)));
            congviec.setSogio(cursor.getString(cursor.getColumnIndex(CongviecDTO.COL_NAME_SOGIO)));
            congviec.setLuong(cursor.getString(cursor.getColumnIndex(CongviecDTO.COL_NAME_LUONG)));
            list.add(congviec);
        }
        cursor.close(); // Đảm bảo đóng Cursor sau khi sử dụng xong
        return list;
    }
}
