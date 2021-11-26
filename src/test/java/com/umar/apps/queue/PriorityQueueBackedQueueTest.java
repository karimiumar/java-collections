package com.umar.apps.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriorityQueueBackedQueueTest {

    private Queue<Integer> queue;

    @BeforeEach
    void before(){
        queue = new PriorityQueue<>();
    }

    @Test
    void givenPriorityBlockingQueue_whenPolling_thenShouldOrderItems() {
        queue.add(5);
        queue.add(3);
        queue.add(7);
        queue.add(1);
        queue.add(2);

        assertThat(queue.poll()).isEqualTo(1);
        assertThat(queue.poll()).isEqualTo(2);
        assertThat(queue.poll()).isEqualTo(3);
        assertThat(queue.poll()).isEqualTo(5);
        assertThat(queue.poll()).isEqualTo(7);
    }

    @Test
    void given_PriorityQueue_whenReverseComparatorUsed_thenPollElementsShouldBeReversed() {
        queue = new PriorityQueue<>(Comparator.reverseOrder());
        queue.add(5);
        queue.add(3);
        queue.add(7);
        queue.add(1);
        queue.add(2);

        assertThat(queue.poll()).isEqualTo(7);
        assertThat(queue.poll()).isEqualTo(5);
        assertThat(queue.poll()).isEqualTo(3);
        assertThat(queue.poll()).isEqualTo(2);
        assertThat(queue.poll()).isEqualTo(1);
    }

    @Test
    void givenPriorityQueue_whenOfferNull_thenThrowsException() {
        assertThatThrownBy(() -> queue.offer(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenPriorityQueue_whenDefaultCapacityLessThan1_thenThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new PriorityQueue<>(0)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenPriorityQueue_whenCreatedWithValidInitialCapacity_thenQueueInstantiatedSuccessfully() {
        queue = new PriorityQueue<>(2);
        assertThat(queue).hasSize(0);
    }

    @Test
    void givenPriorityQueue_whenMoreElementsAddedThenInitialCapacity_thenQueueGrows() {
        queue = new PriorityQueue<>(2);
        queue.add(1);
        queue.add(2);
        queue.add(1);
        queue.add(14);
        queue.add(8);
        assertThat(queue).hasSize(5);
    }
}
