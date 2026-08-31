package com.monireh.concurrency.racecondition;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RaceConditionExampleTest {

    @RepeatedTest(10)//@Test
    void shouldDemonstrateRaceCondition()
            throws InterruptedException{
        RaceConditionExample counter = new RaceConditionExample();



        //-------- with CountDownLatch for 10 Thread --------
        int numberOfThreads = 10;
        int incrementsPerThread = 100_000;

        CountDownLatch startLatch =
                new CountDownLatch(1);

        CountDownLatch doneLatch =
                new CountDownLatch(numberOfThreads);



        for (int i = 0; i < numberOfThreads; i++) {

            Thread thread = new Thread(() -> {

                try {
                    startLatch.await();

                    for (int j = 0;
                         j < incrementsPerThread;
                         j++) {

                        counter.increment();
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                } finally {
                    doneLatch.countDown();
                }
            });

            thread.start();
        }

        // Release all threads
        startLatch.countDown();

        // Wait until all threads finish
        doneLatch.await();

        int expected =
                numberOfThreads * incrementsPerThread;

        int actual = counter.getCount();

        System.out.println(
                "Expected: " + expected +
                        " | Actual: " + actual
        );

        assertTrue(actual <= expected);
        //-------- with CountDownLatch --------
//        int incrementsPerThread = 100_000;
//        CountDownLatch startLatch = new CountDownLatch(1);
//
//        CountDownLatch doneLatch = new CountDownLatch(2);
//
//        Thread thread1 = new Thread(() -> {
//
//            try {
//                startLatch.await();
//
//                for (int i = 0; i < incrementsPerThread; i++) {
//                    counter.increment();
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//
//            } finally {
//                doneLatch.countDown();
//            }
//
//        });
//
//        Thread thread2 = new Thread(() -> {
//
//            try {
//                startLatch.await();
//
//                for (int i = 0; i < incrementsPerThread; i++) {
//                    counter.increment();
//                }
//
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//
//            } finally {
//                doneLatch.countDown();
//            }
//        });
//
//        thread1.start();
//        thread2.start();
//
//        // Release both threads
//        startLatch.countDown();
//
//        // Wait until both threads finish
//        doneLatch.await();
//
//        int actual = counter.getCount();
//
//        System.out.println("Expected: 200000");
//        System.out.println("Actual:   " + actual);
//
//        assertTrue(
//                actual <= 200_000,
//                "Counter cannot exceed expected value"
//        );
    }
}
