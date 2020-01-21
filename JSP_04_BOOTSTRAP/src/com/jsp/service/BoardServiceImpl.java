package com.jsp.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.controller.PageMaker;
import com.jsp.controller.SearchCriteria;
import com.jsp.dao.BoardDAO;
import com.jsp.dao.ReplyDAO;
import com.jsp.dto.BoardVO;

public class BoardServiceImpl implements BoardService {
	
	private static BoardServiceImpl instance=new BoardServiceImpl();
	
	private BoardServiceImpl() {}
	
	public static BoardServiceImpl getInstance() {
		return instance;
	}
	
	
	private BoardDAO boardDAO;
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO=boardDAO;
	}
	
	private ReplyDAO replyDAO;
	public void setReplyDAO(ReplyDAO replyDAO) {
		this.replyDAO = replyDAO;
	}

	@Override
	public BoardVO getBoardForModify(int bno) throws SQLException {
		BoardVO board = boardDAO.selectBoardByBno(bno);
		return board;
	}
	@Override
	public BoardVO getBoard(int bno) throws SQLException {
		BoardVO board = boardDAO.selectBoardByBno(bno);
		boardDAO.increaseViewCnt(bno);
		return board;
	}

	@Override
	public void write(BoardVO board) throws SQLException {
		int bno=boardDAO.selectBoardSeqNext();
		
		board.setBno(bno);
		
		boardDAO.insertBoard(board);
	}

	@Override
	public void modify(BoardVO board) throws SQLException {
		boardDAO.updateBoard(board);
	}

	@Override
	public void remove(int bno) throws SQLException {
		boardDAO.deleteBoard(bno);
	}
	@Override
	public Map<String, Object> getBoardList(SearchCriteria cri) throws SQLException {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		
		//현재 page 번호에 맞는 리스트를 perPageNum 개수 만큼 가져오기.
		List<BoardVO> boardList = boardDAO.selectBoardCriteria(cri);
		
		//전체 board 개수
		int totalCount=boardDAO.selectBoardCriteriaTotalCount(cri);
		
		for(BoardVO board : boardList) {
			int replycnt= replyDAO.countReply(board.getBno());
			board.setReplycnt(replycnt);
		}
		
		//PageMaker 생성.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(totalCount);
		
		dataMap.put("boardList", boardList);
		dataMap.put("pageMaker",pageMaker);
		
		
		return dataMap;
	}

}
