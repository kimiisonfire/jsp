<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<title>Member | 회원 수정</title>

<body>
  <!-- Content Wrapper. Contains page content -->
  <div>
   <jsp:include page="content_header.jsp">
    	<jsp:param value="회원관리" name="subject"/>
    	<jsp:param value="수정하기" name="item"/>
    	<jsp:param value="list.do" name="url"/>
    </jsp:include>
  <!-- Main content -->
   <section class="content register-page" >
	<div class="register-box" style="min-width:450px;">					
		<form role="form" class="form-horizontal" action="modify.do" method="post">
			<div class="register-card-body">	
				<div class="row">		
					<input type="hidden" name="picture" />
					<input type="hidden" name="oldPicture" value="${member.picture }" />
					<div class="input-group col-md-12">
						<div class="col-md-12" style="text-align: center;">
							<div class="" id="pictureView" style="border: 1px solid green; height: 170px; width: 170px; margin: 0 auto; margin-bottom:5px;"></div>														
							<div class="input-group input-group-sm">
								<label for="inputFile" class=" btn btn-warning btn-sm btn-flat input-group-addon">파일선택</label>
								<input id="inputFileName" class="form-control" type="text" name="picture"/>
								<span class="input-group-append-sm">											
									<button type="button" class="btn btn-info btn-sm btn-append" onclick="upload_go();">업로드</button>											
								</span>
							</div>						
						</div>												
					  </div>
				</div>	
				<div class="form-group row">
					<label for="id" class="col-sm-3 control-label text-right">아이디</label>	
					<div class="col-sm-9">
						<input readonly name="id" type="text" class="form-control" id="id"
							placeholder="13글자 영문자,숫자 조합" value="${member.id }">
					</div>
				</div>
				
				<div class="form-group row">
					<label for="pwd" class="col-sm-3 control-label text-right" >패스워드</label>

					<div class="col-sm-9">
						<input name="pwd" type="password" class="form-control" id="pwd"
							placeholder="20글자 영문자,숫자,특수문자 조합" value="${member.pwd }">
					</div>
				</div>
						
					<c:if test="${loginUser.authority eq 'ROLE_ADMIN' }">						
						<div class="form-group row">
							<label for="authority" class="col-sm-3 control-label text-right" >권 한</label>
							<div class="col-sm-9">
								<select name="authority" class="form-control">
									<option value="ROLE_USER">사용자</option>
									<option value="ROLE_MANAGER">운영자</option>
									<option value="ROLE_ADMIN">관리자</option>
								</select>
							</div>
						</div>
					</c:if>
					<div class="form-group row">
						<label for="email" class="col-sm-3 control-label text-right">이메일</label>

						<div class="col-sm-9">
							<input name="email" type="email" class="form-control" id="email"
								placeholder="example@naver.com" value="${member.email }">
						</div>
					</div>
					<div class="form-group row">
	                  <label for="phone" class="col-sm-3 control-label text-right">전화번호</label>
	                  <div class="col-sm-9">   
	                  	<input name="phone" type="text" class="form-control" id="inputPassword3" value="${member.phone.substring(0,3) }-${member.phone.substring(3,7)}-${member.phone.substring(7) }">	                
	                  </div>                  
	                </div>  
				</div>
				<div class="card-footer row" style="margin-top: 0; border-top: none;">						
					<button type="button" id="modifyBtn" class="btn btn-warning col-sm-4 text-center" >수정하기</button>
					<div class="col-sm-4"></div>
					<button type="button" id="cancelBtn"
						class="btn btn-default pull-right col-sm-4 text-center">취 소</button>
				</div>
				
			</form>
	
			
		</div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
<form role="imageForm" action="upload/picture.do" method="post" enctype="multipart/form-data">
	<input style="display:none;" id="inputFile" name="pictureFile" type="file" class="form-control">
	<input type="hidden" id="oldFile" name="oldPicture" value="" />
	<input type="hidden" name="checkUpload" value="1" />	
</form>
 
<script>
var imageURL = "getPicture.do?picture=${member.picture}";
$('div#pictureView')
.css({
	'background-image' : 'url(' + imageURL +')',
	'background-position' : 'center',
	'background-size' : 'cover',
	'background-repeat' : 'no-repeat'
});

// 수정하기 버튼 클릭 이벤트
$('#modifyBtn').on('click', function(e) {
	//alert('modify : modify btn click!');
	
	var uploadCheck = $('input[name="checkUpload"]').val();
	if (uploadCheck != 1) {
		alert("사진 업로드는 필수입니다.");
		return;
	}
	
	var form = $('form[role="form"]');
	form.submit();
});

// 취소하기 버튼 클릭 이벤트
$('#cancelBtn').on('click', function(e) {
	//alert("cancle btn click");
	history.go(-1);
});

</script> 

<jsp:include page="picture_js.jsp" />
 
</body>






  
  