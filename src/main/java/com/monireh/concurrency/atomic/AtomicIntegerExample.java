package com.monireh.concurrency.atomic;

public class AtomicIntegerExample {

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerCounter counter =
                new AtomicIntegerCounter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Expected: 200000");
        System.out.println("Actual:   " + counter.getCount());
    }
}
