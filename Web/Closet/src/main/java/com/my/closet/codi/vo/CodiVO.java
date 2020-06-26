package com.my.closet.codi.vo;

import org.springframework.stereotype.Component;

@Component("codiVO")
public class CodiVO {
	private int no; // PRIMARY KEY. AUTO INCREMENT
	private String name;
	private String category;
	private String date;
	private String season;
	private String img;
	private String fileName;
	private String filePath;
	private String like;
	private String userID; // FOREIGN KEY(CLOSET). not null 
	private String closetName; // FOREIGN KEY(CLOSET). not null 
	
	
}
