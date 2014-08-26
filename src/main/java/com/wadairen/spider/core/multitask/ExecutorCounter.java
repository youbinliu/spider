package com.wadairen.spider.core.multitask;

import java.util.concurrent.atomic.AtomicInteger;

final public class ExecutorCounter {
	private final AtomicInteger numberOfExecutors = new AtomicInteger(0);

    public void increase() {
        numberOfExecutors.incrementAndGet();
    }

    public void decrease() {
        numberOfExecutors.decrementAndGet();
    }

    public int value() {
        return numberOfExecutors.get();
    }
}
