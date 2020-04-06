package com.my.closet.user.vo;

import org.springframework.stereotype.Component;

@Component("userVO")
public class UserVO {
	private String id;
	private String name;
	private String mail;
	private String pwd;
	private String gender;
	private String age;
	private String emailChecked;
	
	
	//생성자
	public UserVO() {
		System.out.println("UserVO 생성자 호출");
	}
	
	public UserVO(String id) {
		System.out.println("UserVO 생성자 호출");
		this.id = id;
	}

	public UserVO(String id, String pwd) {
		System.out.println("UserVO 생성자 호출");
		this.id = id;
		this.pwd = pwd;
	}

	public UserVO(String id, String pwd, String name, String mail) {
		System.out.println("UserVO 생성자 호출");
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.mail = mail;
	}
	
	
	//getter & setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEmailChecked() {
		return emailChecked;
	}

	public void setEmailChecked(String emailChecked) {
		this.emailChecked = emailChecked;
	}
	
}
