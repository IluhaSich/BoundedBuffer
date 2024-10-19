package org.example;

import java.util.concurrent.CountDownLatch;

public class TTaker implements Runnable {
    private final BoundedBuffer buffer;
    private final String name;
    private CountDownLatch startSignal;
    private CountDownLatch finishSignal;

    public TTaker(BoundedBuffer buffer, String name, CountDownLatch startSignal, CountDownLatch finishSignal) {
        this.buffer = buffer;
        this.name = name;
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            System.out.println("take start");
            while (buffer.getCount()> 0) {
                System.out.println(name + ":" + buffer.take());
            }
            finishSignal.countDown();
            System.out.println("take end");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

}
