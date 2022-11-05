package com.miit.sep22.java.thread;

public class SampleThreadRunnableWithGrp implements Runnable {

    ThreadGroup threadGroup;

    public SampleThreadRunnableWithGrp(ThreadGroup threadGroup) {
        this.threadGroup = threadGroup;
    }

    @Override
    public void run() {
        try {
            System.out.println("Running....");
            Thread.sleep(5000L);
            System.out.println("Complete....");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
