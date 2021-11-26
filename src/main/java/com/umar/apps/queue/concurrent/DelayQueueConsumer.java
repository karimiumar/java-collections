package com.umar.apps.queue.concurrent;

import com.umar.apps.util.ThrowingFunction;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayQueueConsumer implements Runnable{

    private final BlockingQueue<DelayedEvent> queue;

    private final int noOfElementsToConsume;

    final AtomicInteger noOfElementsConsumed;

    public DelayQueueConsumer(BlockingQueue<DelayedEvent> queue, int noOfElementsToConsume) {
        this.queue = queue;
        this.noOfElementsToConsume = noOfElementsToConsume;
        this.noOfElementsConsumed = new AtomicInteger();
    }

    @Override
    public void run() {
        for (int i = 0; i < noOfElementsToConsume; i++) {
            var event = ThrowingFunction.unchecked(throwable -> queue.take()).apply(queue);
            noOfElementsConsumed.incrementAndGet();
            System.out.printf("Consumed: %s\n", event);
        }
    }
}
