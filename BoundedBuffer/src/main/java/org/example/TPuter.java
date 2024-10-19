package org.example;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class TPuter implements Runnable {
    private final BoundedBuffer buffer;
    private final String name;
    private CountDownLatch startSignal;
    private CountDownLatch finishSignal;

    public TPuter(BoundedBuffer buffer, String name, CountDownLatch startSignal, CountDownLatch finishSignal) {
        this.buffer = buffer;
        this.name = name;
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            System.out.println("put start");
            while (buffer.getCount() > 0) {
                buffer.put(name + " " + buffer.getCount());
                System.out.println(Arrays.toString(buffer.getBuffer()));
            }
            finishSignal.countDown();
            System.out.println("put end");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
