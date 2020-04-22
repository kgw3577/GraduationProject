package com.my.closet.user.vo;

import org.springframework.stereotype.Component;

@Component("loginVO")
public class LoginVO {
	private String id;
	private String pwd;
	
	
	public LoginVO() {
		System.out.println("LoginVO 생성자 호출");
	}
	
	//getter & setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
