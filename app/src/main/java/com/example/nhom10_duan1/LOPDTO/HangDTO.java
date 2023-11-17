package com.example.nhom10_duan1.LOPDTO;

public class HangDTO {
    private int maHang;
    private String tenHang;




    public static final String TB_NAME = "Hang";
    public static final String COL_NAME_MAHang = "maHang";
    public static final String COL_NAME_TENHang = "tenHang";


    public HangDTO() {
    }

    public HangDTO(int mahang, String tenhang) {
        this.maHang = mahang;
        this.tenHang = tenhang;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }
}
