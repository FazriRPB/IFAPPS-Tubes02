package com.example.ifapps_tubes02.model;

public class Pertemuan {
    private String judul;
    private String people;
    private String deskripsi;
    private String start_datetime;
    private String end_datetime;

    public Pertemuan(String judul, String people, String deskripsi){
        this.judul= judul;
        this.people= people;
        this.deskripsi= deskripsi;
    }
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }



    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }


    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
    }

}
