<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script>
	
	// 댓글 등록
	$('#replyAddBtn').on('click', function() {
		//alert('저기용 제가 맞게왔나용?');
		
		var replyer = $('#newReplyWriter').val();
		var replytext = $('#newReplyText').val();
		//alert('댓글단사람 : ' + replyer + ', 뭐라고썼냐면 : ' + replytext);
		
		if (replytext == "") {
			alert("댓글 내용은 필수입니다..");
			$('#newReplyText').focus().css("border-color", "red")
							  .attr("placeholder", "내용은 필수입니다.");
			return;
		}
		
		var data = {
				"bno"       : "${board.bno}",
				"replyer"   : replyer,
				"replytext" : replytext
		};
		
		$.ajax({
			url  : "<%=request.getContextPath()%>/replies/regist.do",
			type : "post",
			data : JSON.stringify(data),
			contentType : "application/json",	// 보내는 data 타입
			dataType : "text",
			success  : function(data) {
				var res = data.split(',');
				if (res[0] == "SUCCESS") {
					getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page=" + res[1]);
					alert("댓글이 등록되었습니다!");
					$('#newReplyText').val("");
				} else {
					alert("댓글 등록이 취소되었습니다.");
				}
			},
			error : function(xhr) {
				alert('서버 오류로 인하여 댓글 등록을 실패했습니다.');
			}
		}).done(function() {});
		
	});
	
	$('#newReplyText').on('keydown', function() {
		$(this).css("border-color", "lightgrey");
	});
</script>	
	
	
<!-- handlebars 적용 --> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.5.3/handlebars.min.js"></script>

<!-- 탬플릿...! -->
<script id="reply-list-template" type="text/x-handlebars-template">
{{#each .}}
<div class="replyLi" data-rno={{rno}}>
	<i class="fas fa-comments bg-yellow"></i>
 	<div class="timeline-item" >
  		<span class="time">
    		<i class="fa fa-clock"></i>{{prettifyDate regdate}}
	 		<a class="btn btn-primary btn-xs" id="modifyReplyBtn"
	    		data-replyer={{replyer}} data-toggle="modal" data-target="#modifyModal">Modify</a>
  		</span>
	
  		<h3 class="timeline-header"><strong style="display:none;">{{rno}}</strong>{{replyer}}</h3>
  		<div class="timeline-body">{{replytext}} </div>
	</div>
</div>
{{/each}}
</script>	
	
<script>	
	
	// millisec 날짜를 보기좋게 바꿔주기
	Handlebars.registerHelper("prettifyDate", function(timeValue) {
		var dateObj = new Date(timeValue);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth() + 1;
		var date = dateObj.getDate();
		
		return year + "/" + month + "/" + date;
	});

	var printData = function(replyArr, target, templateObject) {
		var template = Handlebars.compile(templateObject.html());
		var html = template(replyArr);
		$('.replyLi').remove();
		target.after(html);
	};
	
	// reply list
	function getPage(pageInfo) {
		$.getJSON(pageInfo, function(data) {
			printData(data.replyList, $('#repliesDiv'), $('#reply-list-template'));
			printPaging(data.pageMaker, $('.pagination'));
			if (data.pageMaker.realEndPage > 0) {
				realEndPage = data.pageMaker.realEndPage;
			}
		});
	};
	var replyPage = 1;
	var realEndPage = 1;
	getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page="+replyPage);
	
	
	// reply pagination
	var printPaging=function(pageMaker,target){
		var str="";
		if(pageMaker.prev){
			str+="<li class='page-item'><a class='page-link' href='"+(pageMaker.startPage-1)
					+"'> <i class='fas fa-angle-left'/> </a></li>";
		}
		for(var i=pageMaker.startPage,len=pageMaker.endPage;i<=len;i++){
			var strClass = pageMaker.cri.page==i?'active':'';
			str+="<li class='page-item "+strClass+"'><a class='page-link' href='"+i+"'>"+i+"</a></li>";
		}
		if(pageMaker.next){
			str+="<li class='page-item' ><a class='page-link' href='"+(pageMaker.endPage+1)
				+"'> <i class='fas fa-angle-right'/> </a></li>";
		}
		target.html(str);
	};
	$('.pagination').on('click','li a',function(event){
		event.preventDefault();
		replyPage=$(this).attr("href");
		getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page="+replyPage);
	});
	
	
	//reply modify - 사용자 확인
	$('div.timeline').on('click', '#modifyReplyBtn', function(event) {
		//alert($(this).attr("data-replyer"));
		// 로그인 사용자 확인
		var replyer = $(event.target).attr("data-replyer");
		if (replyer!="${loginUser.id}") {
			alert("수정 권한이 없습니다.");
			$(this).attr("data-toggle","");
		}
	});
	
	// reply modify - 선택한 댓글의 내용을 modal창에 뿌리기
	$('div.timeline').on('click', '.replyLi', function(event) {
		var reply = $(this);
		$('#replytext').val(reply.find('.timeline-body').text());
		$('.modal-title').html(reply.attr('data-rno'));
	});
	
	// 댓글 수정하고 '수정하기' 버튼 click 이벤트
	$('#replyModBtn').on('click', function(event) {
		
		var rno = $('.modal-title').html();
		var replytext = $('#replytext').val();
		
		var sendData = {
				rno : rno,
				replytext : replytext
		};
		
		$.ajax({
			url  : "<%=request.getContextPath()%>/replies/modify.do",
			type : "post",
			data : JSON.stringify(sendData),
			success : function(result) {
				if (result == "SUCCESS") {
					alert("수정되었습니다!");
					getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page="+replyPage);
				}
			},
			error : function(xhr) {
				alert("댓글 수정에 실패했습니다 흑흑..");
			},
			complete : function() {
				$('#modifyModal').modal('hide');
			}
		});
	});
	
	// 댓글 수정하기 - delete버튼 클릭시 댓글 삭제
	$('#replyDelBtn').on('click', function(event) {
		//alert('댓글 삭제 버튼을 누르셨네용');
		var rno = $('.modal-title').html();
		var sendData = {
				rno : rno,
				page : replyPage,
				bno : "${board.bno}"
		};
		
		$.ajax({
			url  : "<%=request.getContextPath()%>/replies/remove.do",
			type : "post",
			data : JSON.stringify(sendData),
			success : function(data) {
				var res = data.split(',');
				if (res[0] == "SUCCESS") {
					alert('삭제되었습니다!');
					getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page="+res[1]);
				}
			},
			error : function(xhr) {
				alert("댓글 삭제에 실패했습니다 흑흑,,");
			},
			complete : function() {
				$('#modifyModal').modal('hide');
			}
		});
	});
	
	
</script>