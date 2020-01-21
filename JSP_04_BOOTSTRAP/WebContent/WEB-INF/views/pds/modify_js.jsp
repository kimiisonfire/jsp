<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script>

// 파일 하나씩 첨부하기
$('#addFileBtn').on('click', function(e) {
	//alert("add file btn click");
	
	// 첨부파일 개수 카운트
	var attachedFile = $('a[name="attachedFile"]').length;	// 기존의 파일들
	var inputFile = $('input[name="uploadFile"]').length;
	var attachCount = attachedFile + inputFile;
	
	if ( attachCount >= 5) {
		alert("파일 추가는 5개까지만 가능합니다.");
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

// add file 시 X 버튼 이벤트
$('div.fileInput').on('click', 'div.inputRow > button', function(event) {
	$(this).parent('div.inputRow').remove();
});

// add file로 새로 등록한 파일 용량 체크
$('.fileInput').on('change', 'input[type="file"]', function(event) {
	if (this.files[0].size > 1024 * 124 * 40) {
		alert("파일 용량이 40MB를 초과하였습니다.");
		this.value="";
		$(this).focus();
		return false;
	}
});

// 이미 첨부했던 파일의 X 버튼 이벤트
$('div.fileInput').on('click', 'a[name="attachedFile"] > button', function(event) {
	//event.preventDefault();
	//event.stopPropagation();	// button 눌렀더니 그다음의 a가 실행됐었거든 그거막은겨
	
	var parent = $(this).parent('a[name="attachedFile"]');
	alert(parent.attr("attach-fileName") + " 파일을 삭제헙니다!");
	
	var ano = parent.attr("attach-no");
	
	$(this).parents('li.attach-item').remove();
	
	var input = $('<input>').attr({
		"type" : "hidden",
		"name" : "deleteFile",
		"value" : ano
		});
	$('form[role="form"]').prepend(input);
	
	return false; // 위에서 버블링막으려고했는데 이게 더 효과적이래
});





</script>
