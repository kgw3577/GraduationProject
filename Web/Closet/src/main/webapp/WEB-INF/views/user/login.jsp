<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   request.setCharacterEncoding("UTF-8");
%> 
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>로그인 결과창</title>
</head>
<body>
<table border="1" width="50%" align="center" >

   <tr align="center">
      <td>아이디</td>
      <td>비밀번호</td>
   </tr>
   <tr align="center">
      <td>${id}</td>
      <td>${pwd}</td>
   </tr>
</table>
answer : ${answer}

</body>
</html>
