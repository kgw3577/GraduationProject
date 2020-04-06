<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  />  

<%
	request.setCharacterEncoding("UTF-8");
%>

<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 출력창</title>
<style>
.cls1 {
	font-size: 40px;
	text-align: center;
}

.cls2 {
	font-size: 20px;
	text-align: center;
}
</style>
</head>
<body>
	<p class="cls1">회원정보</p>
	<table align="center" border="1">
		<tr align='center' bgcolor='lightgreen'>
			<td width="7%"><b>아이디</b></td>
			<td width="7%"><b>비밀번호</b></td>
			<td width="7%"><b>이름</b></td>
			<td width="7%"><b>성별</b></td>
			<td width="7%"><b>나이</b></td>
			<td width="7%"><b>이메일</b></td>
			<td width="7%"><b>삭제</b></td>
		</tr>

		<c:choose>
			<c:when test="${userList==null}">
				<tr>
					<td colspan=7><b>등록된 회원이 없습니다.</b></td>
				</tr>
			</c:when>
			<c:when test="${userList!=null}">
				<c:forEach var="user" items="${userList}"> <!-- 컨트롤러에서 바인딩한 usersList에 바로 접근 -->
					<tr align='center'>
						<td>${user.id}</td>
						<td>${user.pwd}</td>
						<td>${user.name}</td>
						<td>${user.gender}</td>
						<td>${user.age}</td>
						<td>${user.mail}</td>
						<td><a href="${contextPath}/user/delete.do?id=${user.id}">삭제하기</a></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	answer : ${answer}
	<a href="${contextPath}/user/userForm.do">
		<p class="cls2">회원가입하기</p>
	</a>
	<a href="${contextPath}/user/loginForm.do">
		<p class="cls2">로그인하기</p>
	</a>
	<a href="${contextPath}/user/modifyForm.do">
		<p class="cls2">회원정보 수정하기</p>
	</a>
	<a href="${contextPath}/user/myInfoForm.do">
		<p class="cls2">내 정보 확인하기</p>
	</a>
</body>
</html>


<%-- 
		<%
			for (int i = 0; i < usersList.size(); i++) {
				UserVO userVO = (UserVO) usersList.get(i);
				String id = userVO.getId();
				String pwd = userVO.getPwd();
				String name = userVO.getName();
				String gender = userVO.getGender();
				String age = userVO.getAge();
		%>
		<tr align='center'>
			<td><%=id%></td>
			<td><%=pwd%></td>
			<td><%=name%></td>
			<td><%=gender%></td>
			<td><%=age%></td>
		</tr>
		
		<%
			}
		%>
		
--%>