<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function selChange() {
		 var sel = document.getElementById('cntPerPage').value;
		
		 location.href="list.do?nowPage=${paging.nowPage}&cntPerPage="+sel;
	}

</script> 
</head>
<body>
<h1>list</h1>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

 	<div style="float: right;">
		<select id="cntPerPage" name="sel" onchange="selChange()">
			<option value="10"
				<c:if test="${paging.cntPerPage == 10}">selected</c:if>>10줄 보기</option>
			<option value="20"
				<c:if test="${paging.cntPerPage == 20}">selected</c:if>>20줄 보기</option>
			<option value="30"
				<c:if test="${paging.cntPerPage == 30}">selected</c:if>>30줄 보기</option>
			<option value="50"
				<c:if test="${paging.cntPerPage == 50}">selected</c:if>>50줄 보기</option>
		</select>
	</div> <!-- 옵션선택 끝 -->
	
	<form align="left" name="form11" method="post" action="/member/list" ">
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
			<th width="30%">file</th>
			<th>joindate</th>
			<th>modifydate</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="uvo" items="${selectAll}">
			<tr>
				<td>${uvo.id}</td>
				<td>${uvo.pwd}</td>
				<td><a href="/member/view?id=${uvo.id}">${uvo.name}</a></td>
				<td>${uvo.email}</td>
				<td>${uvo.fileName}</td>
				<td><fmt:formatDate value="${uvo.joindate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${uvo.modifydate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<div style="display: block; text-align: center;">		
		<c:if test="${paging.startPage != 1 }">
			<a href="${contextPath}/member/list?nowPage=${paging.startPage - 1 }&cntPerPage=${paging.cntPerPage}">[이전]</a> <!-- < -->
		</c:if>
		<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="p">
			<c:choose>
				<c:when test="${p == paging.nowPage }">
					<b>${p }</b>
				</c:when>
				<c:when test="${p != paging.nowPage }">
					<a href="${contextPath}/member/list?nowPage=${p }&cntPerPage=${paging.cntPerPage}">${p }</a>
				</c:when>
			</c:choose>
		</c:forEach>
		<c:if test="${paging.endPage != paging.lastPage}">
			<a href="${contextPath}/member/list?nowPage=${paging.endPage+1 }&cntPerPage=${paging.cntPerPage}">[다음]</a><!-- > -->
		</c:if>
	</div>
</body>
</html>