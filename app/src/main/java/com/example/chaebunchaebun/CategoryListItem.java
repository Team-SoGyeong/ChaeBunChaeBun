package com.example.chaebunchaebun;

public class CategoryListItem {
    private String profile = "";
    private String title = "";
    private String nickname = "";
    private String writingDate = "";
    private String content = "";
    private String img1 = "";
    private String img2 = "";
    private String img3 = "";
    private String img4 = "";
    private String img5 = "";
    private String buyingDate = "";
    private String people = "";
    private String price = "";
    private String likeCount = "";
    private String commentCount = "";
    private int isAuth = 5;
    private int isWish = 0;
    private int postId = 0;
    private int userId = 0;
    private boolean isSame = false;
    private int categoryId = 0;

    public CategoryListItem(String profile, String title, String nickname, String writingDate, String content,
                            String img1, String img2, String img3, String img4, String img5,
                            String buyingDate, String people, String price, String likeCount, String commentCount, int isAuth,
                            int isWish, int postId, int userId, boolean isSame, int categoryId) {
        this.profile = profile;
        this.title = title;
        this.nickname = nickname;
        this.writingDate = writingDate;
        this.content = content;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
        this.buyingDate = buyingDate;
        this.people = people;
        this.price = price;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isAuth = isAuth;
        this.isWish = isWish;
        this.postId = postId;
        this.userId = userId;
        this.isSame = isSame;
        this.categoryId = categoryId;
    }

    public  String getProfile() {
        return this.profile;
    }

    public String getTitle() {
        return this.title;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getWritingDate() {
        return this.writingDate;
    }

    public String getContent() {
        return this.content;
    }

    public String getImg1() {
        return this.img1;
    }

    public String getImg2() {
        return this.img2;
    }

    public String getImg3() {
        return this.img3;
    }

    public String getImg4() {
        return this.img4;
    }

    public String getImg5() {
        return this.img5;
    }

    public String getBuyingDate() {
        return this.buyingDate;
    }

    public String getPeople() {
        return this.people;
    }

    public String getPrice() {
        return this.price;
    }

    public String getLikeCount() {
        return this.likeCount;
    }

    public String getCommentCount() {
        return this.commentCount;
    }

    public int getIsAuth() {
        return isAuth;
    }

    public int getIsWish() {
        return isWish;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isSame() {
        return isSame;
    }

    public void setIsWish(int isWish) {
        this.isWish = isWish;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
