<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>view</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		var actionForm = $("#actionForm");
		$("#modify").on("click", function(e){
			
			if(confirm("수정하시겠습니까?")){
			//alert("hi modify");
				e.preventDefault();
				actionForm.attr("action","/member/modify");
				actionForm.attr("method","post");
				actionForm.submit();
			}
		});
		
		$("#delete").on("click", function(e){
			//alert("hi modify");
			if(confirm("삭제하시겠습니까?")){
				e.preventDefault();
				actionForm.attr("action","/member/delete");
				actionForm.attr("method","post");
				actionForm.submit();
			}
		});	
	});
</script>
</head>
<body>
<a href="/member/list">list</a>
<h3>view</h3>

<form id="actionForm" >
	id :<input type="text" name="id" value="${viewOne.id}" readonly="readonly"/><br>
	pw :<input type="text" name="pwd" value="${viewOne.pwd}"/><br>
	name :<input type="text" name="name" value="${viewOne.name}"/><br>
	email :<input type="text" name="email" value="${viewOne.email}"/><br>
	<button id="modify" type="button">modify</button>
	<button id="delete" type="button">delete</button>
</form>

</body>
</html>