package com.miit.sep22.java.bank.services;

import com.miit.sep22.java.bank.dto.Account;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {



    public Double getBalance(String accountNumber, String pin) {

        List<Account> accountList = AccountHandlerImplService.list;

        List<Account> account = accountList.stream()
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .filter(a -> a.getPin().equals(pin))
                .collect(Collectors.toList());
        if(account == null || account.size() == 0 || account.size() > 1) {
            //Custom exception
        }

        return account.get(0).getBalance();
    }

    public Double deposit(Account account, double amount) {

        List<Account> accountList = AccountHandlerImplService.list;

        List<Account> customerAccount = accountList.stream()
                .filter(a -> a.getAccountNumber().equals(account.getAccountNumber()))
                .filter(a -> a.getPin().equals(account.getPin()))
                .collect(Collectors.toList());
        if(customerAccount == null || customerAccount.size() == 0 || customerAccount.size() > 1) {
            //Custom exception
        }

        Account resultAccount = customerAccount.get(0);
        resultAccount.setBalance(resultAccount.getBalance()+amount);


        return resultAccount.getBalance();
    }


}
