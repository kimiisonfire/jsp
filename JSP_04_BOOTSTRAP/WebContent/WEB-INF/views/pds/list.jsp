<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<head>
	<title>Pds | 자료실 목록</title>
	<style>
		table th,td{
			text-align:center;
		}
		
	
	</style>
</head>	
	
<body>
  <c:set var="pageMaker" value="${dataMap.pageMaker }" />	  	
   <div class="content-wrapper"  >
    <!-- Content Header (Page header) -->
    <section class="content-header">
    	<div class="container-fluid">
    		<div class="row mb-2">
    			<div class="col-sm-6">
	      			<h1>자료실</h1>
	      		</div>	      		
	    	
	       		
	       		<div class="col-sm-6">
			      <ol class="breadcrumb float-sm-right">
			        <li class="breadcrumb-item"><a href="list.do"><i class="fa fa-dashboard"></i>자료실</a></li>
			        <li class="breadcrumb-item active">리스트</li>		        
			      </ol>
		      	</div>
	     	</div>	     	
      	</div>
    </section>

    <!-- Main content -->
    <section class="content">
		<div class="card">
			<div class="card-header with-border">
				<button type="button" class="btn btn-primary" id="registBtn" onclick="OpenWindow('registForm.do','자료등록',800,600);">자료등록</button>
				<div id="keyword" class="card-tools" style="width:350px;">	
					<div class="input-group row">
						<select class="form-control" name="searchType" id="searchType">
							<option value="tcw"  ${pageMaker.cri.searchType eq 'tcw' ? 'selected':'' }>전 체</option>
							<option value="t" ${pageMaker.cri.searchType eq 't' ? 'selected':'' }>제 목</option>
							<option value="w" ${pageMaker.cri.searchType eq 'w' ? 'selected':'' }>작성자</option>
							<option value="c" ${pageMaker.cri.searchType eq 'c' ? 'selected':'' }>내 용</option>
							<option value="tc" ${pageMaker.cri.searchType eq 'tc' ? 'selected':'' }>제목+내용</option>
							<option value="cw" ${pageMaker.cri.searchType eq 'cw' ? 'selected':'' }>작성자+내용</option>							
						</select>
						<input  class="form-control" type="text" name="keyword" placeholder="검색어를 입력하세요." value="${param.keyword }"/>
						<span class="input-group-append">
							<button class="btn btn-primary" type="button" id="searchBtn" data-card-widget="search">
								<i class="fa fa-fw fa-search"></i>
							</button>
						</span>
					</div>						
				</div>			
			</div>
			<div class="card-body">
				<table class="table table-bordered text-center">
					<tr style="font-size:0.95em;">
						<th style="width:10%;">번 호</th>
						<th style="width:50%;">제 목</th>
						<th style="width:15%;">작성자</th>
						<th >등록일</th>
						<th style="width:10%;">조회수</th>
					</tr>	
					<c:if test="${empty dataMap.pdsList }" >
						<tr>
							<td colspan="5">
								<strong>해당 내용이 없습니다.</strong>
							</td>
						</tr>
					</c:if>						
					<c:forEach items="${dataMap.pdsList }" var="pds">
						<tr>
							<td>${pds.pno }</td>
							<td id="pdsTitle" style="text-align:left;max-width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
							<a href="javascript:OpenWindow('detail.do?pno=${pds.pno }','상세보기',800,600)">
								<span class="col-sm-12 ">${pds.title }</span>
							</a>
							</td>
							<td>${pds.writer }</td>
							<td>
								<fmt:formatDate value="${pds.regDate }" pattern="yyyy-MM-dd"/>
							</td>
							<td><span class="badge bg-red">${pds.viewcnt }</span></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="card-footer">
				<nav aria-label="pds list Navigation">
					<ul class="pagination justify-content-center m-0">
							
						<li class="page-item" >
							<a class="page-link" href="list${pageMaker.makeQuery(1)}">
								<i class="fas fa-angle-double-left" ></i>
							</a>						
						<li class="page-item">
							<a class="page-link" href="list.do
							<c:if test="${pageMaker.prev }">
								${pageMaker.makeQuery(pageMaker.startPage-1) }
							</c:if>
							"><i class="fas fa-angle-left" ></i></a>
						</li>
						
						<c:forEach begin="${pageMaker.startPage }" 
						           end="${pageMaker.endPage }" var="pageNum">
						<li class="page-item <c:out value="${pageMaker.cri.page == pageNum?'active':''}"/>">
							<a class="page-link" href="list.do${pageMaker.makeQuery(pageNum) }" >${pageNum }</a></li>
						</c:forEach>
						<li class="page-item">
							<a class="page-link" href="list.do<c:if test="${pageMaker.next }">
							${pageMaker.makeQuery(pageMaker.endPage+1) }
								</c:if>
								<c:if test="${!pageMaker.next }">${pageMaker.makeQuery(pageMaker.cri.page) }
								</c:if>
								"><i class="fas fa-angle-right" ></i>
							</a>
						</li>
						<li class="page-item">
							<a class="page-link" href="list.do${pageMaker.makeQuery(pageMaker.realEndPage) }">
								<i class="fas fa-angle-double-right" ></i>
							</a>					
						</li>	
					</ul>
				</nav>
			</div>
		</div>
	</section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
	<form id="jobForm">
		  <input type='hidden' name="page" value="${pageMaker.cri.page}" />
		  <input type='hidden' name="perPageNum" 
		  		 value="${pageMaker.cri.perPageNum}"/>
		  <input type='hidden' name="searchType" 
		         value="${pageMaker.cri.searchType }" />
		  <input type='hidden' name="keyword" 
		         value="${pageMaker.cri.keyword }" />
	</form>
   	
<script>
	
	
</script>

</body>






  