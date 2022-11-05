package com.miit.sep22.java.batch.service;

import com.miit.sep22.java.batch.model.TransactionDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class TxnFileWriterThread implements Runnable {

    private List<TransactionDto> dtoList;
    private String fileName;

    TxnFileWriterThread(List<TransactionDto> dtoList, String fileName){
        this.dtoList = dtoList;
        this.fileName = fileName;
    }

    @Override
    public void run() {

        try {
            //Some business logic
            //Call to database to txn is valid or not
            //save to batch database
            StringBuffer buffer = new StringBuffer();
            dtoList.stream()
                    .map( t -> String.join(",", ""+t.getId(), t.getDescription(), ""+t.getAmount()))
                    .forEach(s -> buffer.append(s).append(System.getProperty("line.separator")));

            Files.write(Paths.get(fileName), buffer.toString().getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
