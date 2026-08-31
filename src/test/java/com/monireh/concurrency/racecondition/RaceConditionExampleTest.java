package com.monireh.concurrency.racecondition;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RaceConditionExampleTest {

    @Test
    void shouldDemonstrateRaceCondition()
            throws InterruptedException{
        RaceConditionExample counter = new RaceConditionExample();

        int incrementsPerThread = 100_000;

        CountDownLatch startLatch = new CountDownLatch(1);

        CountDownLatch doneLatch = new CountDownLatch(2);

        Thread thread1 = new Thread(() -> {

            try {
                startLatch.await();

                for (int i = 0; i < incrementsPerThread; i++) {
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

                for (int i = 0; i < incrementsPerThread; i++) {
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

        // Release both threads
        startLatch.countDown();

        // Wait until both threads finish
        doneLatch.await();

        int actual = counter.getCount();

        System.out.println("Expected: 200000");
        System.out.println("Actual:   " + actual);

        assertTrue(
                actual <= 200_000,
                "Counter cannot exceed expected value"
        );
    }
}
