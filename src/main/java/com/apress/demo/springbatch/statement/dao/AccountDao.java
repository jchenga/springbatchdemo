package com.apress.demo.springbatch.statement.dao;

import com.apress.demo.springbatch.statement.domain.Account;

import java.util.List;

/**
 * Created by j1cheng on 2017-08-15.
 */
public interface AccountDao {
    Account findAccountByNumber(String accountNumber);
    void saveAccount(Account account);
    List<Account> loadAccounts();
}
