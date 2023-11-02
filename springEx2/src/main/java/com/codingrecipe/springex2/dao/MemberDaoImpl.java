package com.codingrecipe.springex2.dao;

import com.codingrecipe.springex2.dto.Member;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl implements MemberDao{
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

   /* public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }*/
    @Override
    public Member selectMember(String id) throws Exception {
        return sqlSessionTemplate.selectOne("mapper.member.selectMember", id);
    }

    @Override
    public void insertMember(Member member) throws Exception {
        sqlSessionTemplate.insert("mapper.member.insertMember", member);
    }
}
