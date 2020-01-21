package com.jsp.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.dto.MemberVO;
import com.jsp.exception.IdNotFoundException;
import com.jsp.exception.InvalidPasswordException;
import com.jsp.service.MemberService;
import com.jsp.service.MemberServiceImpl;

public class LoginAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "redirect:member/list.do";
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		String message = null;
		HttpSession session = request.getSession();
		
		// 로그인 실패와 관련된 attribute를 삭제.
		session.removeAttribute("msg");
		session.removeAttribute("id");
		
		
		// 아이디, 비밀번호 비교 후 로그인가능여부 판단
		try {
			MemberServiceImpl.getInstance().login(id, pwd);
			
			// 검사 성공 시 아이디의 사용가능여부(enabled)를 적용해 로그인 처리
			MemberVO loginUser = MemberServiceImpl.getInstance().getMember(id);
			
			
			if (loginUser.getEnabled() == 0) {	// 사용정지상태의 유저
				message = "사용 중지된 아이디로 사이트 이용이 제한됩니다.";
				url = "redirect:commons/loginForm.do";
				
			} else {	// 사용가능한 아이디
				session.setAttribute("loginUser", loginUser);
				//session.setMaxInactiveInterval(60*6);
				session.setMaxInactiveInterval(600*6);
			}
			
			
		} catch (IdNotFoundException e) {
			message = "아이디가 존재하지 않습니다.";
			url = "redirect:commons/loginForm.do";
			
		} catch (InvalidPasswordException e) {
			message = "패스워드가 일치하지 않습니다.";
			url = "redirect:commons/loginForm.do";
			
		} catch (SQLException e) {
			e.printStackTrace();
			message = "시스템 장애로 로그인이 불가합니다.";
			url = "redirect:commons/loginForm.do";
		}
		
		session.setAttribute("msg", message);
		session.setAttribute("id", id);
		
		
		return url;
	}

}
