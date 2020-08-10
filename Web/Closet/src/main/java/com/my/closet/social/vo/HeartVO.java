package com.my.closet.social.vo;

import org.springframework.stereotype.Component;

@Component("heartVO")
public class HeartVO {

	private String boardNo, hearterID, regDate;
	
	//생성자
	public HeartVO() {
		System.out.println("HeartVO 생성자 호출");
	}	

	

	public String getBoardNo() {
		return boardNo;
	}



	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}



	public String getHearterID() {
		return hearterID;
	}



	public void setHearterID(String hearterID) {
		this.hearterID = hearterID;
	}



	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
