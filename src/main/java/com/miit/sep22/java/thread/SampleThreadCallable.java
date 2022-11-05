package com.miit.sep22.java.thread;

import java.util.Random;
import java.util.concurrent.Callable;

public class SampleThreadCallable implements Callable {


    @Override
    public Object call() throws Exception {

        Random random = new Random();
        Thread.sleep(3000);
        return random.nextInt();
    }
}
