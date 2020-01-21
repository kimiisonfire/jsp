package com.jsp.dao;

import java.sql.SQLException;
import java.util.List;

import com.jsp.controller.SearchCriteria;
import com.jsp.dto.MemberVO;

public interface MemberDAO {
	
	// MemberVO 리스트 - 이건 거의 안쓸것같다네용
	List<MemberVO> selectMemberList() throws SQLException;
	
	// MemberVO 리스트
	List<MemberVO> selectMemberList(SearchCriteria cri) throws SQLException;
	
	// 검색 결과의 전체 리스트 개수
	int selectMemberListCount(SearchCriteria cri) throws SQLException;
	
	// id 조회 MemberVO
	MemberVO selectMemberById(String id) throws SQLException;
	
	// insert memberVo
	void insertMember(MemberVO member) throws SQLException;
	
	// update memberVo
	void updateMember(MemberVO member) throws SQLException;
	
	// id를 받아서 delete memberVo
	void deleteMember(String id) throws SQLException;
	
	// id를 받아서 disable memberVo
	void disableMember(String id) throws SQLException;
	
}
