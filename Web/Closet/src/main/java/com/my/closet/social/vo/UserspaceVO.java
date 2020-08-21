package com.my.closet.social.vo;
import org.springframework.stereotype.Component;

@Component("userspaceVO")
public class UserspaceVO {

	private String userID, nickname, pfImagePath, pfContents, 
	if_following, following_friendsID, following_friendsName, following_friendsImgPath;
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




	public String getFollowing_friendsID() {
		return following_friendsID;
	}


	public void setFollowing_friendsID(String following_friendsID) {
		this.following_friendsID = following_friendsID;
	}


	public String getFollowing_friendsName() {
		return following_friendsName;
	}


	public void setFollowing_friendsName(String following_friendsName) {
		this.following_friendsName = following_friendsName;
	}


	public String getFollowing_friendsImgPath() {
		return following_friendsImgPath;
	}


	public void setFollowing_friendsImgPath(String following_friendsImgPath) {
		this.following_friendsImgPath = following_friendsImgPath;
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