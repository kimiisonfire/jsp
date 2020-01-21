package com.jsp.controller;

public class SearchCriteria {
	
	private int page;			// 페이지 번호
	private int perPageNum;		// 한 페이지당 리스트 개수
	private String searchType;	// 검색 구분
	private String keyword;		// 검색어
	
	public SearchCriteria() {
		this.page = 1;
		this.perPageNum = 10;
		this.searchType = "";
		this.keyword = "";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getPageStartRowNum() {	// 각 페이지마다 시작하는 행의 번호
		return (this.page-1) * this.perPageNum;
	}
	
	@Override
	public String toString() {
		return "SearchCriteria [page=" + page + ", perPageNum=" + perPageNum
				+ ", searchType=" + searchType + ", keyword=" + keyword + "]";
	}
}
