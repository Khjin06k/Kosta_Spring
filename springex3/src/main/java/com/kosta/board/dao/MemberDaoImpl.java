package com.kosta.board.dao;

import com.kosta.board.dto.Member;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public Member selectMember(String id) throws Exception {
		return sqlSessionTemplate.selectOne("mapper.member.selectMember", id);
	}

	@Override
	public void insertMember(Member member) throws Exception {
		sqlSessionTemplate.insert("mapper.member.insertMember", member);
	}
}
