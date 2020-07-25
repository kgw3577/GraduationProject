package com.Project.Closet.HTTP.VO;

public class CodiVO {
    private int codiNo; // PRIMARY KEY. AUTO INCREMENT
    private String codiName;
    private String season; //계절
    private String place; //장소
    private String buyDate; //구입 날짜
    private String comment; //코멘트 500자
    private String favorite; //즐겨찾기 여부, DEFAULT 'no'
    private String fileName;
    private String filePath;
    private String addedDate; //등록일, DEFAULT CURRENT_TIMESTAMP
    private String userID; // FOREIGN KEY(CLOSET). not null



    //생성자
    public CodiVO() {
        System.out.println("CodiVO 생성자 호출");
    }

    public CodiVO(int codiNo) {
        System.out.println("CodiVO 생성자 호출");
        this.codiNo = codiNo;
    }

    public CodiVO(String userID) {
        System.out.println("CodiVO 생성자 호출");
        this.userID = userID;
    }

    public CodiVO(String userID, String fileName) {
        System.out.println("CodiVO 생성자 호출");
        this.userID = userID;
        this.fileName = fileName;
    }


    //게터&세터
    public int getCodiNo() {
        return codiNo;
    }

    public void setCodiNo(int codiNo) {
        this.codiNo = codiNo;
    }

    public String getCodiName() {
        return codiName;
    }

    public void setCodiName(String codiName) {
        this.codiName = codiName;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
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

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }





}
