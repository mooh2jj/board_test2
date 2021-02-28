<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기창</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#btnSave').click(function(){
			// 절차적으로 잘 적었는지 체크!
			var title = $('#title').val();
			var content = $('#content').val();
			var writer = $('#writer').val();
			
			if(title == "" || title.length == 0){
				alert("제목을 입력하세요");
				document.form1.title.focus();	// 안쓴거 해당입력창에 포커스!
				return;
			}
			
			if(content == "" || content.length == 0){
				alert("내용을 입력하세요");
				document.form1.content.focus();	// 안쓴거 해당입력창에 포커스! 
				return;
			}
			
			if(writer == "" || writer.length == 0){
				alert("이름을 입력하세요");
				document.form1.writer.focus();	// 안쓴거 해당입력창에 포커스!
				return;
			}
			
			document.form1.submit();
		});
	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<h2>글 작성</h2>

	<form name="form1" action="${contextPath}/board/insert.do">
		
		<div>
			제목 
			<input name="title" id="title" size="80" placeholder="제목을 입력해주세요" />
		</div>
		<div>
			내용
			<textarea name="content" id="content" rows="4" cols="80" placeholder="내용을 입력해주세요"></textarea>
		</div>
		<div>
			글쓴이 
			<input name="writer" id="writer" size="80" placeholder="이름을 입력해주세요" />
		</div>
		<div>
			<button type="button" id="btnSave">확인</button>
			<button type="reset" id="btnCancle">취소</button>
		</div>
	</form>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>	
</body>
</html>