<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script>

// 업로드 버튼(사실 label)으로 파일 선택시 내용이 input태그 inputFile val에 담김 = change event
$('input#inputFile').on('change', function(event) {
	//alert("inputFile input tag choiced")
	
	// form 태그의 checkUpload에 유효성 정보 세팅 -> 0:비정상, 1:정상
	$('input[name="checkUpload"]').val(0);
	
	var fileFormat =
		this.value.substr(this.value.lastIndexOf(".")+1).toUpperCase();
	
	// 파일 형식 체크
	if (fileFormat != "JPG") {
		alert("이미지는 jpg 형식만 가능합니다.");
		return;
	}
	
	// 이미지 파일 용량 체크
	if (this.files[0].size > 1024 * 1024 * 1) {
		alert("이미지는 1MB 이하의 파일만 가능합니다.");
		return;
	}
	
	// 파일명을 input 태그에 지정
	document.getElementById('inputFileName').value = this.files[0].name;
	
	// 이미지파일 썸네일을 div에 지정
	if (this.files && this.files[0]) {
		var reader = new FileReader();
		
		reader.onload = function(e) {
			$('div#pictureView')
			.css({
				'background-image' 		: 'url(' + e.target.result + ')',
				'background-position' 	: 'center',
				'background-size' 		: 'cover',
				'background-repeat' 	: 'no-repeat'
			});
		};
		reader.readAsDataURL(this.files[0]);
	}
});

// 사진 업로드 버튼 클릭했을때의 함수
function upload_go() {
	var form = new FormData($('form[role="imageForm"]')[0]);
	
	// form 태그에 값이 있는지 없는지 확인. 없으면 사진을 선택하지않은거
	if ($('input[name="pictureFile"]').val() == "" ) {
		alert("사진을 선택하세요.");
		$('input[name="pictureFile"]').click();
		return;
	}
	
	// form 태그 양식을 객체화
	$.ajax({
		url  : "<%=request.getContextPath()%>/member/picture.do",
		data : form,
		type : "post",
		processData : false,
		contentType : false,
		success : function(data) {
			$('input#oldFile').val(data);
			$('form[role="form"] input[name="picture"]').val(data);
			$('input[name="checkUpload"]').val(1);
			alert("사진을 업로드 했습니다.");
		}
	});
}






</script>