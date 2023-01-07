package com.example.ifapps_tubes02.model;

import java.util.ArrayList;

public class Pengumuman {
    String id;
    String title;
    String updated_at;
    String created_at;
    Author author;
    ArrayList<Tags> tags;

    public Pengumuman(String id, String title, String updated_at, String created_at, Author author, ArrayList<Tags> tags) {
        this.id = id;
        this.title = title;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.author = author;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public ArrayList<Tags> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tags> tags) {
        this.tags = tags;
    }
}
