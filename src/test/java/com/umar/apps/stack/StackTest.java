package com.umar.apps.stack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StackTest {

    @Test
    void given_Stack_when_instantiated_then_empty() {
        var stack = new Stack<Integer>();
        assertThat(stack.isEmpty()).isTrue();
    }

    @Test
    void given_Stack_when_a_value_is_pushed_then_size_is_1() {
        var stack = new Stack<Integer>();
        stack.push(1);
        assertThat(stack.size()).isEqualTo(1);
    }

    @Test
    void given_Stack_when_two_values_are_pushed_then_size_is_2() {
        var stack = new Stack<Integer>();
        stack.push(1);
        stack.push(4);
        assertThat(stack.size()).isEqualTo(2);
    }

    @Test
    void given_Stack_when_five_values_are_pushed_then_size_is_5() {
        var stack = new Stack<Integer>();
        stack.push(1);//At 5th position
        stack.push(4);//At 4th position
        stack.push(7);//At 3rd position
        stack.push(2);//At 2nd position
        stack.push(4);//At 1st position
        assertThat(stack.size()).isEqualTo(5);
    }

    @Test
    void given_Stack_of_five_when_popped_then_size_is_4() {
        var stack = new Stack<Integer>();
        stack.push(1);
        stack.push(4);
        stack.push(7);
        stack.push(2);
        stack.push(4);//At the top position. This will be popped

        assertThat(stack.pop()).isEqualTo(4);
        assertThat(stack.size()).isEqualTo(4);
    }

    @Test
    void given_Stack_of_five_when_peek_then_size_is_5() {
        var stack = new Stack<Integer>();
        stack.push(1);
        stack.push(4);
        stack.push(7);
        stack.push(2);
        stack.push(4);//At the top position. This will be peeked

        assertThat(stack.peek()).isEqualTo(4);
        assertThat(stack.size()).isEqualTo(5);
    }

    @Test
    void given_Stack_of_five_when_search_4_then_5th_position_returned() {
        var stack = new Stack<Integer>();
        stack.push(1);//At 5th position
        stack.push(4);//At 4th position
        stack.push(7);//At 3rd position
        stack.push(2);//At 2nd position
        stack.push(4);//At 1st position

        assertThat(stack.search(4)).isEqualTo(1);
    }

    @Test
    void given_Stack_of_five_when_search_7_then_3rd_position_returned() {
        var stack = new Stack<Integer>();
        stack.push(1);//At 5th position
        stack.push(4);//At 4th position
        stack.push(7);//At 3rd position
        stack.push(2);//At 2nd position
        stack.push(4);//At 1st position

        assertThat(stack.search(7)).isEqualTo(3);
        assertThat(stack.size()).isEqualTo(5);
    }

    @Test
    void given_Stack_of_five_when_search_non_item_then_negativeOne_returned() {
        var stack = new Stack<Integer>();
        stack.push(1);//At 5th position
        stack.push(4);//At 4th position
        stack.push(7);//At 3rd position
        stack.push(2);//At 2nd position
        stack.push(4);//At 1st position

        assertThat(stack.search(109)).isEqualTo(-1);
        assertThat(stack.size()).isEqualTo(5);
    }
}
