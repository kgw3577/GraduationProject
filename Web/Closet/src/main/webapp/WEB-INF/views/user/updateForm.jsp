<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    





<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%> 


<!DOCTYPE html >
<html>
<head>
<meta "charset=utf-8">
<title>회원정보 수정하기</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>  
<script>
  $(function() {
      $("#checkJson").click(function() {
      	var article = {
      			userID:"bcde", 
	            nickname:"바람의 나라",
	            email:"on@naver.com",
	            pwd:"1", 
	            gender:"여자",
	            birth:"1977-01-05",
	            emailChecked:"no",
	            pfImageName:"default07.png",
	            pfImagePath:"/download/profile?imageFileName=default07.png",
	            pfContents:"인스타 @bcde 친추 부탁"
	            };
  
  	$.ajax({
  	    type:"PUT",
        url:"${contextPath}/user/modify",
        contentType: "application/json",
        data :JSON.stringify(article),
      success:function (data,textStatus){
          alert(data);
      },
      error:function(data,textStatus){
        alert("에러가 발생했습니다.");ㅣ
      },
      complete:function(data,textStatus){
      }
   });  //end ajax	

   });
});
</script>
</head>
<body>
<h1>파일 업로드 하기</h1>
<form method="post" action="${contextPath}/user/modify">
	<label>id:</label>
    <input type="text" name="userID"><br>
	<label>닉네임:</label>
    <input type="text" name="closetName"><br>
    <label>이메일:</label>
    <input type="text" name="name"><br>
    <label>비밀번호:</label>
    <input type="text" name="category"><br>
    <label>성별:</label>
    <input type="text" name="category"><br>
    <label>생년월일:</label>
    <input type="text" name="category"><br>
    <label>이메일 체크 여부:</label>
    <input type="text" name="category"><br>
    <label>프사 파일명:</label>
    <input type="text" name="category"><br>
    <label>프사 파일 경로:</label>
    <input type="text" name="category"><br>
    <label>자기소개:</label>
    <input type="text" name="category"><br>
	<input type="submit"  value="업로드"/>
</form>


<input type="button" id="checkJson" value="수정하기"/><br><br>
  <div id="output"></div>

</body>
</html>
