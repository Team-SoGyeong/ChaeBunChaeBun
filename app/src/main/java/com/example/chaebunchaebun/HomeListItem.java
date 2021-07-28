package com.example.chaebunchaebun;

import android.graphics.drawable.Drawable;

public class HomeListItem {
    private Drawable img;
    private String title = "";
    private String date = "";
    private String people = "";
    private String price = "";

    public Drawable getImg() {
        return this.img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
