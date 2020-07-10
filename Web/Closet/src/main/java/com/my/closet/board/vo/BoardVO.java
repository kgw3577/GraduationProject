package com.my.closet.board.vo;
import org.springframework.stereotype.Component;

@Component("boardVO")
public class BoardVO {
	
	private int boardNo; // PRIMARY KEY. AUTO INCREMENT
	private String boardType; //not null. 게시글 타입
	private String userID; //FOREIGN KEY(USER). not null 
	private String fileName;
	private String filePath;
	private String subject;  //not null. 게시글 제목
	private String contents;
	private String regDate; // DEFAULT CURRENT_TIMESTAMP
	private int numHeart;

	//임시
	private int pageStart =-1;
	private int pageSize =-1;
	

	//생성자
	public BoardVO() {
		System.out.println("CloBoardVO 생성자 호출");
	}


	
	//게터&세터
	public int getBoardNo() {
		return boardNo;
	}


	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}


	public String getBoardType() {
		return boardType;
	}


	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
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


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContents() {
		return contents;
	}


	public void setContents(String contents) {
		this.contents = contents;
	}


	public String getRegDate() {
		return regDate;
	}


	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}


	public int getNumHeart() {
		return numHeart;
	}


	public void setNumHeart(int numHeart) {
		this.numHeart = numHeart;
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