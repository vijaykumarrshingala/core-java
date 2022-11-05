package com.miit.sep22.java.bank.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingAccount extends Account {

    String permAddress;
    String tempAddress;
}
