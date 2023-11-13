package com.btln9.qlsv.model;

public class Em_Off {
     int id;
    String name_employee;
    String name_office;

    public Em_Off(int id, String name_employee, String name_office) {
        this.id = id;
        this.name_employee = name_employee;
        this.name_office = name_office;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_employee() {
        return name_employee;
    }

    public void setName_employee(String name_employee) {
        this.name_employee = name_employee;
    }

    public String getName_office() {
        return name_office;
    }

    public void setName_office(String name_office) {
        this.name_office = name_office;
    }
}
