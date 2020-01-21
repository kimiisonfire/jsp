package com.jsp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jsp.dao.AttachDAO;
import com.jsp.dao.BoardDAO;
import com.jsp.dao.MemberDAO;
import com.jsp.dao.PdsDAO;
import com.jsp.dao.ReplyDAO;
import com.jsp.service.BoardService;
import com.jsp.service.BoardServiceImpl;
import com.jsp.service.MemberService;
import com.jsp.service.MemberServiceImpl;
import com.jsp.service.PdsService;
import com.jsp.service.PdsServiceImpl;
import com.jsp.service.ReplyService;
import com.jsp.service.ReplyServiceImpl;

public class InitBeanListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent ctxEvent) {
    	
		String daoType = ctxEvent.getServletContext().getInitParameter("memberDAO");
		String boardDao = ctxEvent.getServletContext().getInitParameter("boardDAO");
		String replyDao = ctxEvent.getServletContext().getInitParameter("replyDAO");
		String pdsDao = ctxEvent.getServletContext().getInitParameter("pdsDAO");
		String attachDao = ctxEvent.getServletContext().getInitParameter("attachDAO");
		
		MemberDAO memberDAO = null;
		BoardDAO boardDAO = null;
		ReplyDAO replyDAO = null;
		PdsDAO pdsDAO = null;
		AttachDAO attachDAO = null;
		
		try {
			memberDAO = (MemberDAO)Class.forName(daoType).newInstance();
			boardDAO = (BoardDAO)Class.forName(boardDao).newInstance();
			replyDAO = (ReplyDAO)Class.forName(replyDao).newInstance();
			pdsDAO = (PdsDAO)Class.forName(pdsDao).newInstance();
			attachDAO = (AttachDAO)Class.forName(attachDao).newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Bean 조립 실패 따흑흑.,");
			return;
		}
		
		// 인스턴스 할당
		MemberService service = MemberServiceImpl.getInstance();
		BoardService boardService = BoardServiceImpl.getInstance();
		ReplyService replyService = ReplyServiceImpl.getInstance();
		PdsService pdsService = PdsServiceImpl.getInstance();
		
		// 조립
		((MemberServiceImpl)service).setMemberDAO(memberDAO);
		((BoardServiceImpl)boardService).setBoardDAO(boardDAO);
		((BoardServiceImpl)boardService).setReplyDAO(replyDAO);
		((ReplyServiceImpl)replyService).setReplyDAO(replyDAO);
		((PdsServiceImpl)pdsService).setPdsDAO(pdsDAO);
		((PdsServiceImpl)pdsService).setAttachDAO(attachDAO);
		
		
		System.out.println("with Listener : MemberDao 조립 완성. Dao = " + daoType);
		System.out.println("with Listener : BoardDdo 조립 완성. Dao = " + boardDao);
		System.out.println("with Listener : ReplyDao 조립 완성. Dao = " + replyDao);
		System.out.println("with Listener : PdsDao 조립 완성. Dao = " + pdsDao);
		System.out.println("with Listener : AttachDao 조립 완성. Dao = " + attachDao);
    }

    public void contextDestroyed(ServletContextEvent ctxEvent) {
        
    }
	
}
