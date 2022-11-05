package com.miit.sep22.java.batch.service;

import com.miit.sep22.java.batch.mapper.Mapper;
import com.miit.sep22.java.batch.mapper.TransactionMapper;
import com.miit.sep22.java.batch.model.TransactionDto;

import java.io.FileInputStream;
import java.io.IOException;

public class TextFileReader<T extends FileInputStream> implements FileReader {

    Mapper<TransactionDto, String> mapper = new TransactionMapper<>();
    @Override
    public FileInputStream read(String name) throws IOException {
        System.out.println("read file "+name);
        return new FileInputStream("/Users/vijay/IdeaProjects/miit/sep22/core-java/src/main/java/com/miit/sep22/java/batch/service/txn_data.csv");
    }
}
