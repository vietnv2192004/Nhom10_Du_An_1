package com.example.nhom10_duan1.LOPDTO;

public class TopDTO {
    private String tenSanPham;
    private int soLuong;
    private int tongSoLuong;

    public TopDTO(String tenSanPham, int soLuong) {
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.tongSoLuong = soLuong;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }
    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public void setTongSoLuong(int tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
    }
}