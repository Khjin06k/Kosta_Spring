package com.codingrecipe.springex2.service;

import com.codingrecipe.springex2.dao.AccountDao;
import com.codingrecipe.springex2.dto.Account;

import java.util.List;
import java.util.Map;

public class AccountServiceImpl implements AccountService{

    private AccountDao accountDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void makeAccount(Account acc) throws Exception {
        System.out.println("service" + acc.getId());
        accountDao.insertAccount(acc);
    }

    @Override
    public Account selectAccount(String id) throws Exception {
        System.out.println(id);
        return accountDao.selectAccount(id);
    }

    @Override
    public void updateAccountBalance(Map<String, Object> param) throws Exception {
        System.out.println(param);
        accountDao.updateAccountBalance(param);
    }

    @Override
    public List<Account> selectAccountList() throws Exception{
        return accountDao.selectAccountList();
    }
}
