package com.kosta.board.service;

import com.kosta.board.dto.Member;

public interface MemberService {
    Member selectMember(String id, String password) throws Exception;

    void insertMember(Member member) throws Exception;

    Boolean checkId(String id) throws Exception;

}
