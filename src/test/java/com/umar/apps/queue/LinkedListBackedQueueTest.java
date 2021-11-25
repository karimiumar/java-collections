package com.umar.apps.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 *
 * A collection designed for holding elements prior to processing. Besides basic Collection operations,
 * queues provide additional insertion, extraction, and inspection operations. Each of these methods exists
 * in two forms: one throws an exception if the operation fails, the other returns a special value
 * (either null or false, depending on the operation). The latter form of the insert operation is designed
 * specifically for use with capacity-restricted Queue implementations; in most implementations,
 * insert operations cannot fail.
 *
 * A queue is defined as
 *  {@code
       public interface Queue<E> extends Collection<E> {

            Inserts the specified element into this queue if it is possible to do so
            immediately without violating capacity restrictions returning true upon success and
            throwing an IllegalStateException if no space is currently available.

            boolean add(E e) throws IllegalStateException, ClassCastException, IllegalArgumentException, NullPointerException;

            Inserts the specified element into this queue if it is possible to do so immediately
            without violating capacity restrictions. When using a capacity-restricted queue,
            this method is generally preferable to add(E), which can fail to insert an element only
            by throwing an exception

            boolean offer(E e) throws ClassCastException, IllegalArgumentException, NullPointerException

            E remove() throws NoSuchElementException - if the queue is empty
            E poll() Retrieves and removes the head of this queue or returns null if queue is empty.
            E element(). Retrieves but does not remove the head of this queue. For empty queue, throws NoSuchElementException
            E peek(). Retrieves but does not remove head of this queue. Returns a null if the queue is empty
       }
 *  }
 *
 *  Summary:
 * _________________________________________________________
 * |                  |  Throws Exception  |  Special Value
 * ---------------------------------------------------------
 * |    Insert        |  add(e)            | offer(e)
 * ---------------------------------------------------------
 * |    Remove        |  remove()          | poll()
 * ---------------------------------------------------------
 * |    Examine       |  element()         | peek()
 * ---------------------------------------------------------
 */
public class LinkedListBackedQueueTest {

    private Queue<String> queue;

    @BeforeEach
    void before() {
        queue = new LinkedList<>();
    }

    @Test
    void given_Queue_when_3_elements_added_to_queue_then_size_is_3() {
        queue.add("Mac");
        queue.add("Windows");
        queue.add("Linux");
        assertThat(queue).size().isEqualTo(3);
    }

    //E poll() Retrieves and removes the head of this queue or returns null if queue is empty.
    @Test
    void given_Queue_when_poll_called_on_empty_then_returns_null() {
        assertThat(queue.poll()).isNull();
    }

    @Test
    void given_Queue_when_poll_called_on_NonEmpty_then_returns_head() {
        queue.offer("Mac");
        queue.offer("Windows");
        assertThat(queue.poll()).isEqualTo("Mac");
        assertThat(queue.size()).isEqualTo(1);
    }

    //E remove() throws NoSuchElementException - if the queue is empty
    @Test
    void given_Queue_when_remove_called_on_empty_then_throws_NoSuchElementException() {
        assertThatThrownBy(() -> queue.remove()).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void given_Queue_when_remove_called_on_NonEmpty_then_removes_the_Head() {
        queue.offer("Mac");
        queue.offer("Windows");
        assertThat(queue.remove()).isEqualTo("Mac");
        assertThat(queue.size()).isEqualTo(1);
    }

    //E element(). Retrieves but does not remove the head of this queue. For empty queue, throws NoSuchElementException
    @Test
    void given_Queue_when_element_called_on_empty_then_throws_NoSuchElementException() {
        assertThatThrownBy(() -> queue.element()).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void given_Queue_when_element_called_on_NonEmpty_then_retrieves_the_Head() {
        queue.offer("Mac");
        queue.offer("Windows");
        assertThat(queue.element()).isEqualTo("Mac");
        assertThat(queue.size()).isEqualTo(2);
    }

    //E peek(). Retrieves but does not remove head of this queue. Returns a null if the queue is empty
    @Test
    void given_Queue_when_peek_called_on_empty_then_returns_null() {
        assertThat(queue.peek()).isNull();
    }

    @Test
    void given_Queue_when_peek_called_on_NonEmpty_then_returns_Head() {
        queue.offer("Mac");
        queue.offer("Windows");
        assertThat(queue.peek()).isEqualTo("Mac");
        assertThat(queue.size()).isEqualTo(2);
    }
}
