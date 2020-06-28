package com.my.closet.codi.vo;
import org.springframework.stereotype.Component;

@Component("codiVO")
public class CodiVO {
	
	private int codiNo; // PRIMARY KEY. AUTO INCREMENT
	private String codiName;
	private String season; //계절
	private String where; //장소
	private String buyDate; //구입 날짜
	private String comment; //코멘트 500자
	private String like; //즐겨찾기 여부, DEFAULT 'no'
	private String fileName;
	private String filePath;
	private String addedDate; //등록일, DEFAULT CURRENT_TIMESTAMP
	private String userID; // FOREIGN KEY(CLOSET). not null
	
	//임시
	private int pageStart =-1;
	private int pageSize =-1;
	

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

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
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

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
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
	
	
	
	
}
