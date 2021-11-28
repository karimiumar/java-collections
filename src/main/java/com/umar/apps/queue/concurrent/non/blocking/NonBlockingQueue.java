package com.umar.apps.queue.concurrent.non.blocking;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Taken from https://www.baeldung.com/lock-free-programming
 * @param <T>
 */
public class NonBlockingQueue <T> {

    private final AtomicReference<Node<T>> head;
    private final AtomicReference<Node<T>> tail;
    private final AtomicInteger size;

    public NonBlockingQueue() {
        this.head = new AtomicReference<>(null);
        this.tail = new AtomicReference<>(null);
        this.size = new AtomicInteger(0);
    }

    //Lock-free add
    public void add(T element) {
        Objects.requireNonNull(element, "Element is null");
        var node = new Node<>(element);
        Node<T> currentTail;
        do {
            currentTail = tail.get();
            node.previous = currentTail;
        }while (!tail.compareAndSet(currentTail, node));

        if(null != node.previous) {
            node.previous.next = node;
        }

        //for inserting first element
        head.compareAndSet(null, node);
        size.incrementAndGet();
    }

    //Lock-free get
    public T get() {
        if(null == head.get()){
            throw new NoSuchElementException();
        }
        Node<T> currentHead;
        Node<T> nextNode;
        do {
            currentHead = head.get();
            nextNode = currentHead.next;
        }while (!head.compareAndSet(currentHead, nextNode));

        size.decrementAndGet();
        return currentHead.value;
    }

    public int size() {
        return this.size.get();
    }

    private static class Node <T> {
        volatile T value;
        volatile Node<T> next;
        volatile Node<T> previous;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    }
}
