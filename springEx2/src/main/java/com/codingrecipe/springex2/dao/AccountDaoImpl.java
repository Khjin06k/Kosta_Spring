package com.codingrecipe.springex2.dao;

import com.codingrecipe.springex2.dto.Account;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public class AccountDaoImpl implements AccountDao{
    // sqlSessionTemplate에 대한 의존성? 주입??
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public void insertAccount(Account acc) throws Exception {
        sqlSessionTemplate.insert("mapper.account.insertAccount");
    }

    @Override
    public Account selectAccount(String id) throws Exception {
        return sqlSessionTemplate.selectOne("mapper.account.selectAccount", id);
    }

    @Override
    @Transactional
    public void updateAccountBalance(Map<String, Object> param) throws Exception {
        sqlSessionTemplate.update("mapper.account.updateBalance", param);
    }

    @Override
    public List<Account> selectAccountList() throws Exception {
        return sqlSessionTemplate.selectList("mapper.account.selectAccountList");
    }
}
