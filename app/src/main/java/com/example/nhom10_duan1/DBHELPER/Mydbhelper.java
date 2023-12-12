package com.example.nhom10_duan1.DBHELPER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nhom10_duan1.LOPDTO.HoadonDTO;

public class Mydbhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DUAN1.db";
    private static final int VERSION = 7;


    public Mydbhelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Bảng Nhân Viên
        String tb_nv =
                "create table NhanVien (" +
                        "maNV TEXT PRIMARY KEY UNIQUE, " +
                        "hoTen TEXT NOT NULL, " +
                        "matKhau TEXT NOT NULL,"+"sodt INTEGER NOT NULL)";
        db.execSQL(tb_nv);
        String tb_hang =
                "create table Hang (" +
                        "maHang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenHang TEXT UNIQUE NOT NULL)";
        db.execSQL(tb_hang);
        String tb_Sanpham =
                "create table Sanpham (" +
                        "maSanpham INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenSanpham TEXT NOT NULL, " +
                        "gia INTEGER NOT NULL, " +
                        "loai TEXT NOT NULL, " +
                        "maHang INTEGER REFERENCES Hang(maHang))";
        db.execSQL(tb_Sanpham);
        String tb_congviec =
                "create table Congviec (" +
                        "maCongviec INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenNhanvien TEXT  NOT NULL,"+
                        "Gio TEXT NOT NULL,"+
                        "Luong TEXT NOT NULL )";
        db.execSQL(tb_congviec);
        String tb_hanghoa =
                "create table Hanghoa (" +
                        "maHanghoa INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenHanghoa TEXT  NOT NULL,"+
                        "soluong INTERGER)";
        db.execSQL(tb_hanghoa);
        String tb_hoadon =
                "create table Hoadon (" +
                        "maHoadon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "soluong INTEGER NOT NULL, " +
                        "tenSanpham TEXT NOT NULL, " +
                        "Tongtien INTEGER NOT NULL, " +
                        "Ngaymua DATE NOT NULL)";
        db.execSQL(tb_hoadon);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        String dropTableNhanvien = "drop table if exists NhanVien";
        db.execSQL(dropTableNhanvien);
        String dropTablehang = "drop table if exists Hang";
        db.execSQL(dropTablehang);
        String dropTablesanpham = "drop table if exists Sanpham";
        db.execSQL(dropTablesanpham);
        String dropTablecongviec = "drop table if exists Congviec";
        db.execSQL(dropTablecongviec);
        String dropTablehanghoa = "drop table if exists Hanghoa";
        db.execSQL(dropTablehanghoa);
        String dropTablehoadon = "drop table if exists HoaDon";
        db.execSQL(dropTablehoadon);
        onCreate(db);
    }

    public boolean checkMaNVExists(String maNV) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM NhanVien WHERE maNV = ?";
        Cursor cursor = db.rawQuery(query, new String[]{maNV});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    public boolean checkSoDTExists(String soDT) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM NhanVien WHERE sodt = ?";
        Cursor cursor = db.rawQuery(query, new String[]{soDT});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    public boolean checkMATKHAUExists(String matKhau) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM NhanVien WHERE matKhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{matKhau});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    public void updateMatKhau(String maNV, String matKhauMoi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matKhau", matKhauMoi);
        String selection = "maNV = ?";
        String[] selectionArgs = {maNV};
        db.update("NhanVien", values, selection, selectionArgs);
    }
    public int getDoanhThuByDate(String fromDate, String toDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int doanhThu = 0;

        String query = "SELECT SUM(Tongtien) FROM Hoadon WHERE Ngaymua >= ? AND Ngaymua <= ?";
        Cursor cursor = db.rawQuery(query, new String[]{fromDate, toDate});

        if (cursor.moveToFirst()) {
            doanhThu = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return doanhThu;
    }

}

