package com.jsp.action.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.controller.SearchCriteria;
import com.jsp.service.MemberService;
import com.jsp.service.MemberServiceImpl;

public class MemberListAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "member/list";
		
		MemberService service = MemberServiceImpl.getInstance();

		// request에서 pagenum, perpagenum, ... 을 파라미터로 받아야함
		// Integer.parseInt는 무조건 try-catch 해줘야함
		SearchCriteria cri = new SearchCriteria();
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			int perPageNum = Integer.parseInt(request.getParameter("perPageNum"));
			cri.setPage(page);
			cri.setPerPageNum(perPageNum);
			
		} catch (NumberFormatException e) {
			System.out.println("페이지 번호 누락으로 기본 1페이지로 세팅됩니다.");
		}
		
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		cri.setSearchType(searchType);
		cri.setKeyword(keyword);
		
		try {
			
			Map<String, Object> dataMap = service.getMemberList(cri);
			request.setAttribute("memberList", dataMap.get("memberList"));
			request.setAttribute("pageMaker", dataMap.get("pageMaker"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return url;
	}

}