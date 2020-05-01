package com.my.closet.closet.vo;
import org.springframework.stereotype.Component;

@Component("closetVO")
public class ClosetVO {
	private String userID;		//PRIMARY KEY, FOREIGN KEY(USER)
	private String closetName; 	//PRIMARY KEY
	private String closetLocation;
	private String closetMemo;
	private String closetOpen;
	
	//생성자
	public ClosetVO() {
		System.out.println("ClosetVO 생성자 호출");
	}
	
	public ClosetVO(String userID, String closetName) {
		System.out.println("ClosetVO 생성자 호출");
		this.userID=userID;
		this.closetName=closetName;
	}
	
	public ClosetVO(String userID, String closetName, String closetOpen) {
		System.out.println("ClosetVO 생성자 호출");
		this.userID=userID;
		this.closetName=closetName;
		this.closetOpen=closetOpen;
	}
	
	//getter & setter
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

	public String getClosetLocation() {
		return closetLocation;
	}

	public void setClosetLocation(String closetLocation) {
		this.closetLocation = closetLocation;
	}

	public String getClosetMemo() {
		return closetMemo;
	}

	public void setClosetMemo(String closetMemo) {
		this.closetMemo = closetMemo;
	}

	public String getClosetOpen() {
		return closetOpen;
	}

	public void setClosetOpen(String closetOpen) {
		this.closetOpen = closetOpen;
	}
	
}
