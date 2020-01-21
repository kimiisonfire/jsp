package com.jsp.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.exception.PageNotFoundException;

public class FrontServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestPro(request, response);
	}
	
	/*
	@Override
	public void init() throws ServletException {
		super.init();
		HandlerMapper.getAction("");
	}
	*/
	
	private void requestPro(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, PageNotFoundException {
		
		//request.setCharacterEncoding("utf-8");	// filter 달아서 필요없음
		//response.setContentType("text/html; charset=utf-8");	// jsp에서 정해져 나가므로 필요없음
		
		String command = request.getRequestURI();	// uri 받음
		
		if (command.indexOf(request.getContextPath()) == 0) {	// contextPath 삭제
			command = command.substring(request.getContextPath().length());
		}
		
		String view = null;
		Action act = null;
		
		act = HandlerMapper.getAction(command);	// 원하는 uri의 action 객체 가져오기
		
		if (act == null) {
			// command(uri)가 없을 경우 getAction결과가 null, 에러페이지!
			System.out.println("not found : " + command);
			
			// return 대신 에러페이지를 tomcat한테 보냄
			throw new PageNotFoundException();
			
		} else {
			// 존재하는 command일 경우 실행 & 작업 이후 어디로 갈지 주소를 return함
			view = act.execute(request, response);
			if ( view!= null ) {
				ViewResolver.view(request, response, view);
			}
		}
		
		
		
	}
	
}
