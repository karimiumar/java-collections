package com.umar.apps.stack;

/**
 * The Stack class represents a last-in-first-out (LIFO) stack of objects.
 *
 * @param <T> The type of objects. Remember to override the equals method for any Custom object type.
 */
public class Stack<T> implements Stackable<T>{

    private Stack<T> previous;
    private T value;

    Stack(){}

    Stack(T value) {
        this.value = value;
    }

    Stack(Stack<T> previous, T value) {
        this(value);
        this.previous = previous;
    }

    @Override
    public void push(T value) {
        this.previous = new Stack<>(this.previous, this.value);
        this.value = value;
    }

    @Override
    public T pop() {
        if(this.isEmpty()) {
            throw new IllegalArgumentException("Stack is empty");
        }
        T top = this.value;
        this.value = this.previous.value;
        this.previous = this.previous.previous;
        return top;
    }

    @Override
    public T peek() {
        return this.value;
    }

    @Override
    public boolean isEmpty() {
        return this.previous == null;
    }

    @Override
    public int size() {
        return this.isEmpty() ? 0: 1 + this.previous.size();
    }

    @Override
    public int search(Object obj) {
        int count = 1;
        for(Stack<T> stack = this; !stack.isEmpty(); stack = stack.previous) {
            if(stack.value.equals(obj)) {
                return count;
            }
            count++;
        }
        return -1;
    }
}
