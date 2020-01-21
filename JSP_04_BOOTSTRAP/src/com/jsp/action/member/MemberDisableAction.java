package com.jsp.action.member;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.action.Action;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;
import com.jsp.service.MemberServiceImpl;

public class MemberDisableAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "member/stopSuccess";
		
		String id = request.getParameter("id");
		
		MemberService service = MemberServiceImpl.getInstance();

		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginUser");
		
		if (id.equals(member.getId())) {	// 로그인한 사람을 정지시키려고할경우 실패
			url = "member/stopFail";
		} else {
			try {
				service.disable(id);
			} catch (SQLException e) {
				e.printStackTrace();
				url = "error/500";
			}
		}

		request.setAttribute("id", id);
		
		return url;
	}

}
