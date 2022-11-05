package com.miit.sep22.java.thread.executor;

import com.miit.sep22.java.thread.SampleThreadCallable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleThreadPoolDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future futureOfCallable = executorService.submit(new SampleThreadCallable());
        System.out.println("Waiting for result...");
        System.out.println("Here is the result of callable "+futureOfCallable.get());
        executorService.shutdown();
    }
}
