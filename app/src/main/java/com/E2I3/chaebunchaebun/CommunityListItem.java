package com.E2I3.chaebunchaebun;

public class CommunityListItem {
    private String profile = "";
    private String nickname = "";
    private String createAt = "";
    private String content = "";
    private String img1 = "";
    private String img2 = "";
    private String img3 = "";
    private String img4 = "";
    private String img5 = "";
    private String address = "";
    private int likeCount = 0;
    private int postId = 0;
    private int commCount = 0;
    private int isLike = 0;
    private boolean isSame = false;

    public CommunityListItem(String profile, String nickname, String createAt, String content,
                             String img1, String img2, String img3, String img4, String img5,
                             String address, int likeCount, int postId, int commCount, int isLike,
                             boolean isSame){
        this.profile = profile;
        this.nickname = nickname;
        this.createAt = createAt;
        this.content = content;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
        this.address = address;
        this.likeCount = likeCount;
        this.postId = postId;
        this.commCount = commCount;
        this.isLike = isLike;
        this.isSame = isSame;
    }

    public String getProfile() {
        return profile;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getContent() {
        return content;
    }

    public String getImg1() {
        return img1;
    }

    public String getImg2() {
        return img2;
    }

    public String getImg3() {
        return img3;
    }

    public String getImg4() {
        return img4;
    }

    public String getImg5() {
        return img5;
    }

    public String getAddress() {
        return address;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getPostId() {
        return postId;
    }

    public int getCommCount() {
        return commCount;
    }

    public int isLike() {
        return isLike;
    }

    public boolean isSame() {
        return isSame;
    }

    public void setIsLike(int like) {
        isLike = like;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
