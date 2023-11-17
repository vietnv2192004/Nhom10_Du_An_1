package com.example.nhom10_duan1.LOPDTO;

public class CongviecDTO {
    private int maCongviec;
    private String tenNhanvien;
    private String Sogio;
    private String Luong;

    public static final String TB_NAME = "Congviec";
    public static final String COL_NAME_MACONGVIEC = "maCongviec";
    public static final String COL_NAME_TENNHANVIEN = "tenNhanvien";
    public static final String COL_NAME_SOGIO = "Gio";
    public static final String COL_NAME_LUONG = "Luong";

    public CongviecDTO() {
    }

    public CongviecDTO(int maCongviec, String tenNhanvien, String sogio, String luong) {
        this.maCongviec = maCongviec;
        this.tenNhanvien = tenNhanvien;
        this.Sogio = sogio;
        this.Luong = luong;
    }

    public int getMaCongviec() {
        return maCongviec;
    }

    public void setMaCongviec(int maCongviec) {
        this.maCongviec = maCongviec;
    }

    public String getTenNhanvien() {
        return tenNhanvien;
    }

    public void setTenNhanvien(String tenNhanvien) {
        this.tenNhanvien = tenNhanvien;
    }

    public String getSogio() {
        return Sogio;
    }

    public void setSogio(String sogio) {
        this.Sogio = sogio;
    }

    public String getLuong() {
        return Luong;
    }

    public void setLuong(String luong) {
        this.Luong = luong;
    }
}
