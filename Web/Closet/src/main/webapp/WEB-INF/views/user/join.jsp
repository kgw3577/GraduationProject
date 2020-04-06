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
  <title>회원가입 결과창</title>
</head>
<body>
<table border="1" width="50%" align="center" >

   <tr align="center">
      <td>아이디</td>
      <td>비밀번호</td>
      <td>이름</td>
      <td>성별</td>
      <td>나이</td>
      <td>이메일</td>
   </tr>
   <tr align="center">
      <td>${info.id}</td>
      <td>${info.pwd}</td>
      <td>${info.name}</td>
      <td>${info.gender}</td>
      <td>${info.age}</td>
      <td>${info.mail}</td>
   </tr>
</table>
answer : ${answer}

</body>
</html>
