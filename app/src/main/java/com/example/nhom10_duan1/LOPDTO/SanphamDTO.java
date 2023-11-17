package com.example.nhom10_duan1.LOPDTO;

public class SanphamDTO {
    int masanpham;
    String tensanpham;
    int giasanpham;
    int mahang;
    String loai;

    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public int getMahang() {
        return mahang;
    }

    public void setMahang(int mahang) {
        this.mahang = mahang;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public SanphamDTO() {
    }

    public SanphamDTO(int masanpham, String tensanpham, int giasanpham, int mahang) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.mahang = mahang;
    }

    public static final String TB_NAME = "Sanpham";
    public static final String COL_NAME_MASanpham = "maSanpham";
    public static final String COL_NAME_loai = "loai";
    public static final String COL_NAME_TENSanpham = "tenSanpham";
    public static final String COL_NAME_GIA = "gia";
    public static final String COL_NAME_MAhang = "maHang";
}
