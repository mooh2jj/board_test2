<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/common.css"  type="text/css">
</head>
<body>
<%@ include file="/WEB-INF/views/menu.jsp" %>
<header class="header_class1">
	<h3>header</h3>
<%-- 	<a href="${contextPath}/board/write.do">글등록하기</a> --%>

</header>
</body>
</html>