package com.miit.sep22.java.thread;

import java.util.concurrent.*;

public class ThreadDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        //executorService.execute and submit
        ScheduledFuture scheduledFuture =
                scheduledExecutor.scheduleAtFixedRate(new SampleThreadRunnable("Running"), 2, 5, TimeUnit.SECONDS);


                scheduledExecutor.scheduleWithFixedDelay(new SampleThreadRunnable("Running"), 2, 5, TimeUnit.SECONDS);

        scheduledExecutor.schedule( () -> {
            System.out.println("Going to shutdown...");
            scheduledFuture.cancel(true);
            scheduledExecutor.shutdown();

        }, 10, TimeUnit.SECONDS);







    }




}
