package com.example.ifapps_tubes02.model;

public class Pertemuan {
    private String title;
    private String start_datetime;
    private String end_datetime;
    String id;

    public Pertemuan(String id, String title, String start_datetime, String end_datetime){
        this.id= id;
        this.title= title;
        this.start_datetime= start_datetime;
        this.end_datetime= end_datetime;
    }
    public String gettitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void settitle(String title) {
        this.title = title;
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
