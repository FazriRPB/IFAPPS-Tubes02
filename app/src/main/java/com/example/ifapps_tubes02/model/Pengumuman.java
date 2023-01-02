package com.example.ifapps_tubes02.model;

public class Pengumuman {
    private String judul;
    private String designasi;
    private boolean readStatus;

    public Pengumuman(String judul, String designasi, boolean readStatus){
        this.judul = judul;
        this.designasi = designasi;
        this.readStatus = readStatus;
    }

    public String getJudul() {
        return this.judul;
    }

    public String getDesignasi(){
        return this.designasi;
    }

    public boolean readStatus(){
        return this.readStatus;
    }
}
