<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
<title>Member | 회원 목록</title>
</head>

<body>
	
	<div class="content-wrapper">
	  <!-- header -->
      <jsp:include page="content_header.jsp">
      	<jsp:param value='회원 목록' name="subject"/>
      	<jsp:param value='목록' name="item"/>
      </jsp:include>
      
      <!-- 내용 -->
      <section class="content">
	    <div class="card">
	      <div class="card-header with-border">
	      
	        <c:if test="${loginUser.authority eq 'ROLE_ADMIN'}">
	        <!-- 회원등록 새창 -->
	        <button type="button" class="btn btn-primary" 
	        	onclick="OpenWindow('registForm.do','회원등록',800,600)">회원등록
	        </button>
	        </c:if>
	        
	        <!-- 검색 -->
	        <div id="keyword" class="card-tools" style="width:350px;">
	          <div class="input-group row">
	            <!-- search bar -->
	            <select class="form-control col-md-4" name="searchType" id="searchType">
	              <option value=""  ${pageMaker.cri.searchType eq '' ? 'selected' : ''}>검색구분</option>
	              <option value="i" ${pageMaker.cri.searchType eq 'i' ? 'selected' : ''}>아이디</option>
	              <option value="p" ${pageMaker.cri.searchType eq 'p' ? 'selected' : ''}>전화번호</option>
	              <option value="e" ${pageMaker.cri.searchType eq 'e' ? 'selected' : ''}>이메일</option>
	            </select>
	            <input class="form-control" type="text" name="keyword"
	            	placeholder="검색어를 입력하세요." value="${param.keyword }">
	            <span class="input-group-append">
	              <button class="btn btn-primary" type="button"
	              	id="searchBtn" data-card-widget="search" onclick="searchList_go(1);">
	              	  <i class="fa fa-fw fa-search"></i>
	              </button>
	            </span>
	            <!-- end : search bar -->
	          </div>
	        </div>
	      </div>
	      
	      <!-- 회원 목록 테이블 -->
	      <div class="card-body" style="text-align:center;">
	       <div class="row">
	       	<div class="col-sm-12">
	          <table class="table table-bordered">
	          	<tr>
	              <th>순번</th>
	              <th>아이디</th>
	              <th>패스워드</th>
	              <th>이메일</th>
	              <th>전화번호</th>
	          	</tr>
	          	
	          	<c:forEach items="${memberList }" var="member" varStatus="status">
	              <tr>
	              	<td>${status.count }</td>
	              	<td>
	                  <a href="javascript:OpenWindow('detail.do?id=${member.id }','회원 정보 상세보기',800,600);">${member.id}</a> 
	              	</td>
	              	<td>${member.pwd}</td>
	              	<td>${member.email}</td>
	              	<td>${member.phone}</td>
	              </tr>
	          	</c:forEach>
	          </table>
	       	</div>	<!-- div class="col-sm-12" -->
	       </div>	<!-- div class="row" -->
	      </div>	<!-- div class="card-body" -->
	      
	      <!-- footer의 페이지네이션 -->
	      <div class="card-footer">
	      	<%@ include file="/WEB-INF/views/pagination/pagination.jsp" %>
	      </div>	<!-- div class="footer" -->
	    </div>	<!-- div class="card" -->
	  </section>
	</div>

	
</body>