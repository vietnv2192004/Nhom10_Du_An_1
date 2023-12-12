package com.example.nhom10_duan1.LOPDTO;

public class HanghoaDTO {
    private String tenHanghoa;
    private int soLuong;
    private int id;


    public int getId() {
        return id;
    }

    public void setTenHanghoa(String tenHanghoa) {
        this.tenHanghoa = tenHanghoa;
    }

    public HanghoaDTO( int id,String tenHanghoa, int soLuong) {
        this.tenHanghoa = tenHanghoa;
        this.soLuong = soLuong;
        this.id = id;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HanghoaDTO(String tenHanghoa, int soLuong) {
        this.tenHanghoa = tenHanghoa;
        this.soLuong = soLuong;
    }

    public String getTenHanghoa() {
        return tenHanghoa;
    }

    public int getSoLuong() {
        return soLuong;
    }



    public static final String TB_NAME = "Hanghoa";
    public static final String COL_MAHD = "maHanghoa";
    public static final String COL_TENHANGHOA = "tenHanghoa";
    public static final String COL_SOLUONG = "soluong";

}
