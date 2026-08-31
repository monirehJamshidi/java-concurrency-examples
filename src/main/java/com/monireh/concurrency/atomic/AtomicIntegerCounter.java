package com.monireh.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCounter {

    private final AtomicInteger count =
            new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
