<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script>
	alert("로그인 후 이용 가능합니다.");
	if (window.opener) {
		window.opener.location.reload(true);
		window.close();
	} else {
		location.href="<%=request.getContextPath()%>/commons/loginForm.do";
	}
</script>