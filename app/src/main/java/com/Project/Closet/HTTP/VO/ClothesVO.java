package com.Project.Closet.HTTP.VO;

import android.os.Parcel;
import android.os.Parcelable;

public class ClothesVO implements Parcelable {
    private int cloNo; // PRIMARY KEY. AUTO INCREMENT
    private String location;
    private String kind;
    private String category; // not null
    private String detailCategory; // not null
    private String color;
    private String identifier;
    private String season;
    private String brand;
    private String cloSize;
    private String buyDate;
    private String fileName;
    private String filePath;
    private String favorite;
    private String userID; // FOREIGN KEY(USER). not null
    private String closetName; // FOREIGN KEY(CLOSET). not null
    private String regDate;

    //임시
    private int pageStart =-1;
    private int pageSize =-1;




    protected ClothesVO(Parcel in) {
        cloNo = in.readInt();
        location = in.readString();
        kind = in.readString();
        category = in.readString();
        detailCategory = in.readString();
        color = in.readString();
        identifier = in.readString();
        season = in.readString();
        brand = in.readString();
        cloSize = in.readString();
        buyDate = in.readString();
        fileName = in.readString();
        filePath = in.readString();
        favorite = in.readString();
        userID = in.readString();
        closetName = in.readString();
        regDate = in.readString();
        pageStart = in.readInt();
        pageSize = in.readInt();
    }

    public static final Creator<ClothesVO> CREATOR = new Creator<ClothesVO>() {
        @Override
        public ClothesVO createFromParcel(Parcel in) {
            return new ClothesVO(in);
        }

        @Override
        public ClothesVO[] newArray(int size) {
            return new ClothesVO[size];
        }
    };

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }




    //생성자
    public ClothesVO() {
        System.out.println("ClothesVO 생성자 호출");
    }

    public ClothesVO(int no) {
        System.out.println("ClothesVO 생성자 호출");
        this.cloNo = no;
    }

    public ClothesVO(String userID) {
        System.out.println("ClothesVO 생성자 호출");
        this.userID = userID;
    }

    public ClothesVO(String userID, String closetName, String fileName) {
        System.out.println("ClothesVO 생성자 호출");
        this.userID = userID;
        this.closetName = closetName;
        this.fileName = fileName;
    }
    public ClothesVO(int cloNo, String location, String kind, String category, String detailCategory,
                     String color, String identifier,
                     String fileName, String filePath, String favorite, String userID, String closetName){
        this.cloNo = cloNo;
        this.location =location;
        this.kind =kind;
        this.category= category;
        this.detailCategory=detailCategory;
        this.color=color;
        this.identifier=identifier;
        this.fileName=fileName;
        this.filePath=filePath;
        this.favorite=favorite;
        this.userID=userID; // FOREIGN KEY(USER). not null
        this.closetName=closetName; // FOREIGN KEY(CLOSET). not null
    }

    //get set
    public int getCloNo() {
        return cloNo;
    }

    public void setCloNo(int cloNo) {
        this.cloNo = cloNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetailCategory() {
        return detailCategory;
    }

    public void setDetailCategory(String detailCategory) {
        this.detailCategory = detailCategory;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCloSize() {
        return cloSize;
    }

    public void setCloSize(String cloSize) {
        this.cloSize = cloSize;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
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

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
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

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cloNo);
        dest.writeString(location);
        dest.writeString(kind);
        dest.writeString(category);
        dest.writeString(detailCategory);
        dest.writeString(color);
        dest.writeString(identifier);
        dest.writeString(season);
        dest.writeString(brand);
        dest.writeString(cloSize);
        dest.writeString(buyDate);
        dest.writeString(fileName);
        dest.writeString(filePath);
        dest.writeString(favorite);
        dest.writeString(userID);
        dest.writeString(closetName);
        dest.writeString(regDate);
        dest.writeInt(pageStart);
        dest.writeInt(pageSize);
    }
}
