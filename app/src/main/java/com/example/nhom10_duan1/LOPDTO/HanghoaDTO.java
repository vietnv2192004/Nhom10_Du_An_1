package com.example.nhom10_duan1.LOPDTO;

public class HanghoaDTO {
    private String tenHanghoa;
    private int soLuong;
    private int id;


    public int getId() {
        return id;
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


}
