<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>list</h1>

<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<form align="center" name="form11" method="post" action="/member/list">
		<select name="searchOption">
			<option value="all" <c:if test="${map.searchOption == 'all'}">selected</c:if>>id+pwd+name</option>
			<option value="id" <c:if test="${map.searchOption == 'id'}">selected</c:if>>id</option>
			<option value="pwd" <c:if test="${map.searchOption == 'pwd'}">selected</c:if>>pwd</option>
			<option value="name" <c:if test="${map.searchOption == 'name'}">selected</c:if>>name</option>
		</select>
		<input name="keyword" value="${map.keyword}">
		<!-- <input type="text" name="keyword" /> -->
		<input type="submit" value="조회"/>
	</form>	

<table border="1">
	<thead >
	<tr>
		<th>id</th>
		<th>pwd</th>
		<th>name</th>
		<th>email</th>
		<th>joinDate</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="uvo" items="${selectAll}">
		<tr>
			<td>${uvo.id}</td>
			<td>${uvo.pwd}</td>
			<td><a href="/member/view?id=${uvo.id}">${uvo.name}</a></td>
			<td>${uvo.email}</td>
			<td><fmt:formatDate value="${uvo.joindate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</c:forEach>
	</tbody>
</table>

</body>
</html>