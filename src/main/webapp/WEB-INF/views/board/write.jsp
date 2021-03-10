<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기창</title>
<script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
	    // 첨부파일 목록 불러오기 함수 호출
	    //listAttach();
		
	    // 파일 업로드 영역에서 기본효과를 제한
	    $(".fileDrop").on("dragenter dragover", function(e){
	        e.preventDefault(); // 기본효과 제한
	    });
	    // 드래그해서 드롭한 파일들 ajax 업로드 요청
	    $(".fileDrop").on("drop", function(e){
	        e.preventDefault(); // 기본효과 제한
	        var files = e.originalEvent.dataTransfer.files; // 드래그한 파일들
	        var file = files[0]; // 첫번째 첨부파일
	        console.log("file:",file);
	        var formData = new FormData(); // 폼데이터 객체
	        formData.append("file", file); // 첨부파일 추가
	        
	        $.ajax({
	            url: "${contextPath}/upload/uploadAjax",
	            type: "post",
	            data: formData,
	            dataType: "text",
	            processData: false, // processType: false - header가 아닌 body로 전달
	            contentType: false,
	            // ajax 업로드 요청이 성공적으로 처리되면
	            success: function(data){
	            	console.log("data: ", data)
	              /*   var str = "";
	                // 이미지 파일이면 썸네일 이미지 출력
	                if(checkImageType(data)){ 
	                    str = "<div><a href='${contextPath}/upload/displayFile?fileName="+getImageLink(data)+"'>";
	                    str += "<img src='${contextPath}/upload/displayFile?fileName="+data+"'></a>";
	                // 일반파일이면 다운로드링크
	                } else { 
	                    str = "<div><a href='${contextPath}/upload/displayFile?fileName="+data+"'>"+getOriginalName(data)+"</a>";
	                }
	                // 삭제 버튼
	                str += "<span data-src="+data+">[삭제]</span></div>";
	                $(".uploadedList").append(str); */
	            	 // 첨부 파일의 정보
	                var fileInfo = getFileInfo(data);
	                // 하이퍼링크
	                var html = "<a href='"+fileInfo.getLink+"'>"+fileInfo.fileName+"</a><br>";
	                // hidden 태그 추가
	                html += "<input type='hidden' class='file' value='"+fileInfo.fullName+"'>";
	                // div에 추가
	                $(".uploadedList").append(html);
	        }
	    });
	});
	
    $(".uploadedList").on("click", ".fileDel", function(event){
        alert("이미지 삭제")
        var that = $(this); // 여기서 this는 클릭한 span태그
        $.ajax({
            url: "${contextPath}/upload/deleteFile",
            type: "post",
            // data: "fileName="+$(this).attr("date-src") = {fileName:$(this).attr("data-src")}
            // 태그.attr("속성")
            data: {fileName:$(this).attr("data-src")}, // json방식
            dataType: "text",
            success: function(result){
                if( result == "deleted" ){
                    // 클릭한 span태그가 속한 div를 제거
                    that.parent("div").remove();
                }
            }
        });
    });
    
	$('#btnSave').click(function() {
		// 절차적으로 잘 적었는지 체크!
		var title = $('#title').val();
		var content = $('#content').val();
		/* var writer = $('#writer').val(); */

		if (title == "" || title.length == 0) {
			alert("제목을 입력하세요");
			document.form1.title.focus(); // 안쓴거 해당입력창에 포커스!
			return;
		}

		if (content == "" || content.length == 0) {
			alert("내용을 입력하세요");
			document.form1.content.focus(); // 안쓴거 해당입력창에 포커스! 
			return;
		}
		/* 
		if(writer == "" || writer.length == 0){
			alert("이름을 입력하세요");
			document.form1.writer.focus();	// 안쓴거 해당입력창에 포커스!
			return;
		} */
		
		var that = $("#form1");
		var str = "";
		// 태그들.each(함수)
        // id가 uploadedList인 태그 내부에 있는 hidden태그들
        $(".uploadedList .file").each(function(i){
            str += "<input type='hidden' name='files["+i+"]' value='"+$(this).val()+"'>";
        });
        // form에 hidden태그들을 추가
        $("#form1").append(str);
     	// 폼에 입력한 데이터를 서버로 전송
		document.form1.submit();
	});
    
});
	
	// 첨부파일 목록 ajax요청 처리
	function listAttach(){
	    $.ajax({
	        type: "post",
	        url: "${contextPath}/board/getAttach/${dto.bno}",
	        success: function(list){
	            $(list).each(function(){
	            // each문 내부의 this : 각 step에 해당되는 값을 의미    
	            var fileInfo = getFileInfo(this);
	            // a태그안에는 파일의 링크를 걸어주고, 목록에는 파일의 이름 출력
	            var html = "<div><a href='"+fileInfo.getLink+"'>"+fileInfo.fileName+"</a>&nbsp;&nbsp;";
	            // 삭제 버튼
	            html += "<a href='#' class='fileDel' data-src='"+this+"'>[삭제]</a></div>"
	            $(".uploadedList").append(html);
	            });
	        }
	    });
	}
	
	
	// 원본파일이름을 목록에 출력하기 위해
	function getOriginalName(fileName) {
	    // 이미지 파일이면
	    if(checkImageType(fileName)) {
	        return; // 함수종료
	    }
	    // uuid를 제외한 원래 파일 이름을 리턴
	    var idx = fileName.indexOf("_")+1;
	    return fileName.substr(idx);
	}
	
	// 이미지파일 링크 - 클릭하면 원본 이미지를 출력해주기 위해
	function getImageLink(fileName) {
	    // 이미지파일이 아니면
	    if(!checkImageType(fileName)) { 
	        return; // 함수 종료 
	    }
	    // 이미지 파일이면(썸네일이 아닌 원본이미지를 가져오기 위해)
	    // 썸네일 이미지 파일명 - 파일경로+파일명 /2017/03/09/s_43fc37cc-021b-4eec-8322-bc5c8162863d_spring001.png
	    var front = fileName.substr(0, 12); // 년원일 경로 추출
	    var end = fileName.substr(14); // 년원일 경로와 s_를 제거한 원본 파일명을 추출
	    console.log("front:",front); // /2017/03/09/
	    console.log("end:",end); // 43fc37cc-021b-4eec-8322-bc5c8162863d_spring001.png
	    // 원본 파일명 - /2017/03/09/43fc37cc-021b-4eec-8322-bc5c8162863d_spring001.png
	    return front+end; // 디렉토리를 포함한 원본파일명을 리턴
	}
	
	
	// 이미지파일 형식을 체크하기 위해
	function checkImageType(fileName) {
	    // i : ignore case(대소문자 무관)
	    var pattern = /jpg|gif|png|jpeg/i; // 정규표현식
	    return fileName.match(pattern); // 규칙이 맞으면 true
	}

	// 업로드 파일 정보
	function getFileInfo(fullName){
	    var fileName, imgsrc, getLink, fileLink;
	    // 이미지 파일일 경우
	    if(checkImageType(fullName)){
	        // 이미지 파열 경로(썸네일)
	        imgsrc = "${contextPath}/upload/displayFile?fileName="+fullName;
	        console.log(imgsrc);
	        // 업로드 파일명
	        fileLink = fullName.substr(14);
	        console.log(fileLink);
	        // 날짜별 디렉토리 추출
	        var front = fullName.substr(0, 12);
	        console.log(front);
	        // s_를 제거한 업로드이미지파일명
	        var end = fullName.substr(14);
	        console.log(end);
	        // 원본이미지 파일 디렉토리
	        getLink = "${contextPath}/upload/displayFile?fileName="+front+end;
	        console.log(getLink);
	    // 이미지 파일이 아닐경우
	    } else {
	        // UUID를 제외한 원본파일명
	        fileLink = fullName.substr(12);
	        console.log(fileLink);
	        // 일반파일디렉토리 
	        getLink = "${contextPath}/upload/displayFile?fileName="+fullName;
	        console.log(getLink);
	    }
	    // 목록에 출력할 원본파일명
	    fileName = fileLink.substr(fileLink.indexOf("_")+1);
	    console.log(fileName);
	    // { 변수:값 } json 객체 리턴
	    return {fileName:fileName, imgsrc:imgsrc, getLink:getLink, fullName:fullName};
	}	
		
</script>
<style type="text/css">
     .fileDrop{
           width: 400px;
           height: 200px;
           border: 2px dotted gray;
           
     }
     
     /* small {
           margin-left: 3px;
           font-weight: bold;
           color: gray;
     } */
</style>
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<h2>글 작성</h2>

	<form name="form1" id="form1" action="${contextPath}/board/insert.do">

		<div>
			제목 <input name="title" id="title" size="80" placeholder="제목을 입력해주세요" />
		</div>
		<div>
			내용
			<textarea name="content" id="content" rows="4" cols="80" placeholder="내용을 입력해주세요"></textarea>
		</div>
		<!-- 		<div>
			글쓴이 
			<input name="writer" id="writer" size="80" placeholder="이름을 입력해주세요" />
		</div> -->
		첨부파일 등록
		<!-- 첨부파일 등록영역 -->
		<div class="fileDrop"></div>
		<!-- 업로드된 파일 목록 -->
		<div class="uploadedList"></div>


		<div>
			<button type="button" id="btnSave">확인</button>
			<button type="reset" id="btnCancle">취소</button>
		</div>
	</form>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>