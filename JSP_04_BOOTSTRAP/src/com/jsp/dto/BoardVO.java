package com.jsp.dto;

import java.util.Date;

public class BoardVO {
	
	private int bno;			// 게시판 번호
	private String title;		// 제목
	private String content;		// 내용
	private String writer;		// 작성자
	private int viewcnt;		// 조회수
	private Date regdate;		// 등록날짜
	private Date updatedate;	// 수정날짜
	
	private int replycnt;		// 댓글 개수
	
	
	public BoardVO() {}

	public BoardVO(int bno, String title, String content, String writer,
			int viewcnt, Date regdate, Date updatedate, int replycnt) {
		super();
		this.bno = bno;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.viewcnt = viewcnt;
		this.regdate = regdate;
		this.updatedate = updatedate;
		this.replycnt = replycnt;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getViewcnt() {
		return viewcnt;
	}

	public void setViewcnt(int viewcnt) {
		this.viewcnt = viewcnt;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public int getReplycnt() {
		return replycnt;
	}

	public void setReplycnt(int replycnt) {
		this.replycnt = replycnt;
	}

	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title + ", content="
				+ content + ", writer=" + writer + ", viewcnt=" + viewcnt
				+ ", regdate=" + regdate + ", updatedate=" + updatedate
				+ ", replycnt=" + replycnt + "]";
	}
	
}
