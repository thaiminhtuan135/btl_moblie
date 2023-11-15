package com.btln9.qlsv.model;

public class Em_Of {
    int id;
    String nameEm;
    String nameOf;

    public Em_Of() {
    }

    public Em_Of(int id, String nameEm, String nameOf) {
        this.id = id;
        this.nameEm = nameEm;
        this.nameOf = nameOf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEm() {
        return nameEm;
    }

    public void setNameEm(String nameEm) {
        this.nameEm = nameEm;
    }

    public String getNameOf() {
        return nameOf;
    }

    public void setNameOf(String nameOf) {
        this.nameOf = nameOf;
    }
}
