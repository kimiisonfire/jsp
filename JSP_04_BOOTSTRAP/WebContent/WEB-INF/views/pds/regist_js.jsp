<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script>

// 자료실 -> 자료등록버튼 -> 글작성 후 등록 버튼 클릭 이벤트
$('#registBtn').on('click', function(event) {
	var form = document.registForm;
	
	// add file 후 파일을 등록했는지 확인
	var files = $('input[name="uploadFile"]');
	for (var file of files) {
		console.log(file.name + " : " + file.value);
		if ( file.value == "") {
			alert("파일을 선택하세요.");
			file.focus();
			return false;
		}
	}
	
	// 글 제목을 입력했는지 확인 
	if ( $('input[name="title"]').val() == "" ) {	// form.title.value 이렇게 잡아도됨
		alert("제목은 필수입니다.");
		$('input[name="title"]').focus();
		return false;
	}
	
	// 내용의 글자수 확인
	if (form.content.value.length > 1000) {
		alert("글자수가 1000자를 초과할 수 없습니다.");
		return;
	}
	
	form.submit();
});



</script>
