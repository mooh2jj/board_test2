<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>          
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>header</title>
</head>
<body>
<a href="/member/join">회원가입</a>
	<c:if test="${sessionScope.id != null}">
		${sessionScope.name}(${sessionScope.id})님 환영합니다!
		<a href="/member/logout">로그아웃</a>				
	</c:if>
	<c:if test="${sessionScope.id == null}">
		<a href="/member/home">로그인</a>
	</c:if>
	<br>
	<br>
	<hr>	
</body>
</html>