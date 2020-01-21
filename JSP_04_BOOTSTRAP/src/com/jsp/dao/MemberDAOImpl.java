package com.jsp.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.jsp.controller.SearchCriteria;
import com.jsp.dto.MemberVO;
import com.jsp.mybatis.OracleMybatisSqlSession;

public class MemberDAOImpl implements MemberDAO {

	private SqlSessionFactory sessionFactory = 
			OracleMybatisSqlSession.getSqlSessionFactory();
	
	
	@Override
	public List<MemberVO> selectMemberList() throws SQLException {
		SqlSession session = sessionFactory.openSession(); 
		
		List<MemberVO> memberList = 
				session.selectList("Member-Mapper.selectSearchMemberList", null);
		session.close();
		
		return memberList;
	}

	@Override
	public List<MemberVO> selectMemberList(SearchCriteria cri)
			throws SQLException {
		// 검색객체 cri가 들어오고 결과를 perpage만큼씩만 가져와야함
		// 그만큼 자르는걸 db에서 하면 3중 select라서 java에서 하려고함
		SqlSession session = sessionFactory.openSession();
		
		// mybatis가 제공하는 rowBounds 사용하겠음
		// offset부터 시작해서 총 limit개
		int offset = cri.getPageStartRowNum();
		int limit = cri.getPerPageNum();
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<MemberVO> memberList = null;
		memberList = session.selectList
				("Member-Mapper.selectSearchMemberList", cri, rowBounds);
		session.close();
		
		return memberList;
	}

	@Override
	public int selectMemberListCount(SearchCriteria cri) throws SQLException {
		// 총 몇줄인지 가져오는데, 검색했을 경우 검색한 결과가 몇줄인지
		SqlSession session = sessionFactory.openSession();
		
		int count = 0;
		count = session.selectOne("Member-Mapper.selectSearchMemberListCount", cri);
		session.close();
		
		return count;
	}

	@Override
	public MemberVO selectMemberById(String id) throws SQLException {
		SqlSession session = sessionFactory.openSession();
		MemberVO member = session.selectOne("Member-Mapper.selectMemberById", id);
		session.close();
		return member;
	}

	@Override
	public void insertMember(MemberVO member) throws SQLException {
		SqlSession session = sessionFactory.openSession(true);
		
		session.update("Member-Mapper.insertMember", member);
		session.close();
	}

	@Override
	public void updateMember(MemberVO member) throws SQLException {
		SqlSession session = sessionFactory.openSession(true);

		session.update("Member-Mapper.updateMember", member);
		session.close();
	}

	@Override
	public void deleteMember(String id) throws SQLException {
		SqlSession session = sessionFactory.openSession(true);
		
		session.update("Member-Mapper.deleteMember", id);
		session.close();
	}

	@Override
	public void disableMember(String id) throws SQLException {
		SqlSession session = sessionFactory.openSession(true);
		
		session.update("Member-Mapper.disableMember", id);
		session.close();
	}

}
