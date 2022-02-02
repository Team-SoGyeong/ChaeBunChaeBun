package com.E2I3.chaebunchaebun;

public class NoticeListItem {
    String caseType = "";
    String nickname = "";
    String img = "";
    String title = "";
    int isAuth = 0;
    String content = "";
    String date = "";
    int postId = 0;
    int categoryId = 0;
    String userId;

    public NoticeListItem(String caseType, String nickname, String img, String title, String content, String date,
                          int isAuth, int postId, int categoryId, String userId) {
        this.caseType = caseType;
        this.nickname = nickname;
        this.img = img;
        this.title = title;
        this.content = content;
        this.date = date;
        this.isAuth = isAuth;
        this.postId = postId;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public String getCaseType() {
        return caseType;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
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

    public String getUserId() {
        return userId;
    }
}
