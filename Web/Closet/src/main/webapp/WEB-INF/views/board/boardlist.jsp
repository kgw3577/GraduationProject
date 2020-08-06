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
<title>게시글 정보 출력창</title>
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
	<p class="cls1">게시글 정보</p>
	<table align="center" border="1">
		<tr align='center' bgcolor='lightgreen'>
			<td width="7%"><b>번호</b></td>
			<td width="7%"><b>작성자</b></td>
			<td width="7%"><b>이미지</b></td>
			<td width="7%"><b>파일이름</b></td>
			<td width="7%"><b>파일경로</b></td>
			<td width="7%"><b>내용</b></td>
			<td width="7%"><b>작성 날짜</b></td>
			<td width="7%"><b>게시글 정보</b></td>
			<td width="7%"><b>삭제하기</b></td>
		</tr>

		<c:choose>
			<c:when test="${boardList==null}">
				<tr>
					<td colspan=7><b>등록된 옷이 없습니다.</b></td>
				</tr>
			</c:when>
			<c:when test="${boardList!=null}">
				<c:forEach var="board" items="${boardList}"> <!-- 컨트롤러에서 바인딩한 boardthesList에 바로 접근 -->
					<tr align='center'>
						<td>${board.boardNo}</td>
						<td>${board.userID}</td>
						<td><img src="${contextPath}${board.filePath}" style="width:150px"></td>
						<td>${board.fileName}</td>
						<td>${board.filePath}</td>
						<td>${board.contents}</td>
						<td>${board.regDate}</td>
						<td><a href="${contextPath}/board/info/${board.boardNo}">게시글 정보 보기</a></td>
						<td>
						<form method="post" action="/board/delete/${board.boardNo}">
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