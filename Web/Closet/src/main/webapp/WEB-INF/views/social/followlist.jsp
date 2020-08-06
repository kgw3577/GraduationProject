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
<title>팔로우 정보 출력창</title>
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
	<p class="cls1">팔로우 정보</p>
	<table align="center" border="1">
		<tr align='center' bgcolor='lightgreen'>
			<td width="7%"><b>팔로워</b></td>
			<td width="7%"><b>팔로우됨</b></td>
			<td width="7%"><b>작성일</b></td>
		</tr>

		<c:choose>
			<c:when test="${followList==null}">
				<tr>
					<td colspan=7><b>등록된 팔로우가 없습니다.</b></td>
				</tr>
			</c:when>
			<c:when test="${followList!=null}">
				<c:forEach var="follow" items="${followList}"> <!-- 컨트롤러에서 바인딩한 followthesList에 바로 접근 -->
					<tr align='center'>
						<td>${follow.followerID}</td>
						<td>${follow.followedID}</td>
						<td>${follow.regDate}</td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
</body>
</html>
