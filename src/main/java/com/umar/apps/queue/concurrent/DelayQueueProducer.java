package com.umar.apps.queue.concurrent;

import com.umar.apps.util.ThrowingConsumer;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class DelayQueueProducer implements Runnable {

    private final BlockingQueue<DelayedEvent> queue;

    private final int noOfElementsToProduce;

    private final int delayOfEachProducedMessagesInMillis;

    public DelayQueueProducer(BlockingQueue<DelayedEvent> queue, int noOfElementsToProduce, int delayOfEachProducedMessagesInMillis) {
        this.queue = queue;
        this.noOfElementsToProduce = noOfElementsToProduce;
        this.delayOfEachProducedMessagesInMillis = delayOfEachProducedMessagesInMillis;
    }

    @Override
    public void run() {
        for (int i = 0; i < noOfElementsToProduce; i++) {
            var delayedEvent = new DelayedEvent(UUID.randomUUID().toString(), delayOfEachProducedMessagesInMillis);
            System.out.printf("Produced: %s\n", delayedEvent);
            ThrowingConsumer.unchecked(throwable -> queue.put(delayedEvent)).accept(queue);
            ThrowingConsumer.unchecked(throwable -> Thread.sleep(500)).accept(this);
        }
    }
}
