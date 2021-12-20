package com.umar.apps.queue.concurrent;

import java.util.LinkedList;
import java.util.List;

/**
 * A bounded CustomBlockingQueue similar to a bounded Semaphore.
 *
 * This example is taken from Jenkov Jackob
 * https://dzone.com/articles/java-concurrency-blocking-queu
 *
 * @param <T> The type of the blocking queue to work with
 */
public class CustomBlockingQueue<T> {

    private final List<T> queue = new LinkedList<>();
    private final int limit;

    public CustomBlockingQueue(int limit) {
        this.limit = limit;
    }

    /**
     * A blocking method that blocks the enqueing thread from adding elements
     * when queue is full.
     *
     * @param t The parameterized typed to enqueue.
     */
    public synchronized void enqueue(T t) {
        while (queue.size() == limit) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //notify dequeing threads that the queue is full and
        //proceed with dequeing
        if(queue.size() == 0) {
            notifyAll();
        }
        queue.add(t);
    }

    /**
     * This method blocks dequeing threads if no more
     * elements are present in the queue.
     *
     * @return The parameterized type to return
     */
    public synchronized T deque() {
        while (queue.size() == 0) {
            try {
                wait();
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //notify enqueing thread that queue is available for
        //accepting more elements
        if(queue.size() == limit) {
            notifyAll();
        }
        return this.queue.remove(0);
    }

    /**
     * Returns the size of the CustomBlockingQueue
     *
     * @return The size of the CustomBlockingQueue
     */
    public int size() {
        return queue.size();
    }

    /**
     * Returns true if queue is empty false otherwise
     *
     * @return Returns a boolean
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns true if queue is not empty false otherwise
     *
     * @return Returns a boolean
     */
    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
