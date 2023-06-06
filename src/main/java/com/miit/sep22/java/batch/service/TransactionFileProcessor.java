package com.miit.sep22.java.batch.service;

import com.miit.sep22.java.batch.mapper.Mapper;
import com.miit.sep22.java.batch.mapper.TransactionMapper;
import com.miit.sep22.java.batch.model.TransactionDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class TransactionFileProcessor {

    FileReader<FileInputStream> reader = new TextFileReader<>();
    Mapper<TransactionDto, String> mapper = new TransactionMapper<>();
    ExecutorService executorService = Executors.newFixedThreadPool(3);


    String DEBIT_TXN_FILENAME = "/Users/vijay/IdeaProjects/miit/sep22/core-java/src/main/java/com/miit/sep22/java/batch/service/debitTxn.txt";
    String CREDIT_TXN_FILENAME = "/Users/vijay/IdeaProjects/miit/sep22/core-java/src/main/java/com/miit/sep22/java/batch/service/creditTxn.txt";

    public void process(String fileName) throws IOException {

        System.out.println("File name "+fileName);
        FileInputStream inputStream = reader.read(fileName);
        Scanner scanner = null;//new Scanner(inputStream, Charset.defaultCharset());
        List<TransactionDto> transactions = new ArrayList<>();

        while (scanner.hasNext()) {

            transactions.add(mapper.map(scanner.nextLine()));

            if(transactions.size() >=400) {

                Map<Boolean, List<TransactionDto>> listMap = transactions.stream()
                        .collect(Collectors.partitioningBy(transactionDto -> transactionDto.getAmount() > 0));
                executorService.submit(new TxnFileWriterThread(Collections.unmodifiableList(listMap.get(true)), CREDIT_TXN_FILENAME));
                executorService.submit(new TxnFileWriterThread(Collections.unmodifiableList(listMap.get(false)), DEBIT_TXN_FILENAME));
                transactions.clear();
            }
        }

        executorService.shutdown();
        inputStream.close();
        scanner.close();

        while (!executorService.isTerminated()){}
        System.out.println("All txn record validated and processed...");



    }
}
