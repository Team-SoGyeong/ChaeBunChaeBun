package com.example.chaebunchaebun;

public class SearchListItem {
    private String title = "";
    private String date = "";
    private String people = "";
    private String price = "";
    private int isAuth = 0;
    private int postId = 0;

    public SearchListItem(String title, String date, String people, String price, int isAuth, int postId){
        this.title = title;
        this.date = date;
        this.people = people;
        this.price = price;
        this.isAuth = isAuth;
        this.postId = postId;
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

    public int getIsAuth() {
        return isAuth;
    }

    public int getPostId() {
        return postId;
    }
}
