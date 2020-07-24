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
			<td width="7%"><b>닉네임</b></td>
			<td width="7%"><b>이메일</b></td>
			<td width="7%"><b>비밀번호</b></td>
			<td width="7%"><b>성별</b></td>
			<td width="7%"><b>생년월일</b></td>
			<td width="7%"><b>이메일체크</b></td>
			<td width="7%"><b>프로필 사진</b></td>
			<td width="7%"><b>프사 파일명</b></td>
			<td width="7%"><b>프사 파일경로</b></td>
			<td width="7%"><b>자기 소개</b></td>
			<td width="7%"><b>내 정보</b></td>
			<td width="7%"><b>수정하기</b></td>
			<td width="7%"><b>삭제하기</b></td>
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
						<td>${user.userID}</td>
						<td>${user.nickname}</td>
						<td>${user.email}</td>
						<td>${user.pwd}</td>
						<td>${user.gender}</td>
						<td>${user.birth}</td>
						<td>${user.emailChecked}</td>
						<td><img src="${contextPath}${user.pfImagePath}" style="width:150px"></td>
						<td>${user.pfImageName}</td>
						<td>${user.pfImagePath}</td>		
						<td>${user.pfContents}</td>
						<td><a href="${contextPath}/user/myInfo/${user.userID}">내 정보 보기</a></td>
						<td><a href="${contextPath}/user/updateForm">내 정보 수정</a></td>
						<td>
						<form method="post" action="/user/delete/${user.userID}">
   						<input type="hidden" name="_method" value="delete"/>
   						<input type="submit" value="삭제" >
						</form>
						</td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	
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