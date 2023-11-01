package com.codingrecipe.springex2.dao;

import com.codingrecipe.springex2.dto.Member;

public interface MemberDao {
    Member selectMember(String id) throws Exception;

    void insertMember(Member member) throws Exception;
}
