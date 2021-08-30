package com.example.chaebunchaebun;

public class CommentRecyclerItem {
    private String nickname = "";
    private String comment = "";
    private String time = "";
    private int userId = 0;

    public CommentRecyclerItem(String nickname, String comment, String time, int userId){
        this.nickname = nickname;
        this.comment = comment;
        this.time = time;
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTime() {
        return time;
    }

    public int getUserId() {
        return userId;
    }
}
