<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>

<jsp:useBean  id="u"  class="user.UserVO"  scope="page"/>
<jsp:setProperty name="u" property="*" />

<%
	request.setCharacterEncoding("UTF-8");
	String type = request.getParameter("type"); //로그인 요청인지 회원가입 요청인지 구분하는 파라미터
	
	//인스턴스 생성
	UserDAO userDao = new UserDAO(); 

	//회원가입
	if(type != null && "join".equals(type))
		out.print(userDao.join(u)); //join 실행 후 결과 전송
	
	//아이디 찾기
	else if(type != null && "forget-id".equals(type)) {

	} 
	//비밀번호 찾기
	else if(type != null && "forget-pass".equals(type)) {

	}
	
%>