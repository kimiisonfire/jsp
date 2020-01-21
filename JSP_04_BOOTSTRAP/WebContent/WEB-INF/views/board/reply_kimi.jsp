<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script>

// add reply 버튼 클릭
/* 1) writer, text 가져오기
 * 2) text가 비어있을경우 alert, text로 돌아가도록하고 border 색상 변경
   3) 다시 text를 입력하면 border 색상 원상복귀
   4) 위의 과정을 통과하면 json 데이터로 담기
   5) ajax로 보내기 -> json을 String으로 가져가는거 고려
 */
	$('button#replyAddBtn').on('click', function(e) {
		var writer = $('#newReplyWriter').val();
		var text = $('#newReplyText').val();
		
		if (text == "") {
			alert("댓글 내용을 입력하세요.");
			$('#newReplyText').focus().css('border-color', 'red');
			return;
		}
		
		var data = {
				"bno" : "${board.bno}",
				"replyer" : writer,
				"replytext" : text
		};
		
		$.ajax({
			url  : "<%=request.getContextPath()%>/replies/regist.do",
			data : JSON.stringify(data),
			type : "post",
			contentType : "application/json",
			success : function(res) {
				if (res == "SUCCESS") {
					alert("댓글이 등록되었습니다.");
					getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page=" + realEndPage);
					$('#newReplyText').val("");
				} else {
					alert("댓글 등록에 실패했습니다.");
				}
			},
			error : function(xhr) {
				alert("서버 오류로 인해 댓글 등록에 실패했습니다.");
			}
		}).done(function(){});
	})
 
	$('#newReplyText').on('keydown', function() {
		$(this).css('border-color', 'lightgrey');
	})


</script>

<!-- handlebars 적용 --> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.5.3/handlebars.min.js"></script>

<!-- 핸들바 사용한 댓글목록 출력 템플릿 작성 -->
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

	// 날짜 형식을 좀 보기좋게 바꿔주는 함수
	Handlebars.registerHelper("prettifyDate", function(timeValue) {
		var dateObj = new Date(timeValue);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth() + 1;
		var date = dateObj.getDate();
		
		return year + "/" + month + "/" + date;
	})
	
	// 출력할 변수, 출력할 위치, 사용할 템플릿을 매개변수로하는 함수
	var printData = function(replyArr, target, templateObject) {
		var template = Handlebars.compile(templateObject.html());
		var html = template(replyArr);
		$('.replyLi').remove();
		target.after(html);
	}

	// reply 목록을 출력하는 함수
	function getPage(pageInfo) {
		$.getJSON(
			pageInfo,		
			function(data) {
				printData(data.replyList, $('#repliesDiv'), $('#reply-list-template'));
				printPaging(data.pageMaker, $('.pagination'));
				if (data.pageMaker.realEndPage>0) {
					realEndPage = data.pageMaker.realEndPage;
				}
			}
		)
	}
	var realEndPage = 1;
	var replyPage = 1;
	getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page=" + replyPage);
	
	
	// reply 페이지네이션
	var printPaging = function(pageMaker, target) {
		var str = "";
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
	}
	
	// reply 페이지네이션 클릭 이벤트
	$('.pagination').on('click', '.page-link', function(e) {
		e.preventDefault();
		page = $(this).attr('href');
		getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page=" + page);
	})
	

</script>





