package com.miit.sep22.java.batch;

import com.miit.sep22.java.batch.service.TransactionFileProcessor;
import com.miit.sep22.java.batch.service.WithoutThreadTransactionFileProcessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class ApplicationDemo {

    public static void main(String[] args) {
        Instant start = Instant.now();
        System.out.println("Multi threading process start....");
        TransactionFileProcessor transactionFileProcessor = new TransactionFileProcessor();
        try {
            transactionFileProcessor.process("txn_data.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("MultiThread timeElapsed "+timeElapsed);
        System.out.println("Multi threading process end....");

        System.out.println("Main threading process start....");
        Instant start1 = Instant.now();

        WithoutThreadTransactionFileProcessor withoutThreadTransactionFileProcessor = new WithoutThreadTransactionFileProcessor();
        try {
            withoutThreadTransactionFileProcessor.process("txn_data.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Instant finish1 = Instant.now();
        long timeElapsed1 = Duration.between(start1, finish1).toMillis();
        System.out.println("SingleThread timeElapsed "+timeElapsed1);
        System.out.println("Main process end....");
    }
}
