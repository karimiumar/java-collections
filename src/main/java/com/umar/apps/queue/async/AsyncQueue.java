/**
 * Taken from
 * {@link https://stackoverflow.com/questions/28720291/how-to-implement-asynchronous-queue}
 */

package com.umar.apps.queue.async;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class AsyncQueue<E> {

    private final Queue<Consumer<E>> callbackQueue = new LinkedList<>();
    private final Queue<E> elementQueue = new LinkedList<>();

    /**
     * Upon add, check if there's a callback waiting, then call it,
     * otherwise put the element on the element queue
     *
     * @param e The Element to add
     */
    public synchronized void add(E e) {
        if(callbackQueue.size() > 0) {
            callbackQueue.remove().accept(e);
        }else {
            elementQueue.offer(e);
        }
    }

    /**
     * Upon poll, check if there's an element waiting,
     * then call the callback with that element,
     * otherwise put the callback on the callback queue
     *
     * @param consumer The consumer
     */
    public synchronized void poll(Consumer<E> consumer) {
        if(elementQueue.size() > 0) {
            consumer.accept(elementQueue.remove());
        } else {
            callbackQueue.add(consumer);
        }
    }
}
