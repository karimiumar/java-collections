package com.umar.apps.queue.concurrent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A {@link java.util.concurrent.ConcurrentLinkedQueue} is an unbounded, thread-safe, and non-blocking queue.
 */
public class ConcurrentLinkedQueueBackedQueueTest {

    private Queue<Integer> queue;
    private ExecutorService executorService;

    @BeforeEach
    void before() {
        queue = new ConcurrentLinkedQueue<>();
        executorService = Executors.newWorkStealingPool();
    }


    @Test
    void givenQueueWithEmpty_whenPolled_thenDoesNotBlockAccessingThreadAndReturnsNull() throws ExecutionException, InterruptedException, TimeoutException {
        Callable<Integer> pollingTask = ()-> queue.poll();
        var result = executorService.submit(pollingTask).get(100, TimeUnit.MILLISECONDS);
        executorService.shutdown();
        assertThat(result).isNull();
        assertThat(queue).hasSize(0);
        assertThat(executorService.isShutdown()).isTrue();
    }

    @Test
    void givenQueueWithElements_whenPolled_thenReturnsTheElementAtHead() throws ExecutionException, InterruptedException, TimeoutException {
        Runnable offeringTask = () -> {
            queue.offer(1);
            queue.offer(10);
            queue.offer(5);
            queue.offer(11);
        };
        executorService.submit(offeringTask);
        //Let complete offeringTask otherwise it may be possible that pollingTask will begin resulting in Assertion errors
        Thread.sleep(100);
        assertThat(queue).hasSize(4);
        Callable<Integer> pollingTask = ()-> queue.poll();
        var result1 = executorService.submit(pollingTask).get(100, TimeUnit.MILLISECONDS);
        var result2 = executorService.submit(pollingTask).get(100, TimeUnit.MILLISECONDS);
        var result3 = executorService.submit(pollingTask).get(100, TimeUnit.MILLISECONDS);
        var result4 = executorService.submit(pollingTask).get(100, TimeUnit.MILLISECONDS);
        var result5 = executorService.submit(pollingTask).get(100, TimeUnit.MILLISECONDS);
        executorService.shutdown();
        assertThat(result1).isEqualTo(1);
        assertThat(result2).isEqualTo(10);
        assertThat(result3).isEqualTo(5);
        assertThat(result4).isEqualTo(11);
        assertThat(result5).isNull();
        assertThat(queue).hasSize(0);
        assertThat(executorService.isShutdown()).isTrue();
    }
}
