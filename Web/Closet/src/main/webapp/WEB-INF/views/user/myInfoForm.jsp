<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 정보 보기</title>
</head>

<body>
	<form name="frmLogin" method="post"
		action="${contextPath}/user/myInfo.do">
		<table border="1" width="80%" align="center">
			<tr align="center">
				<td>아이디</td>
			</tr>
			<tr align="center">
				<td><input type="text" name="id" value="" size="20"></td>
			</tr>
			<tr align="center">
				<td colspan="2"><input type="submit" value="확인하기"> <input
					type="reset" value="다시입력"></td>
			</tr>
		</table>
	</form>
</body>
</html>
