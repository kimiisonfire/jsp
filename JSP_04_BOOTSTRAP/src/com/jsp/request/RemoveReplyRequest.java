package com.jsp.request;

public class RemoveReplyRequest {
	
	int bno;
	int page;
	int rno;
	
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	
	@Override
	public String toString() {
		return "RemoveReplyRequest [bno=" + bno + ", page=" + page + ", rno="
				+ rno + "]";
	}
	
	
	
	
}
