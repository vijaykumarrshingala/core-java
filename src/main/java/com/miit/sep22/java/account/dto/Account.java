package com.miit.sep22.java.account.dto;


public final class Account {


    private final String accountHolderName;
    private final long accountHolderId;
    private final double accountBalance;


    public Account(long accountHolderId, String accountHolderName, double accountBalance) {
        this.accountHolderName = accountHolderName;
        this.accountBalance = accountBalance;
        this.accountHolderId = accountHolderId;
    }


    public String getAccountHolderName() {
        return accountHolderName;
    }

    public long getAccountHolderId() {
        return accountHolderId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}
