package com.umar.apps.stack;

/**
 * Taken from https://levelup.gitconnected.com/selfmade-stack-class-in-java-d401dc7d68f0
 * An interface for creating a Stack
 *
 * @param <T> The Type of the Stack
 */
public interface Stackable<T> {

    /**
     * Pushes a new element on top of Stack
     *
     * @param value The value to push
     */
    void push(T value);

    /**
     * Pops the top element on the Stack
     * Once an element is popped from the Stack, its size is decremented by 1.
     *
     * @return The element popped.
     */
    T pop();

    /**
     * Peeks the top element on the Stack. Doesn't pop any element.
     *
     * @return The value of the element on top of the Stack
     */
    T peek();

    /**
     * Returns true if the {@link Stackable} is empty, false otherwise
     *
     * @return Returns a boolean
     */
    boolean isEmpty();

    /**
     * Return the number of elements in {@link Stackable}
     *
     * @return The size of {@link Stackable}
     */
    int size();

    /**
     * Searches for a given object in the {@link Stackable}
     *
     * @param o The object to lookup
     * @return The index location of the element if found -1 otherwise
     */
    int search(Object o);
}
