<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>join</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script type="text/javascript">

$(document).ready(function(){
	$("#id-msg").hide();
	// 입력폼에서 벗어날 때 작동
	$("input[name='id']").blur(function(){
        $.ajax({
            type: "post",
            url: "/member/idcheck",
            data: {"id" : $("#id").val()},            // formData : ajax로 전달할 폼 객체 , 가상의 <form> 태그
            // formData.append("file", file); 한
            dataType : "json",
           	success: function(result){
               if(parseInt(result) == 1){
            		$("#id-msg").show(); 
               }else{
           			$("#id-msg").hide(); 
               }
            }
       });
	});
});

// 등록버튼 누를시 작동
function checkForm() {
    /* alert(id.value); */
    form.id.value = form.id.value.trim();
    if (form.id.value.length == 0) {
         alert("로그인 아이디를 입력해주세요.");
         form.id.focus();
         return false;
    }
    
    if( form.id.value.length < 4){
         alert('로그인 아이디를 4자 이상 입력해주세요.');
         form.id.focus();
         return false;
    }
    
    if( isAlphaNumeric(form.id.value) == false ){
         alert('로그인 아이디를 영문자 소문자와 숫자만 사용할 수 있습니다.');
         form.id.focus();
         
         return false;
    }
    
    form.id.value = form.id.value.toLowerCase();
    
    form.pwd.value = form.pwd.value.trim();
    if (form.pwd.value.length == 0) {
         alert("로그인 비밀번호를 입력해주세요.");
         form.pwd.focus();
         return false;
    }
    form.pwdConfirm.value = form.pwdConfirm.value.trim();
    if (form.pwdConfirm.value.length == 0) {
         alert("로그인 비밀번호 확인을 입력해주세요.");
         form.pwdConfirm.focus();
         return false;
    }
    if (form.pwdConfirm.value != form.pwdConfirm.value) {
         alert('로그인 비밀번호 확인이 일치하지 않습니다.');
         form.pwdConfirm.focus();
         return false;
    }
    if (form.name.value != form.name.value) {
         alert('로그인 이름을  입력해주세요.');
         form.name.focus();
         return false;
    }
    
    if( form.name.value.length < 2){
         alert('로그인 이름을 2자 이상 입력해주세요.');
         form.name.focus();
         return false;
    }
    form.submit();
}
	
function isAlphaNumeric(str) {
        var code, i, len;
        for (i = 0, len = str.length; i < len; i++) {
          code = str.charCodeAt(i);
          if (!(code > 47 && code < 58) && // numeric (0-9)
              !(code > 64 && code < 91) && // upper alpha (A-Z)
              !(code > 96 && code < 123)) { // lower alpha (a-z)
            return false;
          }
        }
        return true;
}


</script>
</head>
<body>
<a href="/member/list">list</a>
<h3>join</h3>
<form name="form" onsubmit="return checkForm();" action="/member/insert" method="post">
	id :<input type="text" name="id" id="id"/><span id="id-msg" style="color:red; font-size:0.8em;">중복된 아이디입니다.</span><br>
	pw :<input type="text" name="pwd"/><br>
	name :<input type="text" name="name"/><br>
	email :<input type="text" name="email"/><br>
	<button type="submit">등록</button>
</form>

</body>
</html>