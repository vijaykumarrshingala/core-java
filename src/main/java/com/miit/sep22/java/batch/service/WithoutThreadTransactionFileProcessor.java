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

public class WithoutThreadTransactionFileProcessor {

    FileReader<FileInputStream> reader = new TextFileReader<>();
    Mapper<TransactionDto, String> mapper = new TransactionMapper<>();
    ExecutorService executorService = Executors.newFixedThreadPool(3);


    String DEBIT_TXN_FILENAME = "debitTxn2.txt";
    String CREDIT_TXN_FILENAME = "creditTxn2.txt";

    public File process(String fileName) throws IOException {

        FileInputStream inputStream = reader.read(fileName);
        Scanner scanner = null;//new Scanner(inputStream, Charset.defaultCharset());
        List<TransactionDto> transactions = new ArrayList<>();

        while (scanner.hasNext()) {

            transactions.add(mapper.map(scanner.nextLine()));
            Map<Boolean, List<TransactionDto>> listMap = transactions.stream()
                    .collect(Collectors.partitioningBy(transactionDto -> transactionDto.getAmount() > 0));
            new TxnFileWriterThread(Collections.unmodifiableList(listMap.get(true)), CREDIT_TXN_FILENAME).run();
            new TxnFileWriterThread(Collections.unmodifiableList(listMap.get(false)), DEBIT_TXN_FILENAME).run();
        }

        return null;
    }
}
