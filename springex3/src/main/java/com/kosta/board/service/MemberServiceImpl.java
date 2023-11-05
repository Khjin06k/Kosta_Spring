package com.kosta.board.service;

import com.kosta.board.dao.MemberDao;
import com.kosta.board.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member selectMember(String id, String password) throws Exception {
        Member member = memberDao.selectMember(id);
        if(member == null || !member.getPassword().equals(password)){
            throw new Exception("로그인이 불가합니다");
        }
        return member;
    }

    @Override
    public void insertMember(Member member) throws Exception {
        memberDao.insertMember(member);
    }

    @Override
    public Boolean checkId(String id) throws Exception {
        return memberDao.selectMember(id)==null;
    }
}
