package com.codingrecipe.springex2.dao;

import com.codingrecipe.springex2.dto.Account;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public class AccountDaoImpl implements AccountDao{
    // sqlSessionTemplate에 대한 의존성? 주입??
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate; // contextParam으로 만들어 놓은 것, 이미 만들어 놓은 것이 있기 때문에 Autowired가 가능

    /*public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }*/

    @Override
    public void insertAccount(Account acc) throws Exception {
        sqlSessionTemplate.insert("mapper.account.insertAccount",acc);
    }

    @Override
    public Account selectAccount(String id) throws Exception {
        return sqlSessionTemplate.selectOne("mapper.account.selectAccount", id);
    }

    @Override
    @Transactional
    public void updateAccountBalance(Account acc) throws Exception {
        sqlSessionTemplate.update("mapper.account.updateBalance", acc);
    }

    @Override
    public List<Account> selectAccountList() throws Exception {
        return sqlSessionTemplate.selectList("mapper.account.selectAccountList");
    }
}
