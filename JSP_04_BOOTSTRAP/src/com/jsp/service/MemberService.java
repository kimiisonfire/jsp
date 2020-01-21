package com.jsp.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsp.controller.SearchCriteria;
import com.jsp.dto.MemberVO;
import com.jsp.exception.IdNotFoundException;
import com.jsp.exception.InvalidPasswordException;

public interface MemberService {
	
	// 로그인 기능
	void login(String id, String pwd) throws SQLException,
											 IdNotFoundException,
											 InvalidPasswordException;
	
	// 회원 가입
	void regist(MemberVO member) throws SQLException;
	
	// 회원 조회
	MemberVO getMember(String id) throws SQLException;
	
	// 회원 리스트
	List<MemberVO> getMemberList() throws SQLException;
	
	Map<String, Object> getMemberList(SearchCriteria cri) throws SQLException;

	// 회원 수정
	void modify(MemberVO member) throws SQLException;
	
	// 회원 삭제
	void remove(String id) throws SQLException;
	
	// 회원 비활성
	void disable(String id) throws SQLException;
}
