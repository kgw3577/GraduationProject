package com.my.closet.clothes.vo;

import org.springframework.stereotype.Component;

@Component("clothesVO")
public class ClothesVO {
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

	
	
	
	
}
