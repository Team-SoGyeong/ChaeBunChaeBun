package com.E2I3.chaebunchaebun;

public class SearchListItem {
    private String title = "";
    private String date = "";
    private long locationCode = 0;
    private String price = "";
    private int isAuth = 0;
    private int postId = 0;
    private int categoryId = 0;
    private String writeDate = "";
    private String content = "";

    public SearchListItem(String title, String date, long locationCode, String price, int isAuth, int postId, int categoryId, String writeDate, String content){
        this.title = title;
        this.date = date;
        this.locationCode = locationCode;
        this.price = price;
        this.isAuth = isAuth;
        this.postId = postId;
        this.categoryId = categoryId;
        this.writeDate = writeDate;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public long getLocationCode() {
        return locationCode;
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

    public int getCategoryId() {
        return categoryId;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public String getContent() {
        return content;
    }
}
