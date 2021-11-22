package com.umar.apps.queue;

import com.umar.apps.util.ThrowingConsumer;
import com.umar.apps.util.ThrowingFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A BlockingQueue is defined as
 *
 * {@code
 *      public interface BlockingQueue<E> extends Queue<E> {
 *
 *      }
 * }
 *
 * A Queue that additionally supports operations that wait for the queue to become non-empty when retrieving an element,
 * and wait for space to become available in the queue when storing an element.
 *
 * BlockingQueue methods come in four forms, with different ways of handling operations that cannot be satisfied
 * immediately, but may be satisfied at some point in the future: one throws an exception,
 * the second returns a special value (either null or false, depending on the operation),
 * the third blocks the current thread indefinitely until the operation can succeed,
 * and the fourth blocks for only a given maximum time limit before giving up.
 *
 * These methods are summarized in the following table:
 * ___________________________________________________________________________________________
 * |                  |  Throws Exception  |  Special Value | Blocks    | Times out
 * -------------------------------------------------------------------------------------------
 * |    Insert        |  add(e)            | offer(e)       | put(e)    | offer(e, time, unit)
 * -------------------------------------------------------------------------------------------
 * |    Remove        |  remove()          | poll()         | take()    | poll(time, unit)
 * -------------------------------------------------------------------------------------------
 * |    Examine       |  element()         | peek()         | NA        | NA
 * -------------------------------------------------------------------------------------------
 */
public class BlockingQueueTest {

    private BlockingQueue<String> queue;

    @BeforeEach
    void before() {
        queue = new LinkedBlockingDeque<>(3);
    }

    //The add(e) method
    @Test
    void given_BlockingQueue_when_add_4th_element_then_throws_IllegalStateException() {
        queue.add("Sara");
        queue.add("Zara");
        queue.add("Lara");
        assertThatThrownBy(() -> queue.add("O'Hara")).isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("Deque full");
    }

    //The offer(e) method
    @Test
    void given_BlockingQueue_when_offer_4th_element_then_returns_false() {
        assertThat(queue.offer("Sara")).isTrue();
        assertThat(queue.offer("Zara")).isTrue();
        assertThat(queue.offer("Lara")).isTrue();
        assertThat(queue.offer("O'Hara")).isFalse(); //if (count >= capacity) return false;
        assertThat(queue).hasSize(3);
    }

    //The put(e) method blocks the request when the queue is full.
    @Test
    void given_BlockingQueue_when_put_4th_element_then_blocks() {
        ThrowingConsumer.unchecked(throwable -> queue.put("Sara")).accept(queue);
        ThrowingConsumer.unchecked(throwable -> queue.put("Zara")).accept(queue);
        ThrowingConsumer.unchecked(throwable -> queue.put("Lara")).accept(queue);
        var t1= new Thread(() -> ThrowingConsumer.unchecked(throwable -> queue.put("O'Hara")).accept(queue));
        t1.start();
        Thread.currentThread().interrupt();
        assertThat(queue).hasSize(3);
    }

    //The offer(e, time, unit). Waits for 3 seconds for the last offer() and then quits.
    @Test
    void given_BlockingQueue_when_offer_4th_element_then_quits_on_timeout() {
        assertThat(queue.offer("Sara")).isTrue();
        assertThat(queue.offer("Zara")).isTrue();
        assertThat(queue.offer("Lara")).isTrue();
        ThrowingFunction.unchecked(throwable -> queue.offer("O'Hara", 3L, TimeUnit.SECONDS)).apply(queue);
        assertThat(queue).hasSize(3);
    }

}
