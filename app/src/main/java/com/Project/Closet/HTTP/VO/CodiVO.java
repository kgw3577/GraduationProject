package com.Project.Closet.HTTP.VO;

public class CodiVO {
    private int no; // PRIMARY KEY. AUTO INCREMENT
    private String name;
    private String category;
    private String date;
    private String season;
    private String cloSize;
    private String img;
    private String fileName;
    private String filePath;
    private String like;
    private String userID; // FOREIGN KEY(CLOSET). not null
    private String closetName; // FOREIGN KEY(CLOSET). not null


    //생성자
    public CodiVO() {
        System.out.println("ClothesVO 생성자 호출");
    }

    public CodiVO(String userID, String closetName, String fileName) {
        System.out.println("ClothesVO 생성자 호출");
        this.userID = userID;
        this.closetName = closetName;
        this.fileName = fileName;
    }

    //getter & setter
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCloSize() {
        return cloSize;
    }

    public void setCloSize(String cloSize) {
        this.cloSize = cloSize;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getClosetName() {
        return closetName;
    }

    public void setClosetName(String closetName) {
        this.closetName = closetName;
    }
}
