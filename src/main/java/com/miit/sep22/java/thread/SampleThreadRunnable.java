package com.miit.sep22.java.thread;

public class SampleThreadRunnable implements Runnable {

    String taskName;

    public SampleThreadRunnable(String taskName) {
        this.taskName = taskName;
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
