package com.btln9.qlsv.model;

public class NhanVien_PhongBan {
    private int id;
    private int id_nv;
    private int id_pb;

    public NhanVien_PhongBan(int id, int id_nv, int id_pb) {
        this.id = id;
        this.id_nv = id_nv;
        this.id_pb = id_pb;
    }

    public NhanVien_PhongBan(int id_nv, int id_pb) {
        this.id_nv = id_nv;
        this.id_pb = id_pb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_nv() {
        return id_nv;
    }

    public void setId_nv(int id_nv) {
        this.id_nv = id_nv;
    }

    public int getId_pb() {
        return id_pb;
    }

    public void setId_pb(int id_pb) {
        this.id_pb = id_pb;
    }

    @Override
    public String toString() {
        return "NhanVien_PhongBan{" +
                "id=" + id +
                ", id_nv=" + id_nv +
                ", id_pb=" + id_pb +
                '}';
    }
}
