package com.jsp.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.dto.BoardVO;
import com.jsp.service.BoardService;
import com.jsp.service.BoardServiceImpl;

public class BoardModifyFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "board/modifyBoard";
		
		try {
			int bno = Integer.parseInt(request.getParameter("bno"));
			
			BoardService service = BoardServiceImpl.getInstance();
			BoardVO board = service.getBoardForModify(bno);
			
			request.setAttribute("board", board);
			
		} catch (Exception e) {
			e.printStackTrace();
			url = "error/500";
		}
		
		return url;
	}
}