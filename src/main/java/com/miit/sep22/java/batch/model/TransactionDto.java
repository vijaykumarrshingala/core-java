package com.miit.sep22.java.batch.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {

    int id;
    String description;
    double amount;
}
