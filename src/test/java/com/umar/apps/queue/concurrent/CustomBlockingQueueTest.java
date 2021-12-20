package com.umar.apps.queue.concurrent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomBlockingQueueTest {

    private CustomBlockingQueue<Integer> queue;

    @BeforeEach
    void before() {
        queue = new CustomBlockingQueue<>(5);
    }

    @Test
    void givenQueue_whenEmpty_thenDequeingThreadBlocks() throws InterruptedException {
        assertThat(queue.isEmpty()).isTrue();
        var dequeuer = new Thread(() -> {
            var value = queue.deque();
            System.out.println("Successfully dequeued: " + value);
        });
        dequeuer.start();
        dequeuer.join(2000);
        System.out.println("If message `Successfully dequeued: xx` was not printed then thread was blocked.");
    }

    @Test
    void givenQueue_whenHasItems_thenDequeingThreadDequeuesElements() throws InterruptedException {
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(6);
        assertThat(queue.size()).isEqualTo(3);
        while (queue.isNotEmpty()) {
            var dequeuer = new Thread(() -> {
                var value = queue.deque();
                System.out.println("Successfully dequeued: " + value);
            });
            dequeuer.start();
            dequeuer.join();
        }
    }

    @Test
    void givenQueue_whenFull_thenEnqueingThreadBlocks() throws InterruptedException {
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(6);
        queue.enqueue(12);
        queue.enqueue(13);
        var enqueuer = new Thread(() -> {
            queue.enqueue(7);
            System.out.println("Successfully enqueued: " + 7);
        });
        enqueuer.start();
        enqueuer.join(2000);
        System.out.println("If message `Successfully enqueued: 7` was not printed then thread was blocked.");
        assertThat(queue.isNotEmpty()).isTrue();
    }

    @Test
    void givenQueue_whenPartialFilled_thenEnqueingThreadSucceeds() throws InterruptedException {
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(6);
        var enqueuer = new Thread(() -> {
            queue.enqueue(7);
            System.out.println("Successfully enqueued: " + 7);
            queue.enqueue(70);
            System.out.println("Successfully enqueued: " + 70);
        });
        enqueuer.start();
        enqueuer.join(1000);
        assertThat(queue.size()).isEqualTo(5);
    }
}
