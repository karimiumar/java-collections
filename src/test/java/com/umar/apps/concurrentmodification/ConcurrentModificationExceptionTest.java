package com.umar.apps.concurrentmodification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConcurrentModificationExceptionTest {

    private List<Integer> integers;

    @BeforeEach
    void before() {
        integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
    }

    @Test
    void givenList_whenRemovingInLoop_thenConcurrentModificationException() {
        assertThatThrownBy(() -> {
            for (var i: integers) {
                integers.remove(i);
            }
        }).isExactlyInstanceOf(ConcurrentModificationException.class);
        assertThat(integers).containsExactly(2, 3, 4);
    }

    @Test
    void givenList_whenRemovingIterator_thenSuccess() {
        for(var iterator = integers.iterator(); iterator.hasNext();) {
            if(iterator.next() != null) {
                iterator.remove();
            }
        }
        assertThat(integers).hasSize(0);
    }

    @Test
    void givenList_whenRemovingUsingCollection$if_thenSuccess() {
        integers.removeIf(i -> i==2);
        assertThat(integers).hasSize(3);
        assertThat(integers).containsExactly(1, 3, 4);
    }

    @Test
    void givenList_whenRemovingUsingRemoveAll_thenSuccess() {
        var toRemove = integers.stream().filter(i -> i == 2).collect(Collectors.toList());
        integers.removeAll(toRemove);
        assertThat(integers).hasSize(3);
        assertThat(integers).containsExactly(1, 3, 4);
    }
}
