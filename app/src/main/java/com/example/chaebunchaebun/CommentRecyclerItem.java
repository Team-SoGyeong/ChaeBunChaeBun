package com.example.chaebunchaebun;

public class CommentRecyclerItem {
    private String nickname = "";
    private String comment = "";
    private String time = "";

    public CommentRecyclerItem(String nickname, String comment, String time){
        this.nickname = nickname;
        this.comment = comment;
        this.time = time;
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
}
