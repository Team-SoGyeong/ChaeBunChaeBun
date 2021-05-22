package com.example.chaebunchaebun;

public class User {
    public String userName;
    public String userNickname;
    public String userId;
    public String userPw;
    public String userAddress;
    public Integer mileage;

    public User() {

    }

    public User(String userName, String userNickname, String userId, String userPw, String userAddress, Integer mileage){
        this.userName = userName;
        this.userNickname = userNickname;
        this.userId = userId;
        this.userPw = userPw;
        this.userAddress = userAddress;
        this.mileage = mileage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", mileage=" + mileage +
                '}';
    }
}
