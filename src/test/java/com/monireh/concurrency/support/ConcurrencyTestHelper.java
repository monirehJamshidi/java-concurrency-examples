package com.monireh.concurrency.support;

import java.util.concurrent.CountDownLatch;

public class ConcurrencyTestHelper {
    private ConcurrencyTestHelper(){

    }

    public static void runConcurrently(
            int numberOfThreads,
            int operationsPerThread,
            Runnable operation
    ) throws InterruptedException {
        CountDownLatch startLatch =
                new CountDownLatch(1);

        CountDownLatch doneLatch =
                new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {

            Thread thread = new Thread(() -> {

                try {
                    startLatch.await();

                    for (int j = 0;
                         j < operationsPerThread;
                         j++) {

                        operation.run();
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
    }
}
