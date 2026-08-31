package com.monireh.concurrency.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtomicIntegerCounterTest {
    @Test
    void shouldIncrementSafelyWhenMultipleThreadsAreUsed()
        throws InterruptedException {

        AtomicIntegerCounter counter =
                new AtomicIntegerCounter();

        CountDownLatch startLatch =
                new CountDownLatch(1);

        CountDownLatch doneLatch =
                new CountDownLatch(2);

        Thread thread1 = new Thread(() -> {

            try {
                startLatch.await();

                for (int i = 0; i < 100_000; i++) {
                    counter.increment();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            } finally {
                doneLatch.countDown();
            }
        });

        Thread thread2 = new Thread(() -> {

            try {
                startLatch.await();

                for (int i = 0; i < 100_000; i++) {
                    counter.increment();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            } finally {
                doneLatch.countDown();
            }
        });

        thread1.start();
        thread2.start();

        startLatch.countDown();

        doneLatch.await();
//        Thread thread1 = new Thread(() -> {
//            for (int i = 0; i < 100_000; i++) {
//                counter.increment();
//            }
//        });
//
//        Thread thread2 = new Thread(() -> {
//            for (int i = 0; i < 100_000; i++) {
//                counter.increment();
//            }
//        });

//        thread1.start();
//        thread2.start();
//
//        thread1.join();
//        thread2.join();

        assertEquals(200_000, counter.getCount());
    }
}
