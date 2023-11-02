package com.codingrecipe.springex2.service;

import com.codingrecipe.springex2.dto.Account;

import java.util.List;
import java.util.Map;

public interface AccountService {
    void makeAccount(Account acc) throws Exception;
    Account selectAccount(String id) throws Exception;

    void updateAccountBalance(Account acc) throws Exception;

    List<Account> selectAccountList() throws Exception;
}
