package com.monireh.concurrency.volatileexample;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class VolatileExampleTest {

    @Test
    void shouldStopWorkerWhenRunningIsSetToFalse() throws InterruptedException{
        VolatileExample worker = new VolatileExample();

        //-------- with CountDownLatch --------
        CountDownLatch startedLatch =
                new CountDownLatch(1);

        Thread workerThread = new Thread(() -> {

            startedLatch.countDown();

            worker.run();
        });

        workerThread.start();

        // Wait until the worker thread has started
        startedLatch.await();

        worker.stop();

        workerThread.join(1000);

        assertFalse(workerThread.isAlive());
        assertFalse(worker.isRunning());

        //-------- without CountDownLatch --------
//        Thread workerThread =
//                new Thread(worker::run);
//
//        workerThread.start();
//
//        Thread.sleep(100);
//
//        worker.stop();
//
//        workerThread.join(1000);
//
//        assertFalse(worker.isRunning());
    }
}
