package com.umar.apps.queue.concurrent;

import com.umar.apps.util.ThrowingConsumer;
import com.umar.apps.util.ThrowingFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@link java.util.concurrent.SynchronousQueue} Test
 *
 *  When we want to add an element to the queue, we need to call the put() method.
 *  That method will block until some other thread calls the take() method,
 *  signaling that it is ready to take an element.
 */
public class SynchronousQueueBackedQueueTest {

    private BlockingQueue<Integer> queue;
    private ExecutorService executorService;

    @BeforeEach
    void before() {
        queue = new SynchronousQueue<>();
        executorService = Executors.newFixedThreadPool(2);
    }

    @Test
    void givenSynchronousQueue_whenTwoThreadsExchangeUsingSynchronousQueue_thenSuccess() {
        Runnable producer = () -> {
            var producedElement = ThreadLocalRandom.current().nextInt();
            ThrowingConsumer<BlockingQueue<Integer>, InterruptedException> queueFunc = q -> queue.put(producedElement);
            ThrowingConsumer.unchecked(queueFunc).accept(queue);
            System.out.printf("Saving an element:%d to the exchange point\n", producedElement);
        };

        Runnable consumer = () -> {
            ThrowingFunction<BlockingQueue<Integer>, Integer, InterruptedException> queueFunc = BlockingQueue::take;
            var optionalResult = queueFunc.lift().apply(queue);
            optionalResult.ifPresent(i -> System.out.printf("Consumed element:%d\n", i));
        };

        executorService.execute(producer);
        executorService.execute(consumer);
        ThrowingFunction<ExecutorService, Boolean, InterruptedException> executorFunc = exe -> executorService.awaitTermination(500, TimeUnit.MILLISECONDS);
        executorFunc.lift().apply(executorService);
        assertThat(queue).hasSize(0);
    }
}
