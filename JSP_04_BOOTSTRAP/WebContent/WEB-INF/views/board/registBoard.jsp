<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<head>
	<title>Board | 게시글 등록</title>
</head>


<body>
  <!-- Content Wrapper. Contains page content -->
  <div >
    <!-- Content Header (Page header) -->
    <jsp:include page="content_header.jsp">
      <jsp:param value="자유게시판" name="subject"/>
      <jsp:param value="list.do" name="url"/>
      <jsp:param value="글등록" name="item"/>
    </jsp:include>

    <!-- Main content -->
    <section class="content container-fluid">
		<div class="row justify-content-center">
			<div class="col-md-9" style="max-width:960px;">
				<div class="card card-outline card-info">
					<div class="card-header">
						<h3 class="card-title p-1">글등록</h3>
						<div class ="card-tools">
							<button type="button" class="btn btn-primary" id="registBtn">등 록</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="btn btn-warning" id="cancelBtn">취 소</button>
						</div>
					</div><!--end card-header  -->
					<div class="card-body pad">
						<form role="form" method="post" action="regist.do" name="registForm">
							<div class="form-group">
								<label for="title">제 목</label> 
								<input type="text" id="title"
									name='title' class="form-control" placeholder="제목을 쓰세요">
							</div>							
							<div class="form-group">
								<label for="writer">작성자</label> 
								<input type="text" id="writer" readonly
									name="writer" class="form-control" value="${loginUser.id }">
							</div>
							<div class="form-group">
								<label for="content">내 용</label>
								<textarea class="textarea" name="content" id="content" rows="20"
									placeholder="1000자 내외로 작성하세요." style="display: none;"></textarea>
							</div>
						</form>
					</div><!--end card-body  -->
					<div class="card-footer" style="display:none">
					
					</div><!--end card-footer  -->
				</div><!-- end card -->				
			</div><!-- end col-md-12 -->
		</div><!-- end row -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
<script>
  
  $('#registBtn').click(function() {
	  //alert('resigt 버튼 클릭했다으!');
	  var form = document.registForm;
	  
	  if ( form.title.value == "" ) {
		  alert('제목은 필수입니다.');
		  return;
	  }
	  if ( form.content.value.length>1000 ) {
		  alert('글자수가 1000자를 초과할 수 없습니다.');
		  return;
	  }
	  
	  form.submit();
  })
  
  $('#cancelBtn').click(function() {
	  //alert('저기요 이렇게 취소버튼을 맘대로 누르시면 어케요');
	  window.opener.location.href = 'list.do';
	  window.close();
  })
</script>

<%@include file="/WEB-INF/views/commons/summernote.jsp" %>

</body>

