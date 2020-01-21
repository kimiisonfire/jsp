<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<head>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote-bs4.css">
</head>



<!-- Summernote -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>

<script>
  // summernote 에디터 추가
  $(function() {
	  $('#content').summernote({
		  placeholder:'여기에 내용을 적어라임마',
		  height: 250,
		  
		  callbacks: {
			  // 사진 등록 버튼
			  onImageUpload : function(files, editor, welEditable) {
				  //alert("image upload");
				  for (var i=files.length-1; i>=0; i--) {
					  if (files[i].size > 1024*1024*5) {
						  alert("이미지는 5MB 미만입니다.");
						  return;
					  }
				  }
				  // this = summernote에 있는 textArea
				  for (var i=files.length-1; i>=0; i--) {
					  sendFile(files[i], this);
				  }
			  },
			  // 사진 삭제 버튼
			  onMediaDelete : function(target) {
				  //alert("image remove");
				  deleteFile(target[0].src);
			  }
		  }
	  })
  })
  
  // 이미지파일 전송을 위한 함수
  function sendFile(file, el) {
	  var form_data = new FormData();
	  form_data.append("file", file);
	  $.ajax({
		  data  : form_data,
		  type  : "post",
		  url   : "<%=request.getContextPath()%>/uploadImg.do",
		  cache : false,
		  enctype : "multipart/form-data",
		  contentType : false,
		  processData : false,
		  success : function(img_url) {
			  $(el).summernote('editor.insertImage', img_url);
		  }
	  });
  };
  
  var deleteFile = function(src) {
	  
	  var splitSrc = src.split("=");
	  var fileName = splitSrc[splitSrc.length -1];
	  
	  var fileData = {
			fileName : fileName  
	  }
	  
	  $.ajax({
		  url  : "<%=request.getContextPath()%>/deleteImg.do",
		  data : JSON.stringify(fileData),
		  type : "POST",
		  success : function(res) {
			  console.log(res);
		  }
	  });
  };

</script>

