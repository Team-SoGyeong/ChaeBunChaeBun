package com.E2I3.chaebunchaebun;

public class MypageCommunityListItems {
    private int postId = 0;
    private int userId = 0;
    private String content = "";
    private int likeCount = 0;
    private int commentCount = 0;

    public MypageCommunityListItems(int postId, int userId, String content, int likeCount, int commentCount) {
        this.content = content;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.postId = postId;
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return this.content;
    }

    public String getLikeCount() {
        return String.valueOf(this.likeCount);
    }

    public String getCommentCount() {
        return String.valueOf(this.commentCount);
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = Integer.parseInt(likeCount);
    }
}
