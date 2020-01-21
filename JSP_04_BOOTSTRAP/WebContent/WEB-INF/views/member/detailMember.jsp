<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
<title>Member | 회원 상세</title>
</head>

<body>
  <!-- Content Wrapper. Contains page content -->
  <div >
    <jsp:include page="content_header.jsp">
    	<jsp:param value="회원관리" name="subject"/>
    	<jsp:param value="상세보기" name="item"/>
    	<jsp:param value="list.do" name="url"/>
    </jsp:include>
    
    <!-- Main content -->
    <section class="content register-page" style="height: 586.391px;">       
		<div class="register-box" style="min-width:450px;">         
	    	<form role="form" class="form-horizontal" action="regist.do" method="post">
	        	<div class="register-card-body" >
	            	<div class="row"  style="height:200px;">
						<div class="mailbox-attachments clearfix col-md-12" style="text-align: center;">							
							<div id="pictureView" style="border: 1px solid green; height: 170px; width: 170px; margin: 0 auto;"></div>														
						</div>
					</div>
					<br />
	                <div class="form-group row" >
	                  <label for="inputEmail3" class="col-sm-3 control-label text-right">아이디</label>
	
	                  <div class="col-sm-9">
	                    <input name="id" type="text" class="form-control" id="inputEmail3" value="${member.id }">
	                  </div>
	                </div>
	
	                <div class="form-group row">
	                  <label for="inputPassword3" class="col-sm-3 control-label text-right">패스워드</label>
	
	                  <div class="col-sm-9">
	                    <input name="pwd" type="password" class="form-control" id="inputPassword3" value="${member.pwd }">
	                  </div>
	                </div>
	                 <div class="form-group row">
	                  <label for="inputPassword3" class="col-sm-3 control-label text-right">이메일</label>
	
	                  <div class="col-sm-9">
	                    <input name="email" type="email" class="form-control" id="inputPassword3" value="${member.email }">
	                  </div>
	                </div>
	                 <div class="form-group row">
	                  <label for="inputPassword3" class="col-sm-3 control-label text-right">전화번호</label>
	                  <div class="col-sm-9">   
	                  	<input name="phone" type="text" class="form-control" id="inputPassword3" value="${member.phone.substring(0,3) }-${member.phone.substring(3,7)}-${member.phone.substring(7) }">	                
	                  </div>                  
	                </div>               
	              </div>  
		          <div class="card-footer" >
		          		<div class="row">
			          		<c:if test="${loginUser.authority eq 'ROLE_ADMIN' }">
			          			<c:set var="col" value="3" />
			          		</c:if>
			          		<c:if test="${loginUser.authority ne 'ROLE_ADMIN' }">
			          			<c:set var="col" value="6" />
			          		</c:if>
			          		
			          		<div class="col-sm-${col } text-center">
			          			<button type="button" id="modifyBtn" class="btn btn-warning ">수 정</button>
			          		</div>
			          		<c:if test="${loginUser.authority eq 'ROLE_ADMIN' }">
				          		<div class="col-sm-${col } text-center">
				          			<button type="button" id="deleteBtn" class="btn btn-danger" >삭 제</button>
				          		</div>
				          		<div class="col-sm-${col } text-center">
				          			<button type="button" id="stopBtn" class="btn btn-info" >정 지</button>
				          		</div>
			          		</c:if>
			          		<div class="col-sm-${col } text-center">
			            		<button type="button" id="listBtn" class="btn btn-primary pull-right" onclick="CloseWindow();">닫 기</button>
			            	</div>
		          	    </div>  	
		          </div>
	      	  </form>
      	  </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<!-- post parameter -->
<!-- 수정/삭제/정지를 위한 FORM 태그 -->
<form name="postForm">
	<input type="hidden" name="id" value="${member.id }" />
</form>

<script>

// 이미지를 div의 background로 박아놓기
var imageURL = "getPicture.do?picture=${member.picture}";
$('div#pictureView')
.css({
	'background-image' : 'url(' + imageURL +')',
	'background-position' : 'center',
	'background-size' : 'cover',
	'background-repeat' : 'no-repeat'
});

// input 태그의 테두리 없애고 readonly
$('input').css("border", "none").prop("readonly", true);

// *** 수정, 삭제에 사용될 form 객체
var form = $('form[name="postForm"]');

// 수정하기
$('#modifyBtn').on('click', function(e) {
	//alert('modify btn click!');

	// 로그인한 아이디만 수정 가능 (admin 제외)
	var authority = '${loginUser.authority}';
	var id = $('input[name="id"]').val();
	
	if (id != '${loginUser.id}' ) {
		if (authority != "ROLE_ADMIN") {
			alert('수정 권한이 없습니다!');
			return;
		}
	}
	
	form.attr({
		"action" : "modifyForm.do",
		"method" : "post"
	});
	form.submit();
});

// 사용자 중지
$('#stopBtn').on('click', function(e) {
	var form = $('form[name="postForm"]');
	form.attr({
		"action" : "stop.do",
		"method" : "post"
	});
	form.submit();
});

// 사용자 삭제
$('#deleteBtn').on('click', function(e) {
	var answer = confirm("정말 ${member.id} 님을 삭제하시겠습니까?");
	if (answer) {
		var form = $('form[name="postForm"]');
		form.attr({
			"action" : "remove.do",
			"method" : "post"
		});
		form.submit();
	}
});


</script>

</body>


  