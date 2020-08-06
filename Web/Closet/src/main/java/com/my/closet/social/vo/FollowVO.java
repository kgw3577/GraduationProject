package com.my.closet.social.vo;

import org.springframework.stereotype.Component;

@Component("followVO")
public class FollowVO {

	private String followerID, followedID, regDate;
	
	//생성자
	public FollowVO() {
		System.out.println("FollowVO 생성자 호출");
	}	

	public String getFollowerID() {
		return followerID;
	}

	public void setFollowerID(String followerID) {
		this.followerID = followerID;
	}

	public String getFollowedID() {
		return followedID;
	}

	public void setFollowedID(String followedID) {
		this.followedID = followedID;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
