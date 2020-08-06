package com.Project.Closet.HTTP.VO;

public class UserspaceVO {
    private String userID, nickname, pfImagePath, pfContents, if_following, followig_friendsID, followig_friendsName, followig_friendsImgPath;
    private int numBoard, numFollower, numFollowing;


    //생성자
    public UserspaceVO() {
        System.out.println("UserspaceVO 생성자 호출");
    }


    //getter setter
    public String getUserID() {
        return userID;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getPfImagePath() {
        return pfImagePath;
    }


    public void setPfImagePath(String pfImagePath) {
        this.pfImagePath = pfImagePath;
    }


    public String getPfContents() {
        return pfContents;
    }


    public void setPfContents(String pfContents) {
        this.pfContents = pfContents;
    }


    public String getIf_following() {
        return if_following;
    }


    public void setIf_following(String if_following) {
        this.if_following = if_following;
    }


    public String getFollowig_friendsID() {
        return followig_friendsID;
    }


    public void setFollowig_friendsID(String followig_friendsID) {
        this.followig_friendsID = followig_friendsID;
    }


    public String getFollowig_friendsName() {
        return followig_friendsName;
    }


    public void setFollowig_friendsName(String followig_friendsName) {
        this.followig_friendsName = followig_friendsName;
    }


    public String getFollowig_friendsImgPath() {
        return followig_friendsImgPath;
    }


    public void setFollowig_friendsImgPath(String followig_friendsImgPath) {
        this.followig_friendsImgPath = followig_friendsImgPath;
    }


    public int getNumBoard() {
        return numBoard;
    }


    public void setNumBoard(int numBoard) {
        this.numBoard = numBoard;
    }


    public int getNumFollower() {
        return numFollower;
    }


    public void setNumFollower(int numFollower) {
        this.numFollower = numFollower;
    }


    public int getNumFollowing() {
        return numFollowing;
    }


    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

}
