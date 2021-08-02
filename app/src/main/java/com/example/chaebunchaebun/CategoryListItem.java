package com.example.chaebunchaebun;

public class CategoryListItem {
    private String title = "";
    private String nickname = "";
    private String writingDate = "";
    private String content = "";
    private String buyingDate = "";
    private String people = "";
    private String price = "";
    private String likeCount = "";
    private String commentCount = "";

    public CategoryListItem(String title, String nickname, String writingDate, String content, String buyingDate, String people, String price, String likeCount, String commentCount) {
        this.title = title;
        this.nickname = nickname;
        this.writingDate = writingDate;
        this.content = content;
        this.buyingDate = buyingDate;
        this.people = people;
        this.price = price;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getWritingDate() {
        return this.writingDate;
    }

    public void setWritingDate(String writingDate) {
        this.writingDate = writingDate;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBuyingDate() {
        return this.buyingDate;
    }

    public void setBuyingDate(String buyingDate) {
        this.buyingDate = buyingDate;
    }

    public String getPeople() {
        return this.people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
