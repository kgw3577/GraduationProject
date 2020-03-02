<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="clothes.ClothesDAO" %>

<jsp:useBean  id="c"  class="clothes.ClothesVO"  scope="page"/>
<jsp:setProperty name="c" property="*" />

<%
	request.setCharacterEncoding("UTF-8");
	String type = request.getParameter("type"); //로그인 요청인지 회원가입 요청인지 구분하는 파라미터
	
	//인스턴스 생성
	ClothesDAO clothesDao = new ClothesDAO();

	//옷 추가
	if(type != null && "add".equals(type)){
		out.print(clothesDao.add(c)); //add 실행 후 결과 전송
		
	}
	//옷 삭제
	else if(type != null && "delete".equals(type)){
		out.print(clothesDao.delete(c)); //delete 실행 후 결과 전송
	
	}
	//옷 정보 조회
	else if(type != null && "info".equals(type)) {
		out.print(clothesDao.info(c));
	}
	
%>