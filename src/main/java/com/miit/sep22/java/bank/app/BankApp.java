package com.miit.sep22.java.bank.app;

import com.miit.sep22.java.bank.dto.Account;
import com.miit.sep22.java.bank.dto.SavingAccount;
import com.miit.sep22.java.bank.services.AccountHandler;
import com.miit.sep22.java.bank.services.AccountHandlerImplService;
import com.miit.sep22.java.bank.services.TransactionService;

import java.sql.Savepoint;

public class BankApp {

    public static void main(String[] args) {

        AccountHandler accountHandler = new AccountHandlerImplService();
        TransactionService transactionService = new TransactionService();


        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setPermAddress("abc");
        savingAccount.setTempAddress("xyz");
        savingAccount.setAccountName("James");
        savingAccount.setAccountType("SAVING_ACC");
        savingAccount.setBalance(10.0);
        savingAccount.setPin("123@miit");

        accountHandler.openAccount(savingAccount);

        //accNum, pin, amount

        Account account = accountHandler.getAccount("accNum", "pinCode");

        double updatedBalance = transactionService.deposit(account, 100.0);
        System.out.println("Updated bal = "+updatedBalance);





    }
}
