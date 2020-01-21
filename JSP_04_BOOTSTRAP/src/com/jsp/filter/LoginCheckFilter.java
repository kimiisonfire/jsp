package com.jsp.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.dispatcher.ViewResolver;
import com.jsp.dto.MemberVO;

public class LoginCheckFilter implements Filter {

	private List<String> exURLs = new ArrayList<String>();
	
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpServletResponse httpResp = (HttpServletResponse)response;
		
		HttpSession session = httpReq.getSession();
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		// 제외할 url인지 아닌지 확인
		String reqUrl = httpReq.getRequestURI().substring(
				httpReq.getContextPath().length());
		
		if (excludeCheck(reqUrl)) {	// 로그인 체크하지않을 url일경우 다음단계로 넘기기
			chain.doFilter(request, response);
			return;
		}
		
		// login check
		if (loginUser == null) {	// 비로그인일 경우
			/*
			 * script로 출력하는 경우
			httpResp.setContentType("text/html;charset=utf-8");
			PrintWriter out = httpResp.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용할 수 있습니다.')");
			out.println("if(window.opener) { window.opener.location.reload(true); ");
			out.println("window.close(); } else {");
			out.println("location.href='" + httpReq.getContextPath() + 
						"/commons/loginForm.do'; }");
			out.println("</script>");
			
			 * 리다이렉트 치는 경우
			session.setAttribute("msg", "로그인 후 이용할 수 있습니다.");
			String url = httpReq.getContextPath() + "/commons/loginForm.do";
			httpResp.sendRedirect(url);
			*/
			
			ViewResolver.view(httpReq, httpResp, "commons/loginCheck");
			
		} else {					// 로그인일 경우
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		String excludeURLNames = fConfig.getInitParameter("exclude");
		StringTokenizer st = new StringTokenizer(excludeURLNames, ",");
		
		while (st.hasMoreElements()) {
			exURLs.add(st.nextToken().trim());
		}
		
	}

	// 사용자가 요청한 url을 파라미터로 주면, url이 exURL에 있는건지 아닌지 판단
	private boolean excludeCheck(String url) {
		for (String exURL : exURLs) {
			if (url.contains(exURL)) {
				return true;
			}
		}
		return false;
	}
	
}
