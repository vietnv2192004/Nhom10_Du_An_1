package com.example.nhom10_duan1.LOPDTO;

public class HoadonDTO {

    private int mahoadon;
    private String tenSanpham;
    private int soLuong;
    private int tongTien;
    private String ngayMua;


    public HoadonDTO() {
    }

    public HoadonDTO(int soLuong, String tenSanPham, int tongTien, String ngayMua) {
        this.soLuong = soLuong;
        this.tenSanpham = tenSanPham;
        this.tongTien = tongTien;
        this.ngayMua = ngayMua;
    }
    public String getTenSanpham() {
        return tenSanpham;
    }

    public void setTenSanpham(String tenSanpham) {
        this.tenSanpham = tenSanpham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(int mahoadon) {
        this.mahoadon = mahoadon;
    }

    public static final String TB_NAME = "Hoadon";
    public static final String COL_MAHD = "maHoadon";
    public static final String COL_TENSANPHAM = "tenSanpham";
    public static final String COL_SOLUONG = "soluong";
    public static final String COL_TONGTIEN = "Tongtien";
    public static final String COL_NGAYMUA = "Ngaymua";
}
