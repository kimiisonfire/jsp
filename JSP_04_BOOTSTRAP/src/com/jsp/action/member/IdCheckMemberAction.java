package com.jsp.action.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;
import com.jsp.service.MemberServiceImpl;

public class IdCheckMemberAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = null;
		
		String id = request.getParameter("id");
		
		MemberService service = MemberServiceImpl.getInstance();
		MemberVO member = null;
		try {
			member = service.getMember(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if (member != null) {
			out.print("duplicated");
		}
		
		return url;
	}

}
