package com.monireh.concurrency.volatileexample;

public class VolatileExample {

    private volatile boolean running = true;

    public void run() {

        System.out.println("Worker started...");

        while (running) {
            // Simulate work
        }

        System.out.println("Worker stopped.");
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning(){
        return running;
    }


    public static void main(String[] args)
            throws InterruptedException {

        VolatileExample worker =
                new VolatileExample();

        Thread workerThread =
                new Thread(worker::run);

        workerThread.start();

        Thread.sleep(1000);

        System.out.println("Stopping worker...");

        worker.stop();

        workerThread.join();

        System.out.println("Main thread finished.");
    }
}
