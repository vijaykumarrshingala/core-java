package com.miit.sep22.java.bank.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    Long id;
    String accountNumber;
    String accountName;
    double balance;
    double interestRate;
    String pin;
    String accountType;

}
