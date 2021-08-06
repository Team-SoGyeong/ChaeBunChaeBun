package com.example.chaebunchaebun;

import android.graphics.drawable.Drawable;

public class HomeListItem {
    private int img;
    private String title = "";
    private String date = "";
    private String people = "";
    private String price = "";
    private  String writingDate = "";

    public HomeListItem(int img, String title, String date, String people, String price, String writingDate){
        this.img = img;
        this.title = title;
        this.date = date;
        this.people = people;
        this.price = price;
        this.writingDate = writingDate;
    }

    public int getImg() {
        return this.img;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getPeople() {
        return people;
    }

    public String getPrice() {
        return price;
    }

    public String getWritingDate() {
        return writingDate;
    }
}
