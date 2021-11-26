package com.umar.apps.queue.concurrent;

import com.umar.apps.util.ThrowingFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DelayQueueBackedQueueTest {

    private BlockingQueue<DelayedEvent> queue;
    private ExecutorService executorService;

    @BeforeEach
    void before() {
        queue = new DelayQueue<>();
        executorService = Executors.newFixedThreadPool(2);
    }

    @Test
    void givenDelayQueue_whenProduceElement_thenShouldConsumeAfterGivenDelay() {
        int noOfElementsToProduce = 2;
        int delayOfEachProducedMessagesInMillis = 500;

        var consumer = new DelayQueueConsumer(queue, noOfElementsToProduce);
        var producer = new DelayQueueProducer(queue, noOfElementsToProduce, delayOfEachProducedMessagesInMillis);

        //when
        executorService.submit(producer);
        executorService.submit(consumer);

        //then
        ThrowingFunction.unchecked(throwable -> executorService.awaitTermination(5, TimeUnit.SECONDS)).apply(executorService);
        executorService.shutdown();

        assertThat(consumer.noOfElementsConsumed.get()).isEqualTo(2);
    }

    @Test
    void givenDelayQueue_whenProduceElementWithHugeDelay_thenConsumerNotAbleToConsumeMessageInGivenTime() {
        int noOfElementsToProduce = 1;
        int delayOfEachProducedMessagesInMillis = 10_500;
        var consumer = new DelayQueueConsumer(queue, noOfElementsToProduce);
        var producer = new DelayQueueProducer(queue, noOfElementsToProduce, delayOfEachProducedMessagesInMillis);

        //when
        executorService.submit(producer);
        executorService.submit(consumer);

        //then
        ThrowingFunction.unchecked(throwable -> executorService.awaitTermination(5, TimeUnit.SECONDS)).apply(executorService);
        executorService.shutdown();

        assertThat(consumer.noOfElementsConsumed.get()).isEqualTo(0);
    }

    @Test
    void givenDelayQueue_whenProducrElementWithNegativeDelay_thenConsumeMessageImmediately() {
        int noOfElementsToProduce = 1;
        int delayOfEachProducedMessagesInMillis = -10_500;
        var consumer = new DelayQueueConsumer(queue, noOfElementsToProduce);
        var producer = new DelayQueueProducer(queue, noOfElementsToProduce, delayOfEachProducedMessagesInMillis);

        //when
        executorService.submit(producer);
        executorService.submit(consumer);

        //then
        ThrowingFunction.unchecked(throwable -> executorService.awaitTermination(5, TimeUnit.SECONDS)).apply(executorService);
        executorService.shutdown();

        assertThat(consumer.noOfElementsConsumed.get()).isEqualTo(1);
    }
}
