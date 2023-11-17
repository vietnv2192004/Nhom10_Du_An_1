package com.example.nhom10_duan1.LOPDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.LOPDTO.nhanvienDTO;

import java.util.ArrayList;
import java.util.List;

public class nhanvienDAO {
    SQLiteDatabase sqlite;
    Mydbhelper createData;



    public nhanvienDAO(Context context) {
        createData = new Mydbhelper(context);
        sqlite = createData.getWritableDatabase();
    }

    public void OPEN() {
        sqlite = createData.getWritableDatabase();
    }

    public void Close() {
        createData.close();
    }

    public long ADDNV(nhanvienDTO nhanVien) {
        ContentValues values = new ContentValues();
        values.put(nhanvienDTO.COL_MANV, nhanVien.getMaNV());
        values.put(nhanvienDTO.COL_TENNV, nhanVien.getHoTen());
        values.put(nhanvienDTO.COL_MK, nhanVien.getMaKhau());
        values.put(nhanvienDTO.COL_SDT, nhanVien.getSdt());
        return sqlite.insert(nhanvienDTO.TB_NAME, null, values);
    }

    public int UPDATE(nhanvienDTO nhanVien) {
        ContentValues values = new ContentValues();
        values.put(nhanvienDTO.COL_MANV, nhanVien.getMaNV());
        values.put(nhanvienDTO.COL_TENNV, nhanVien.getHoTen());
        values.put(nhanvienDTO.COL_MK, nhanVien.getMaKhau());
        values.put(nhanvienDTO.COL_SDT, nhanVien.getSdt());
        return sqlite.update(nhanvienDTO.TB_NAME, values, "maNV=?", new String[]{nhanVien.getMaNV()});
    }

    public int Thaypass(nhanvienDTO nhanVien) {
        ContentValues values = new ContentValues();
        values.put(nhanvienDTO.COL_MK, nhanVien.getMaKhau());
        return sqlite.update(nhanvienDTO.TB_NAME, values, "maNV=?", new String[]{nhanVien.getMaNV()});
    }

    public int DELETE(String mNV) {
        return sqlite.delete(nhanvienDTO.TB_NAME, "maNV=?", new String[]{mNV});
    }

    public nhanvienDTO getId(String maNV) {
        String selectId = "SELECT * FROM NhanVien WHERE maNV=?";
        List<nhanvienDTO> list = getdata(selectId, maNV);
        return list.get(0);
    }

    public nhanvienDTO getUser(String user) {
        String getuser = "SELECT * FROM NhanVien WHERE maNV=?";
        List<nhanvienDTO> list = getdata(getuser, user);
        return list.get(0);
    }

    //check taikhoan
    public int getUserName(String user) {
        String dl = "SELECT * FROM NhanVien WHERE maNV=? ";
        List<nhanvienDTO> list = getdata(dl, user);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public List<nhanvienDTO> GETNV() {
        String select = "SELECT* FROM NhanVien";
        return getdata(select);
    }

    @SuppressLint("Range")
    private List<nhanvienDTO> getdata(String sql, String... selection) {
        List<nhanvienDTO> list = new ArrayList<>();
        Cursor cursor = sqlite.rawQuery(sql, selection);
        while (cursor.moveToNext()) {
            nhanvienDTO nhanVien = new nhanvienDTO();
            nhanVien.setMaNV(cursor.getString(cursor.getColumnIndex(nhanvienDTO.COL_MANV)));
            nhanVien.setHoTen(cursor.getString(cursor.getColumnIndex(nhanvienDTO.COL_TENNV)));
            nhanVien.setMaKhau(cursor.getString(cursor.getColumnIndex(nhanvienDTO.COL_MK)));
            nhanVien.setSdt(cursor.getString(cursor.getColumnIndex(nhanvienDTO.COL_SDT)));
            list.add(nhanVien);
        }
        return list;
    }

    public int getlogin(String user, String pass) {
        String dl = "SELECT * FROM NhanVien WHERE maNV=? AND matKhau=?";
        List<nhanvienDTO> list = getdata(dl, user, pass);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
