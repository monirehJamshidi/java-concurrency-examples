package com.monireh.concurrency.volatileexample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class VolatileExampleTest {

    @Test
    void shouldStopWorkerWhenRunningIsSetToFalse() throws InterruptedException{
        VolatileExample worker = new VolatileExample();

        Thread workerThread =
                new Thread(worker::run);

        workerThread.start();

        Thread.sleep(100);

        worker.stop();

        workerThread.join(1000);

        assertFalse(worker.isRunning());
    }
}
