<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script> -->
<script type="text/javascript">
/* 	$(document).ready(function(){
		var loginForm = $("#loginForm");
		$("#loginBtn").on("click",function(e){
			e.preventDefault();
			loginForm.attr("action","/member/login");
			loginForm.attr("method","post");
			loginForm.submit();
		});
		
	}); */
	function login(){
		loginForm.action = "/member/login";
		loginForm.method = "post";
		loginForm.submit();
	}
</script>
</head>
<body>
	<h3>home</h3>
	<a href="/member/join">회원가입</a>
	<form name="loginForm">
		id :<input type="text" name="id"/><br>
		pw :<input type="text" name="pwd"/><br>
		<button id="loginBtn" onclick="login();">로그인</button>
	</form>
	<c:if test="${msg == 'join' }">
		<p style="color:red;">회원가입이 완료되었습니다. 로그인을 시도해주세요!</p>
	</c:if>
	<c:if test="${msg == 'false' }">
		<p style="color:red;">로그인 실패! id와 pwd를 확인해주세요.</p>
	</c:if>
	<c:if test="${msg == 'logout' }">
		<p style="color:#3F0099;">로그아웃되었습니다.</p>
	</c:if>
	
</body>
</html>