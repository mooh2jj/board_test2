<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />	    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${contextPath}/upload/uploadFileSet" method="post" 
	enctype="multipart/form-data" target="iframe1">	<!-- 제출된 파일결과가 iframe에 간다! -->
		<input type="file" name="file">
		<input type="submit" value="upload">
	</form>
	
	<iframe name="iframe1"></iframe>
</body>
</html>