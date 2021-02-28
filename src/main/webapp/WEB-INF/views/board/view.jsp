<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세창</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		listReply2();	// rest 방식
		
		$("#btnReply").click(function(){
			
			var replytext = $('#replytext').val();
			var bno = "${selectViewOne.bno}";
			var param = "replytext="+replytext+"&bno="+bno;
			
			$.ajax({
				type: "post",
				url: "/reply/insert",
				data: param,
				success: function(result){
					alert("댓글이 등록되었습니다.");
					listReply2();
				}
			});
			
		});
		
		$('#btnUpdate').click(function(){
			
			if(confirm("수정하시겠습니까?")){
				document.form1.action = "${contextPath}/board/update.do";
				document.form1.submit();
			}
		});
		$('#btnDelete').click(function(){
			
			if(confirm("삭제하시겠습니까?")){
				document.form1.action = "${contextPath}/board/delete.do";
				document.form1.submit();
			}
		});
		
		
	});
	

	// Rest방식 (Json)
	function listReply2() {
		
		$.ajax({
			type: "get",
			contentType: "application/json; charset=utf-8", // ==> 생략가능(RestController이기때문에 가능)
			dataType: "json",
			url: "${contextPath}/reply/listJson?bno=${selectViewOne.bno}",
			success: function (result) {
				console.log(result);
				/* alert(result); */
				var output = "<table>";

				for (var i in result) {
					output += "<tr>";
					/* output += "<td>"+result[i].userName; */
					output += "<td>"+result[i].replyer;
					output += "("+changeDate(result[i].regdate)+")<br>";
					output += result[i].replytext+"</td>";
					output += "<tr>";
				}
				output += "</table>";
				$("#listReply").html(output);
			}
		});
	}
	
	// **날짜 변환함수 작성
	function changeDate(date){
		date = new Date(parseInt(date));
		year = date.getFullYear();
		month = date.getMonth();
		day = date.getDate();
		hour = date.getHours();
		minute = date.getMinutes();
		second = date.getSeconds();
		strDate = year+"-"+month+"-"+day+"-"+hour+"-"+minute+":"+second;
		
		return strDate;
	}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<h2>상세글</h2>

	<form name="form1">
		
			<div>
				작성일자 : <fmt:formatDate value="${selectViewOne.regdate}" pattern="yyyy-MM-dd a HH:mm:ss"/>
				<!-- <fmt:formatDate value="${selectViewOne.regdate}" pattern="yyyy-MM-dd a HH:mm:ss"/> -->
			</div>
			<div>
				조회수 : ${selectViewOne.viewcnt} 
			</div>
			<div>
				제목
				<input name="title" id="title" value="${selectViewOne.title}">
			</div>
			<div>
				내용
				<textarea name="content" id="content" rows="4" cols="40" >${selectViewOne.content}</textarea>
			</div>
			<div>
				작성자
				<input name="writer" id="writer" value="${selectViewOne.writer}" readonly="readonly">
			</div>
			<div> 
			<input type="hidden" name="bno" value="${selectViewOne.bno}">
				<input type="button" value="수정" id="btnUpdate">
				<input type="button" value="삭제" id="btnDelete">
			</div>
	</form>
	<!-- 댓글 입력창 -->
	<div style="width: 650px; text-align: center;">
		<br>
			<textarea rows="5" cols="80" id="replytext" placeholder="댓글을 작성해주세요"></textarea>
			<br>
			<button type="button" id="btnReply">댓글 작성</button>
		</div>
		
		<!-- 댓글 출력할 자리 -->
		<div id="listReply"></div>
		
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>	
</body>
</html>