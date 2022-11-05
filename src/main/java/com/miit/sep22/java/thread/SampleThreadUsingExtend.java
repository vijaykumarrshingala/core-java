package com.miit.sep22.java.thread;

public class SampleThreadUsingExtend  extends  Thread {



    @Override
    public void run() {

        System.out.println("Inside SampleThreadRunnable class "+Thread.currentThread().getName());

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Run completed");
    }
}
