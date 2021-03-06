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
<title>코디 정보 출력창</title>
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
	<p class="cls1">코디 정보</p>
	<table align="center" border="1">
		<tr align='center' bgcolor='lightgreen'>
			<td width="7%"><b>번호</b></td>
			<td width="7%"><b>이름</b></td>
			<td width="7%"><b>계절</b></td>
			<td width="7%"><b>장소</b></td>
			<td width="7%"><b>구입일</b></td>
			<td width="7%"><b>코멘트</b></td>
			<td width="7%"><b>즐겨찾기</b></td>
			<td width="7%"><b>이미지</b></td>
			<td width="7%"><b>파일이름</b></td>
			<td width="7%"><b>파일경로</b></td>
			<td width="7%"><b>등록일</b></td>
			<td width="7%"><b>소유자</b></td>
			<td width="7%"><b>코디 정보</b></td>
			<td width="7%"><b>삭제하기</b></td>
		</tr>

		<c:choose>
			<c:when test="${codiList==null}">
				<tr>
					<td colspan=7><b>등록된 코디가 없습니다.</b></td>
				</tr>
			</c:when>
			<c:when test="${codiList!=null}">
				<c:forEach var="codi" items="${codiList}"> <!-- 컨트롤러에서 바인딩한 codiList에 바로 접근 -->
					<tr align='center'>
						<td>${codi.codiNo}</td>
						<td>${codi.codiName}</td>
						<td>${codi.season}</td>
						<td>${codi.place}</td>
						<td>${codi.buyDate}</td>
						<td>${codi.comment}</td>
						<td>${codi.favorite}</td>
						<td><img src="${contextPath}${codi.filePath}" style="width:150px"></td>
						<td>${codi.fileName}</td>
						<td>${codi.filePath}</td>
						<td>${codi.addedDate}</td>
						<td>${codi.userID}</td>

						<td><a href="${contextPath}/codi/info/${codi.codiNo}">코디 정보 보기</a></td>
						<td>
						<form method="post" action="/codi/delete/${codi.codiNo}">
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