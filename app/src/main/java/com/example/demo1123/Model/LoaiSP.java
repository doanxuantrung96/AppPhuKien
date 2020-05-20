package com.example.demo1123.Model;

public class LoaiSP {
    public int  id;
    public String tenloaisp;
    public  String hinhanhloaiSP;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public String getHinhanhloaiSP() {
        return hinhanhloaiSP;
    }

    public void setHinhanhloaiSP(String hinhanhloaiSP) {
        this.hinhanhloaiSP = hinhanhloaiSP;
    }

    public LoaiSP(int id, String tenloaisp, String hinhanhloaiSP) {
        this.id = id;
        this.tenloaisp = tenloaisp;
        this.hinhanhloaiSP = hinhanhloaiSP;
    }
}
