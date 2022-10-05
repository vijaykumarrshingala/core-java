package com.miit.sep22.java.account.test;

import com.miit.sep22.java.account.constant.AccountTypeEnum;
import com.miit.sep22.java.account.dto.Account;

public class AccountServiceApp {

    static final String name = "vijay";

    public static void main(String[] args) {

        Account account = new Account(101L,"Ajay",2000.0);

        account.getAccountBalance();

    }
}
