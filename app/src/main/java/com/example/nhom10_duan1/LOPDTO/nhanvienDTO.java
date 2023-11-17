package com.example.nhom10_duan1.LOPDTO;

public class nhanvienDTO {

        private String maNV;
        private String Sdt;


        public nhanvienDTO() {
        }

        public nhanvienDTO(String maNV, String hoTen, String maKhau, String Sdt) {
            this.maNV = maNV;
            this.hoTen = hoTen;
            this.maKhau = maKhau;
            this.Sdt= Sdt;
        }

        private String hoTen;


        public String getMaNV() {
            return maNV;
        }

        public void setMaNV(String maNV) {
            this.maNV = maNV;
        }

        public String getHoTen() {
            return hoTen;
        }

        public void setHoTen(String hoTen) {
            this.hoTen = hoTen;
        }

        public String getMaKhau() {
            return maKhau;
        }

        public void setMaKhau(String maKhau) {
            this.maKhau = maKhau;
        }

        public String getSdt() {
            return Sdt;
        }

        public void setSdt(String sdt) {
            this.Sdt = sdt;
        }

        private String maKhau;


        public static final String TB_NAME = "Nhanvien";
        public static final String COL_MANV = "maNV";
        public static final String COL_TENNV = "hoTen";
        public static final String COL_MK = "matKhau";
        public static final String COL_SDT = "sodt";
    }

