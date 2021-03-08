<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list!!</title>
<script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
<script type="text/javascript">
function selChange() {
	 var sel = document.getElementById('cntPerPage').value;
	
	 location.href="list.do?nowPage=${paging.nowPage}&cntPerPage="+sel;
}

function excelDownF() {
	 location.href="excelDown.do";
}

</script> 
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<!-- 	
	<button type="button" id="btnWrite">글쓰기</button>
 -->
 	<h2 align="center">글 목록</h2>
	
	<form align="center" name="form11" method="post" action="${contextPath}/board/list.do">
		<select name="searchOption">
			<option value="all" <c:if test="${map.searchOption == 'all'}">selected</c:if>>제목+이름+내용</option>
			<%-- <option value="writer" <c:if test="${map.searchOption == 'writer'}">selected</c:if>>이름</option> --%>
			<option value="userName" <c:if test="${map.searchOption == 'userName'}">selected</c:if>>이름</option>
			<option value="content" <c:if test="${map.searchOption == 'content'}">selected</c:if>>내용</option>
			<option value="title" <c:if test="${map.searchOption == 'title'}">selected</c:if>>제목</option>
		</select>
		<input name="keyword" value="${map.keyword}">
		<!-- <input type="text" name="keyword" /> -->
		<input type="submit" value="조회"/>
	</form>	
	<div><button type="button" onclick="excelDownF();">엑셀출력</div>
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
	
	<table border="1" align="center">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		<c:forEach var="row" items="${selectAll}">
		<tr>
			<td>${row.bno}</td>
			<td>
				<a href="${contextPath}/board/view.do?bno=${row.bno}">${row.title}
					<!-- 댓글이 있으면 게시글 이름 옆에 출력하기 -->
					<c:if test="${row.recnt > 0}">
						<span style="color: red;">(${row.recnt})
						</span>
					</c:if>
				</a>
			</td>
			<%-- <td>${row.writer}</td> --%>
			<td>${row.userName}</td>
			<td><fmt:formatDate value="${row.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${row.viewcnt}</td>
		</tr>
		</c:forEach>
	</table>

	<div style="display: block; text-align: center;">		
		<c:if test="${paging.startPage != 1 }">
			<a href="${contextPath}/board/list.do?nowPage=${paging.startPage - 1 }&cntPerPage=${paging.cntPerPage}">&lt;</a> <!-- < -->
		</c:if>
		<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="p">
			<c:choose>
				<c:when test="${p == paging.nowPage }">
					<b>${p }</b>
				</c:when>
				<c:when test="${p != paging.nowPage }">
					<a href="${contextPath}/board/list.do?nowPage=${p }&cntPerPage=${paging.cntPerPage}">${p }</a>
				</c:when>
			</c:choose>
		</c:forEach>
		<c:if test="${paging.endPage != paging.lastPage}">
			<a href="${contextPath}/board/list.do?nowPage=${paging.endPage+1 }&cntPerPage=${paging.cntPerPage}">&gt;</a><!-- > -->
		</c:if>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>	
</body>
</html>