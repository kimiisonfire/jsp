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
import com.jsp.service.ReplyService;
import com.jsp.service.ReplyServiceImpl;

public class RegistReplyAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = null;
		
		// json String이 왔을때 원하는 class로 매핑하는 역할
		ObjectMapper mapper = new ObjectMapper();
		//System.out.println("111  "+request.getReader().readLine());
		
		ReplyVO reply = mapper.readValue(request.getReader(), ReplyVO.class);
		//System.out.println("222  "+reply);
		
		ReplyService service = ReplyServiceImpl.getInstance();
		
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 마지막페이지 정보 가져와야합니다
		SearchCriteria cri = new SearchCriteria();
		cri.setPerPageNum(5);
		Map<String, Object> dataMap;
		
		// 결과는 success / fail text로 내보낼것
		try {
			service.registReply(reply);

			dataMap = service.getReplyList(reply.getBno(), cri);
			PageMaker pageMaker = (PageMaker)dataMap.get("pageMaker");
			int realEndPage = pageMaker.getRealEndPage();
			out.print("SUCCESS," + realEndPage);
			
		} catch (SQLException e) {
			e.printStackTrace();
			out.print("FAIL,1");
		}
		out.close();
		
		return url;
	}

}
