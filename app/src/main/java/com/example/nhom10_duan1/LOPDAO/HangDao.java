package com.example.nhom10_duan1.LOPDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.LOPDTO.HangDTO;

import java.util.ArrayList;
import java.util.List;

public class HangDao {
    private Mydbhelper createData;
    private SQLiteDatabase liteDatabase;

    public HangDao(Context context) {
        createData = new Mydbhelper(context);
        liteDatabase = createData.getWritableDatabase();
    }

    public long ADDLS(HangDTO hang) {
        ContentValues values = new ContentValues();
        values.put(HangDTO.COL_NAME_TENHang, hang.getTenHang());
        return liteDatabase.insert(HangDTO.TB_NAME, null, values);
    }

    public int UPDATELS(HangDTO hang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(HangDTO.COL_NAME_TENHang, hang.getTenHang());
        return liteDatabase.update(HangDTO.TB_NAME, contentValues, "maHang=?", new String[]{String.valueOf(hang.getMaHang())});
    }

    public int DELETELS(HangDTO hang) {
        return liteDatabase.delete(HangDTO.TB_NAME, "maHang=?", new String[]{String.valueOf(hang.getMaHang())});
    }

    public List<HangDTO> GETLS() {
        String dl = "SELECT * FROM Hang";
        List<HangDTO> list = getdata(dl);
        return list;
    }

    public HangDTO getId(String id) {
        String sql = "SELECT * FROM Hang WHERE maHang=?";
        List<HangDTO> list = getdata(sql, id);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @SuppressLint("Range")
    private List<HangDTO> getdata(String dl, String... Arays) {
        List<HangDTO> list = new ArrayList<>();
        Cursor cursor = liteDatabase.rawQuery(dl, Arays);
        while (cursor.moveToNext()) {
            HangDTO hang = new HangDTO();
            hang.setMaHang(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HangDTO.COL_NAME_MAHang))));
            hang.setTenHang(cursor.getString(cursor.getColumnIndex(HangDTO.COL_NAME_TENHang)));
            list.add(hang);
        }
        cursor.close(); // Đảm bảo đóng Cursor sau khi sử dụng xong
        return list;
    }
}