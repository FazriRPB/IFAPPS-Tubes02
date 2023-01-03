package com.example.ifapps_tubes02.model;

public class Pengumuman {
    private String judul;
    private String tag;
    private String deskripsi;
    private boolean readStatus;

    public Pengumuman(String judul, String tag, String deskripsi, boolean readStatus){
        this.judul = judul;
        this.tag = tag;
        this.deskripsi = deskripsi;
        this.readStatus = readStatus;
    }

    public String getJudul() {
        return this.judul;
    }

    public String gettag(){
        return this.tag;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public boolean readStatus(){
        return this.readStatus;
    }
}
