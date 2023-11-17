package com.example.nhom10_duan1.LOPDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.LOPDTO.SanphamDTO;

import java.util.ArrayList;
import java.util.List;

public class SanphamDao {
    SQLiteDatabase sqLiteDatabase;
    Mydbhelper createData;

    public SanphamDao(Context context) {
        createData = new Mydbhelper(context);
        sqLiteDatabase = createData.getWritableDatabase();
    }

    public long ADDS(SanphamDTO sanphamDTO) {
        ContentValues values = new ContentValues();
        values.put(SanphamDTO.COL_NAME_MAhang, sanphamDTO.getMahang());
        values.put(SanphamDTO.COL_NAME_TENSanpham, sanphamDTO.getTensanpham());
        values.put(SanphamDTO.COL_NAME_GIA, sanphamDTO.getGiasanpham());
        values.put(SanphamDTO.COL_NAME_loai, sanphamDTO.getLoai());
        return sqLiteDatabase.insert(SanphamDTO.TB_NAME, null, values);
    }

    public int DELETES(SanphamDTO sanphamDTO) {
        return sqLiteDatabase.delete(SanphamDTO.TB_NAME, "maSanpham=?", new String[]{String.valueOf(sanphamDTO.getMasanpham())});
    }

    public int UPDATES(SanphamDTO sanphamDTO) {
        ContentValues values = new ContentValues();
        values.put(SanphamDTO.COL_NAME_MAhang, sanphamDTO.getMahang());
        values.put(SanphamDTO.COL_NAME_TENSanpham, sanphamDTO.getTensanpham());
        values.put(SanphamDTO.COL_NAME_GIA, sanphamDTO.getGiasanpham());
        values.put(SanphamDTO.COL_NAME_loai, sanphamDTO.getLoai());
        return sqLiteDatabase.update(SanphamDTO.TB_NAME, values, "maSanpham=?", new String[]{String.valueOf(sanphamDTO.getMasanpham())});
    }

    public List<SanphamDTO> GETS() {
        String dl = "SELECT * FROM Sanpham";
        List<SanphamDTO> list = getdata(dl);
        return list;
    }

    public SanphamDTO getId(String id) {
        String sql = "SELECT * FROM Sanpham WHERE maSanpham=?";
        List<SanphamDTO> list = getdata(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<SanphamDTO> getdata(String dl, String... Arays /* có hoặc không nhiều phần tử*/) {
        List<SanphamDTO> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(dl, Arays);
        while (cursor.moveToNext()) {
            SanphamDTO sach = new SanphamDTO();
            sach.setMasanpham(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SanphamDTO.COL_NAME_MASanpham))));
            sach.setMahang(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SanphamDTO.COL_NAME_MAhang))));
            sach.setTensanpham(cursor.getString(cursor.getColumnIndex(SanphamDTO.COL_NAME_TENSanpham)));
            sach.setLoai(cursor.getString(cursor.getColumnIndex(SanphamDTO.COL_NAME_loai)));
            sach.setGiasanpham(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SanphamDTO.COL_NAME_GIA))));
            list.add(sach);
        }
        return list;
    }
}