package com.my.closet.user.vo;

import org.springframework.stereotype.Component;

@Component("userVO")
public class UserVO {
	private String userID; //유저 아이디 (45). NOT NULL PRIMARY
	private String nickname; //닉네임 (50)
	private String email; //이메일 (70)
	private String pwd; //비밀번호 (45)
	private String gender; //성별 (10)
	private String birth; //생년월일 (DATE)
	private String emailChecked; //메일 체크 여부 (45)
	private String pfImageName; //프로필 이미지 파일명 (50)
	private String pfImagePath; //프로필 이미지 파일 경로 (80)
	private String pfContents; //프로필 소개글 (300)
	
	//임시
	private int pageStart =-1;
	private int pageSize =-1;
	
	//생성자
	public UserVO() {
		System.out.println("UserVO 생성자 호출");
	}
	
	public UserVO(String userID) {
		System.out.println("UserVO 생성자 호출");
		this.userID = userID;
	}

	public UserVO(String userID, String pwd) {
		System.out.println("UserVO 생성자 호출");
		this.userID = userID;
		this.pwd = pwd;
	}
	
	public UserVO(String userID, String nickname, String email) {
		System.out.println("UserVO 생성자 호출");
		this.userID = userID;
		this.nickname = nickname;
		this.email = email;
	}

	public UserVO(String userID, String pwd, String nickname, String email) {
		System.out.println("UserVO 생성자 호출");
		this.userID = userID;
		this.pwd = pwd;
		this.nickname = nickname;
		this.email = email;
	}

	

	//getter & setter
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmailChecked() {
		return emailChecked;
	}

	public void setEmailChecked(String emailChecked) {
		this.emailChecked = emailChecked;
	}

	public String getPfImageName() {
		return pfImageName;
	}

	public void setPfImageName(String pfImageName) {
		this.pfImageName = pfImageName;
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
