package com.example.ifapps_tubes02.model;

public class FRS {
    String semester;
    String tahun;
    String[] mataKuliah;

    public FRS(String semester, String tahun/*, String[] mataKuliah*/) {
        this.semester = semester;
        this.tahun = tahun;
//        this.mataKuliah = mataKuliah;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String[] getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String[] mataKuliah) {
        this.mataKuliah = mataKuliah;
    }
}
