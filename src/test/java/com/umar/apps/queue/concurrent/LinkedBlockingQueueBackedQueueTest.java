package com.umar.apps.queue.concurrent;

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
public class LinkedBlockingQueueBackedQueueTest {

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
        ThrowingFunction.unchecked(throwable -> queue.offer("O'Hara", 2L, TimeUnit.SECONDS)).apply(queue);
        assertThat(queue).hasSize(3);
    }

    //Test remove()
    @Test
    void given_BlockingQueue_when_empty_then_remove_throws_Exception() {
        assertThat(queue.offer("Sara")).isTrue();
        assertThat(queue.offer("Zara")).isTrue();
        assertThat(queue.remove()).isEqualTo("Sara");
        assertThat(queue.remove()).isEqualTo("Zara");
        assertThat(queue).hasSize(0);
        assertThatThrownBy(() -> queue.remove());
    }

    //Test remove(o)
    @Test
    void given_BlockingQueue_when_remove_NonExistent_obj_then_returns_false() {
        assertThat(queue.offer("Sara")).isTrue();
        assertThat(queue.offer("Zara")).isTrue();
        assertThat(queue.remove("Lara")).isFalse();
    }

    //Test poll()
    @Test
    void given_BlockingQueue_when_poll_then_returns_head_or_null() {
        assertThat(queue.offer("Sara")).isTrue();
        assertThat(queue.offer("Zara")).isTrue();
        assertThat(queue.poll()).isEqualTo("Sara");
        assertThat(queue).hasSize(1);
        assertThat(queue.poll()).isEqualTo("Zara");
        assertThat(queue.poll()).isNull();
        assertThat(queue).hasSize(0);
    }

    //The take()
    @Test
    void given_BlockingQueue_when_take_then_returns_head_or_blocks_when_empty() {
        assertThat(queue.offer("Sara")).isTrue();
        assertThat(queue.offer("Zara")).isTrue();
        ThrowingConsumer.unchecked(throwable -> queue.take()).accept(queue);
        ThrowingConsumer.unchecked(throwable -> queue.take()).accept(queue);
        assertThat(queue).hasSize(0);
        var t1 = new Thread(() -> {
            //take() operation blocks as queue is empty
            ThrowingConsumer.unchecked(throwable -> queue.take()).accept(queue);
        });
        t1.start();
        Thread.currentThread().interrupt();
        assertThat(queue).hasSize(0);
    }

    //The poll(time, unit)
    @Test
    void given_BlockingQueue_when_poll_then_returns_head_or_blocks_when_empty_until_timeout() {
        assertThat(queue.offer("Sara")).isTrue();
        assertThat(queue.offer("Zara")).isTrue();
        ThrowingConsumer.unchecked(throwable -> queue.poll(1L, TimeUnit.MILLISECONDS)).accept(queue);
        ThrowingConsumer.unchecked(throwable -> queue.poll(1L, TimeUnit.MILLISECONDS)).accept(queue);
        assertThat(queue).hasSize(0);
        //poll(time, unit) operation blocks as queue is empty for the specified time
        ThrowingConsumer.unchecked(throwable -> queue.poll(2L, TimeUnit.SECONDS)).accept(queue);
        assertThat(queue).hasSize(0);
    }

    //calling peek() on populated queue
    @Test
    void given_BlockingQueue_when_3_elements_then_peek_returns_element_at_HEAD_but_does_not_remove_it_from_queue() {
        assertThat(queue.offer("Sara")).isTrue();
        assertThat(queue.offer("Zara")).isTrue();
        assertThat(queue.offer("Lara")).isTrue();
        assertThat(queue).hasSize(3);
        assertThat(queue).containsExactly("Sara", "Zara", "Lara");
        assertThat(queue.peek()).isEqualTo("Sara");
        assertThat(queue).hasSize(3);
        assertThat(queue).containsExactly("Sara", "Zara", "Lara");
    }

    //calling peek() on empty queue
    @Test
    void given_BlockingQueue_when_empty_then_peek_returns_null() {
        assertThat(queue).isEmpty();
        assertThat(queue.peek()).isNull();
    }

    //calling element() on populated queue
    @Test
    void given_BlockingQueue_when_3_elements_then_element_returns_element_at_HEAD_but_does_not_remove_it_from_queue() {
        assertThat(queue.offer("Sara")).isTrue();
        assertThat(queue.offer("Zara")).isTrue();
        assertThat(queue.offer("Lara")).isTrue();
        assertThat(queue).hasSize(3);
        assertThat(queue).containsExactly("Sara", "Zara", "Lara");
        assertThat(queue.element()).isEqualTo("Sara");
        assertThat(queue).hasSize(3);
        assertThat(queue).containsExactly("Sara", "Zara", "Lara");
    }

    //calling element() on empty queue
    @Test
    void given_BlockingQueue_when_empty_then_element_throws_Exception() {
        assertThat(queue).isEmpty();
        assertThatThrownBy(() -> queue.element());
    }

}
