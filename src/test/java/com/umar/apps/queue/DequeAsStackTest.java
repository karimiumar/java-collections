package com.umar.apps.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A test demonstrating use of {@link Deque} as a Stack which supports operations of a Stack
 */
public class DequeAsStackTest {

    private Deque<Integer> stack;

    @BeforeEach
    void before() {
        stack = new ArrayDeque<>();
    }

    @Test
    void givenStack_whenPopped_thenPopExceptions() {
        assertThatThrownBy(() -> stack.pop()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void givenStack_whenPush_thenElementsPushedInLIFO() {
        stack.push(5);
        stack.push(4);
        stack.push(3);
        stack.push(2);
        stack.push(1);
        assertThat(stack.peek()).isEqualTo(1);
        assertThat(stack).hasSize(5);
    }

    @Test
    void givenStack_whenPop_thenElementsPoppedInLIFO() {
        stack.push(5);
        stack.push(4);
        stack.push(3);
        stack.push(2);
        stack.push(1);

        assertThat(stack.pop()).isEqualTo(1);
        assertThat(stack.pop()).isEqualTo(2);
        assertThat(stack.pop()).isEqualTo(3);
        assertThat(stack.pop()).isEqualTo(4);
        assertThat(stack.pop()).isEqualTo(5);
        assertThat(stack).isEmpty();
    }
}
