package com.umar.apps.stack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NoDuplicatesStackTest {

    @Test
    void given_Stack_when_instantiated_then_empty() {
        var stack = new NoDuplicatesStack(10);
        assertThat(stack.isEmpty()).isTrue();
    }

    @Test
    void given_Stack_when_a_value_is_pushed_then_size_is_1() {
        var stack = new NoDuplicatesStack(10);
        stack.push(1);
        assertThat(stack.size()).isEqualTo(1);
    }

    @Test
    void given_Stack_when_two_values_are_pushed_then_size_is_2() {
        var stack = new NoDuplicatesStack(10);
        stack.push(1);
        stack.push(4);
        assertThat(stack.size()).isEqualTo(2);
    }

    @Test
    void given_Stack_when_duplicate_values_are_pushed_then_size_is_4() {
        var stack = new NoDuplicatesStack(10);
        stack.push(1);//At 4th position
        stack.push(4);//At 3rd position
        stack.push(7);//At 2nd position
        stack.push(2);//At 1st position
        stack.push(4);//This is duplicate
        assertThat(stack.size()).isEqualTo(4);
    }

    @Test
    void given_Stack_of_five_when_popped_then_size_is_4() {
        var stack = new NoDuplicatesStack(10);
        stack.push(1);
        stack.push(4);
        stack.push(7);
        stack.push(2);
        stack.push(9);

        assertThat(stack.pop()).isEqualTo(9);
        assertThat(stack.size()).isEqualTo(4);
    }

    @Test
    void given_Stack_of_five_when_peek_then_size_is_5() {
        var stack = new NoDuplicatesStack(10);
        stack.push(1);
        stack.push(4);
        stack.push(7);
        stack.push(2);
        stack.push(9);
        assertThat(stack.peek()).isEqualTo(9);
        assertThat(stack.size()).isEqualTo(5);
    }

    @Test
    void given_Stack_of_five_when_search_4_then_1stIndex_returned() {
        var stack = new NoDuplicatesStack(10);
        stack.push(1);//At 5th position //0th index
        stack.push(4);//At 4th position //1st index
        stack.push(7);//At 3rd position //2nd index
        stack.push(2);//At 2nd position //3rd index
        stack.push(9);//At 1st position //4th index

        assertThat(stack.search(4)).isEqualTo(1);
    }

    @Test
    void given_Stack_of_five_when_search_7_then_2ndIndex_returned() {
        var stack = new NoDuplicatesStack(10);
        stack.push(1);//At 5th position //0th index
        stack.push(4);//At 4th position //1st index
        stack.push(7);//At 3rd position //2nd index
        stack.push(2);//At 2nd position //3rd index
        stack.push(9);//At 1st position //4th index

        assertThat(stack.search(7)).isEqualTo(2);
        assertThat(stack.size()).isEqualTo(5);
    }

    @Test
    void given_Stack_of_five_when_search_non_item_then_negativeOne_returned() {
        var stack = new NoDuplicatesStack(10);
        stack.push(1);//At 5th position //0th index
        stack.push(4);//At 4th position //1st index
        stack.push(7);//At 3rd position //2nd index
        stack.push(2);//At 2nd position //3rd index
        stack.push(9);//At 1st position //4th index

        assertThat(stack.search(109)).isEqualTo(-1);
        assertThat(stack.size()).isEqualTo(5);
    }

    @Test
    void given_Stack_of_five_when_signedIntegerPushed_thenPushedSuccessfully() {
        var stack = new NoDuplicatesStack(10);
        stack.push(-1);//At 5th position //0th index
        stack.push(-4);//At 4th position //1st index
        stack.push(+7);//At 3rd position //2nd index
        stack.push(-2);//At 2nd position //3rd index
        stack.push(+9);//At 1st position //4th index

        assertThat(stack.search(-1)).isEqualTo(0);
        assertThat(stack.size()).isEqualTo(5);
    }
}
