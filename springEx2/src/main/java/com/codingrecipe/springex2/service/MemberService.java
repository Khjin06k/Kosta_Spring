package com.codingrecipe.springex2.service;

import com.codingrecipe.springex2.dto.Member;

public interface MemberService {
    Member selectMember(String id) throws Exception;
    void insertMember(Member member) throws Exception;
}
