package com.codingrecipe.springex2.service;

import com.codingrecipe.springex2.dao.AccountDao;
import com.codingrecipe.springex2.dto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired // Autowired는
    private AccountDao accountDao; // servlet-context를 통해 빈을 생성하는 것이 아닌 @Service를 이용

    /*public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }*/

    @Override
    public void makeAccount(Account acc) throws Exception {
        accountDao.insertAccount(acc);
    }

    @Override
    public Account selectAccount(String id) throws Exception {
        System.out.println(id);
        return accountDao.selectAccount(id);
    }

    @Override
    public void updateAccountBalance(Account acc) throws Exception {
        accountDao.updateAccountBalance(acc);
    }

    @Override
    public List<Account> selectAccountList() throws Exception{
        return accountDao.selectAccountList();
    }
}
