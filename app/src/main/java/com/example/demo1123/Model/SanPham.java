package com.example.demo1123.Model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int id;
    public String tensanpham;
    public Integer giasanpham;
    public String anhsanpham;
    public String motasanpham;
    public int idsp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public Integer getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(Integer giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getAnhsanpham() {
        return anhsanpham;
    }

    public void setAnhsanpham(String anhsanpham) {
        this.anhsanpham = anhsanpham;
    }

    public String getMotasanpham() {
        return motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        this.motasanpham = motasanpham;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public SanPham(int id, String tensanpham, Integer giasanpham, String anhsanpham, String motasanpham, int idsp) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.anhsanpham = anhsanpham;
        this.motasanpham = motasanpham;
        this.idsp = idsp;
    }
}
