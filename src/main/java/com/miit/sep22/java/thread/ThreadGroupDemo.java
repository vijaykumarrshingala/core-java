package com.miit.sep22.java.thread;

public class ThreadGroupDemo {

    public static void main(String[] args) {

        ThreadGroup threadGroup = new ThreadGroup("Notification_Thread_Group");

        Thread t1 = new Thread(threadGroup, new SampleThreadRunnable("Running1"));
        Thread t2 = new Thread(threadGroup, new SampleThreadRunnable("Running2"));
        Thread t3 = new Thread(threadGroup, new SampleThreadRunnable("Running3"));
        Thread t4 = new Thread(threadGroup, new SampleThreadRunnable("Running4"));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            System.out.println(threadGroup.activeCount());
            //threadGroup.interrupt();

            System.out.println("Thread group name = " + t1.getThreadGroup().getName());
        } catch (Exception e) {
            e.getMessage();
        }

    }
}
