package com.example.chaebunchaebun;

public class CommentRecyclerItem {
    private String profile = "";
    private String nickname = "";
    private String comment = "";
    private String time = "";
    private int userId = 0;
    private int commentId = 0;

    public CommentRecyclerItem(String profile, String nickname, String comment, String time, int userId, int commentId){
        this.profile = profile;
        this.nickname = nickname;
        this.comment = comment;
        this.time = time;
        this.userId = userId;
        this.commentId = commentId;
    }

    public String getProfile() {
        return profile;
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

    public int getCommentId() {
        return commentId;
    }
}
