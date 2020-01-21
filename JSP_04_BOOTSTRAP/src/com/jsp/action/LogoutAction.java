package com.jsp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "commons/logout";
		
		HttpSession session = request.getSession();
		session.invalidate();	// session이 아예 종료
		
		/*	
		String url = null;
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("alert('로그아웃 되었습니다')");
		out.println("location.href='" + request.getContextPath() 
					+ "/commons/loginForm.do'; ");
		out.println("</script>");
		*/
		
		request.setAttribute("msg", "로그아웃 되었습니다.");
		
		return url;
	}

}
