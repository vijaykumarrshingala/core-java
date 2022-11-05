package com.miit.sep22.java.bank.services;

import com.miit.sep22.java.bank.dto.Account;

public interface AccountHandler {

    String openAccount(Account account);

    Account getAccount(String accountNumber, String pin);

    /**
     *
     * deposit
     * withdrawal
     * balanceCheck
     *
     */
}
