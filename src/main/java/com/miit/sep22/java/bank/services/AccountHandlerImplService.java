package com.miit.sep22.java.bank.services;

import com.miit.sep22.java.bank.dto.Account;
import com.miit.sep22.java.bank.dto.SavingAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountHandlerImplService implements AccountHandler {


    public static List<Account> list = new ArrayList();

    @Override
    public String openAccount(Account account) {

        if(account instanceof SavingAccount) {
            account.setAccountNumber("SAVING_"+String.valueOf(Math.random()));
            list.add(account);
        } else {
            account.setAccountNumber("CURRENT_"+String.valueOf(Math.random()));
            list.add(account);
        }
        return account.getAccountName();
    }


    public Account getAccount(String accountNumber, String pin){

        List<Account> account = list.stream()
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .filter(a -> a.getPin().equals(pin))
                .collect(Collectors.toList());
        if(account == null || account.size() == 0 || account.size() > 1) {
            //Custom exception
        }

        return account.get(0);
    }
}
