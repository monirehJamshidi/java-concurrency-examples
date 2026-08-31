package com.monireh.concurrency.atomic;

import com.monireh.concurrency.support.ConcurrencyTestHelper;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtomicIntegerCounterTest {

    @Test
    void shouldIncrementSafelyWithMultipleThreads()
            throws InterruptedException{
        AtomicIntegerCounter counter =
                new AtomicIntegerCounter();

        int numberOfThreads = 10;
        int incrementsPerThread = 100_000;

        ConcurrencyTestHelper.runConcurrently(
                numberOfThreads,
                incrementsPerThread,
                counter::increment
        );

        int expected =
                numberOfThreads * incrementsPerThread;

        assertEquals(expected, counter.getCount());
    }
    @Test
    void shouldIncrementSafelyWhenMultipleThreadsAreUsed()
        throws InterruptedException {

        AtomicIntegerCounter counter =
                new AtomicIntegerCounter();

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

        startLatch.countDown();

        doneLatch.await();

        int expected =
                numberOfThreads * incrementsPerThread;

        assertEquals(expected, counter.getCount());
        //-------- with CountDownLatch --------
//        CountDownLatch startLatch =
//                new CountDownLatch(1);
//
//        CountDownLatch doneLatch =
//                new CountDownLatch(2);
//
//        Thread thread1 = new Thread(() -> {
//
//            try {
//                startLatch.await();
//
//                for (int i = 0; i < 100_000; i++) {
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
//        Thread thread2 = new Thread(() -> {
//
//            try {
//                startLatch.await();
//
//                for (int i = 0; i < 100_000; i++) {
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
//        startLatch.countDown();
//
//        doneLatch.await();

        //-------- without CountDownLatch --------
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

//        assertEquals(200_000, counter.getCount());
    }
}
