package com.E2I3.chaebunchaebun;

public class LikeListItem {
    private String img;
    private String title = "";
    private String date = "";
    private String people = "";
    private String price = "";
    private String writingDate = "";
    private int isAuth = 5;
    private int userId = 0;
    private int postId = 0;
    private int categoryId = 0;
    private String content = "";

    public LikeListItem(String img, String title, String date, String people, String price,
                        String writingDate, int isAuth, int postId, int userId, int categoryId, String content){
        this.img = img;
        this.title = title;
        this.date = date;
        this.people = people;
        this.price = price;
        this.writingDate = writingDate;
        this.isAuth = isAuth;
        this.postId = postId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.content = content;
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

    public int getCategoryId() {
        return categoryId;
    }

    public String getContent() {
        return content;
    }
}
