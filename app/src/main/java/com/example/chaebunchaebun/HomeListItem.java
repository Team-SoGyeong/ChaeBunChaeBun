package com.example.chaebunchaebun;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class HomeListItem {
    private String img;
    private String title = "";
    private String date = "";
    private String people = "";
    private String price = "";
    private  String writingDate = "";
    private int isAuth = 5;

    public HomeListItem(String img, String title, String date, String people, String price, String writingDate, int isAuth){
        this.img = img;
        this.title = title;
        this.date = date;
        this.people = people;
        this.price = price;
        this.writingDate = writingDate;
        this.isAuth = isAuth;
    }

    public String getImg() {
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

    public int getIsAuth() {
        return isAuth;
    }
}
