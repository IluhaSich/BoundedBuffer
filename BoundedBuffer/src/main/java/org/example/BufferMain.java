package org.example;



import java.util.concurrent.CountDownLatch;

public class BufferMain {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch finishSignal = new CountDownLatch(4);
        BoundedBuffer<String> buffer = new BoundedBuffer<>(10);
        int threadNumber = 2;
        for (int i = 0; i < threadNumber; i++){
            new Thread(new TPuter(buffer,"name: " + i,startSignal,finishSignal)).start();
            new Thread(new TTaker(buffer,"name: " + i,startSignal,finishSignal)).start();
        }
        System.out.println("start");
        startSignal.countDown();
        finishSignal.await();
        System.out.println("Завершено! count: " + buffer.getCount());
    }
}
