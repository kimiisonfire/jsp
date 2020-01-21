package com.jsp.action.pds;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.controller.SearchCriteria;
import com.jsp.service.PdsService;
import com.jsp.service.PdsServiceImpl;

public class PdsListAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "pds/list";
		
		String page = request.getParameter("page");
		String perPageNum = request.getParameter("perPageNum");
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		
		SearchCriteria cri = new SearchCriteria();
		try {
			cri.setPage(Integer.parseInt(page));
			cri.setPerPageNum(Integer.parseInt(perPageNum));
		} catch (NumberFormatException e) {
			System.out.println("페이지 번호를 1로 세팅합니다.");
		}
		if (searchType != null && keyword != null) {
			cri.setSearchType(searchType);
			cri.setKeyword(keyword);
		}
		
		PdsService service = PdsServiceImpl.getInstance();
		
		try {
			Map<String, Object> dataMap =
					PdsServiceImpl.getInstance().getList(cri);
			
			request.setAttribute("dataMap", dataMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return url;
	}
}
