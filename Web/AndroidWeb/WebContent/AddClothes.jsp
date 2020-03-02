<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="clothes.ClothesDAO" %>    
    
<%
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String type = request.getParameter("type");//로그인 요청인지 회원가입 요청인지를 구분하여 메서드를 실행하도록 합니다.
                                                
	
	//인스턴스 생성
	ClothesDAO User = new ClothesDAO(); 
	
	/*
	if("login".equals(type)) {
		if(!check.isEmail(id)) {
			out.print("email");
		} else {
		String returns = User.logindb(id, pwd);
		out.print(returns);
		}
	} 
	*/

%>