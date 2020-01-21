package com.jsp.action.board;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.dto.BoardVO;
import com.jsp.request.ModifyBoardRequest;
import com.jsp.service.BoardService;
import com.jsp.service.BoardServiceImpl;

public class BoardModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// parameter 저장
		int bno = Integer.parseInt(request.getParameter("bno"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		
		String page = request.getParameter("page");
		String perPageNum = request.getParameter("perPageNum");
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		
		BoardVO board = new ModifyBoardRequest(bno, title, content, writer).toBoard();
		
		// 화면 결정 (url)
		String url = "redirect:board/detail.do?bno=" + bno;
		// url 파라미터를 String으로 만들 경우 한글깨짐 방지
		url += "&page=" + page + "&perPageNum=" + perPageNum + "&searchType="
				+ searchType + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		
		// 서비스 인스턴스 생성
		BoardService service = BoardServiceImpl.getInstance();
		
		// 서비스 의뢰
		try {
			service.modify(board);
		} catch (SQLException e) {
			e.printStackTrace();
			url = "error/500";
		}
		
		// request.setAttr
		
		// 화면 리턴
		return url;
	}

}
