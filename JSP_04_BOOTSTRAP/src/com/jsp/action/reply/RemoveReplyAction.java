package com.jsp.action.reply;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.action.Action;
import com.jsp.controller.PageMaker;
import com.jsp.controller.SearchCriteria;
import com.jsp.dto.ReplyVO;
import com.jsp.request.RemoveReplyRequest;
import com.jsp.service.ReplyService;
import com.jsp.service.ReplyServiceImpl;

public class RemoveReplyAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = null;
		
		ObjectMapper mapper = new ObjectMapper();
		RemoveReplyRequest removeReq = 
				mapper.readValue(request.getReader(), RemoveReplyRequest.class);
		int rno = removeReq.getRno();
		
		ReplyService service = ReplyServiceImpl.getInstance();
		
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		try {
			service.removeReply(rno);
			
			SearchCriteria cri = new SearchCriteria();
			cri.setPerPageNum(5);
			Map<String, Object> dataMap = service.getReplyList(removeReq.getBno(), cri);
			
			int page = removeReq.getPage();
			PageMaker pageMaker = (PageMaker)dataMap.get("pageMaker");
			int realEndPage = pageMaker.getRealEndPage();
			
			if (page > realEndPage) {
				page = realEndPage;
			}
			
			out.print("SUCCESS,"+page);
		} catch (SQLException e) {
			e.printStackTrace();
			out.print("FAIL,1");
		}
		
		return url;
	}

}
