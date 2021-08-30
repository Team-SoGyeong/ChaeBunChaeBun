package com.example.chaebunchaebun;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class HomeListItem {
    private String img;
    private String title = "";
    private String date = "";
    private String people = "";
    private String price = "";
    private String writingDate = "";
    private int isAuth = 5;
    private int userId = 0;
    private int postId = 0;

    public HomeListItem(String img, String title, String date, String people, String price, String writingDate, int isAuth, int postId, int userId){
        this.img = img;
        this.title = title;
        this.date = date;
        this.people = people;
        this.price = price;
        this.writingDate = writingDate;
        this.isAuth = isAuth;
        this.postId = postId;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public int getPostId() {
        return postId;
    }
}
