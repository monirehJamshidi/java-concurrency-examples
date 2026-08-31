package com.monireh.concurrency.synchronizedexample;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SynchronizedCounterTest {

    @Test
    void shouldIncrementSafelyWhenMultipleThreadsAreUsed()
            throws InterruptedException{
        SynchronizedCounter counter = new SynchronizedCounter();

        Thread thread1 = new Thread(() -> {
            for (int i=0; i<100_000; i++){
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

        assertEquals(200_000, counter.getCount());
    }
}
