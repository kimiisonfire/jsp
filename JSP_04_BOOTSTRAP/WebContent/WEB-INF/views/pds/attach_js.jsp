<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script>

// 첨부파일 버튼 클릭!
$('#addFileBtn').on('click', function(event) {
	
	if ($('input[name="uploadFile"]').length >= 5) {
		alert("파일은 5개까지 추가할 수 있습니다.");
		return;
	}
	
	// 타입 file인 input[name="uploadFile"] 태그를 만들거임
	var input = $('<input>').attr({'type':'file', 'name':'uploadFile'})
							.css('display', 'inline');
	// 구분을 위한 div
	var div = $('<div>').addClass("inputRow");
	div.append(input).append("<button style='border:0;outline:0;' class='badge bg-red' type='button'>X</button>");
	div.appendTo('.fileInput');
});

// 빨간 X버튼 클릭시!
$('div.fileInput').on('click', 'div.inputRow > button', function(event) {
	$(this).parent('div.inputRow').remove();
});

// 파일 첨부 시 용량 확인 - change로 확인
$('.fileInput').on('change', 'input[type="file"]', function(event) {
	if (this.files[0].size > 1024*1024*20) {
		alert("파일 용량이 20MB를 초과하였습니다.");
		this.value="";
		$(this).focus();
		return false;
	}
});


</script>
