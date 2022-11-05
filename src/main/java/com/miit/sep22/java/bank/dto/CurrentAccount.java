package com.miit.sep22.java.bank.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CurrentAccount extends Account {

    String gstNumber;
    String companyAddress;
}
