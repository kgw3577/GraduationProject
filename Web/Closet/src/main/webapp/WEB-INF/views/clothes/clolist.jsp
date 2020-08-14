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
<title>옷 정보 출력창</title>
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
	<p class="cls1">옷 정보</p>
	<table align="center" border="1">
		<tr align='center' bgcolor='lightgreen'>
			<td width="7%"><b>번호</b></td>
			<td width="7%"><b>카테고리</b></td>
			<td width="7%"><b>세부 카테고리</b></td>
			<td width="7%"><b>색상</b></td>
			<td width="7%"><b>계절</b></td>
			<td width="7%"><b>브랜드</b></td>
			<td width="7%"><b>사이즈</b></td>
			<td width="7%"><b>구매일</b></td>
			<td width="7%"><b>이미지</b></td>
			<td width="7%"><b>파일이름</b></td>
			<td width="7%"><b>파일경로</b></td>
			<td width="7%"><b>즐겨찾기</b></td>
			<td width="7%"><b>소유자</b></td>
			<td width="7%"><b>옷장이름</b></td>
			<td width="7%"><b>등록시간</b></td>
			<td width="7%"><b>옷 정보</b></td>
			<td width="7%"><b>삭제하기</b></td>
		</tr>

		<c:choose>
			<c:when test="${clothesList==null}">
				<tr>
					<td colspan=7><b>등록된 옷이 없습니다.</b></td>
				</tr>
			</c:when>
			<c:when test="${clothesList!=null}">
				<c:forEach var="clo" items="${clothesList}"> <!-- 컨트롤러에서 바인딩한 clothesList에 바로 접근 -->
					<tr align='center'>
						<td>${clo.cloNo}</td>
						<td>${clo.category}</td>
						<td>${clo.detailCategory}</td>
						<td>${clo.color}</td>
						<td>${clo.season}</td>
						<td>${clo.brand}</td>
						<td>${clo.cloSize}</td>
						<td>${clo.buyDate}</td>
						<td><img src="${contextPath}${clo.filePath}" style="width:150px"></td>
						<td>${clo.fileName}</td>
						<td>${clo.filePath}</td>
						<td>${clo.favorite}</td>
						<td>${clo.userID}</td>
						<td>${clo.closetName}</td>
						<td>${clo.regDate}</td>

						<td><a href="${contextPath}/clothes/info/${clo.cloNo}">내 옷 정보 보기</a></td>
						<td>
						<form method="post" action="/clothes/delete/${clo.cloNo}">
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