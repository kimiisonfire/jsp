<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>


<nav aria-label="member list Navigation">
  <ul class="pagination justify-content-center m-0">
    
    <!-- 제일 첫번째 페이지로 가는 버튼 -->
    <li class="page-item">
      <a class="page-link" href="javascript:searchList_go(1);">
        <i class="fas fa-angle-double-left"></i>
      </a>
    </li>
    
    <!-- 이전 페이지로 가는 버튼 : 이전의 n개 항목의 가장 마지막 페이지를 보여줌 -->
    <li class="page-item">
      <a class="page-link" href="javascript:searchList_go(
        ${pageMaker.prev ? pageMaker.startPage-1 : 1});">
        <i class="fas fa-angle-left"></i>
      </a> 
    </li>
    
    <!-- 가운데 넘버링 -->
    <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="pageNum">
      <li class="page-item
        <c:out value="${pageMaker.cri.page == pageNum ? 'active':'' }"/> " >
        <a class="page-link" href="javascript:searchList_go(${pageNum });">${pageNum }
        </a>
      </li>
    </c:forEach>
    
    <!-- 다음 페이지로 가는 버튼 : 다음의 n개 항목의 가장  첫번째 페이지를 보여줌 -->
    <li class="page-item">
      <a class="page-link" 
        href="javascript:searchList_go('${pageMaker.next ? pageMaker.endPage+1 : pageMaker.cri.page}');">
        <i class="fas fa-angle-right"></i>
      </a>
    </li>
    
    <!-- 제일 마지막 페이지로 가는 버튼 -->
    <li class="page-item">
      <a class="page-link" href="javascript:searchList_go(${pageMaker.realEndPage });">
        <i class="fas fa-angle-double-right"></i>
      </a>
    </li>
    
  </ul>
</nav>
	
	
<form id="jobForm">
	<input type='hidden' name="page" value="${pageMaker.cri.page }" />
	<input type='hidden' name="perPageNum" value="${pageMaker.cri.perPageNum }" />
	<input type='hidden' name="searchType" value="${pageMaker.cri.searchType }" />
	<input type='hidden' name="keyword" value="${pageMaker.cri.keyword }" />
</form>

<script>
function searchList_go(page) {
	var jobForm = $('#jobForm');
	jobForm.find("[name='page']").val(page);
	jobForm.find("[name='searchType']").val($('select[name="searchType"]').val());
	jobForm.find("[name='keyword']").val($('div.input-group>input[name="keyword"]').val());
	jobForm.attr("action", "list.do").attr("method", "post");
	jobForm.submit();
}
</script>