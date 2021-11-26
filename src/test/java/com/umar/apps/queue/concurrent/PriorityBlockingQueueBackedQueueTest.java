package com.umar.apps.queue.concurrent;

import com.umar.apps.util.ThrowingConsumer;
import com.umar.apps.util.ThrowingFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class PriorityBlockingQueueBackedQueueTest {

    private BlockingQueue<Integer> queue;

    @BeforeEach
    void before() {
        queue = new PriorityBlockingQueue<>();
    }

    @Test
    void givenPriorityBlockingQueue_whenPolling_thenShouldOrderItems() {
        var polledElements = new ArrayList<>();
        queue.add(5);
        queue.add(3);
        queue.add(7);
        queue.add(1);
        queue.add(2);

        queue.drainTo(polledElements);

        assertThat(polledElements).containsExactly(1,2,3,5,7);
    }

    @Test
    void given_PriorityBlockingQueue_whenPollingEmptyQueue_thenShouldBlockQueue() {
        var polledElements = new ArrayList<>();
        var t1 = new Thread(() -> {
            while(true) {
                //take() should block
                var poll = ThrowingFunction.unchecked(throwable -> queue.take()).apply(queue);
                polledElements.add(poll);
                System.out.printf("Polled:%d\n", poll);
            }
        });

        t1.start();

        ThrowingConsumer.unchecked(throwable -> Thread.sleep(TimeUnit.SECONDS.toMillis(5))).accept(this);
        System.out.println("Adding to Queue");
        queue.addAll(List.of(1,6,3,8,2,10));
        ThrowingConsumer.unchecked(throwable -> Thread.sleep(TimeUnit.SECONDS.toMillis(2))).accept(this);
        assertThat(polledElements).containsExactly(1, 2, 3, 6, 8, 10);
    }
}
