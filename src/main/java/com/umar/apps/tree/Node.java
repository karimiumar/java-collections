package com.umar.apps.tree;

import java.util.Objects;

public class Node<T extends Comparable<T>> {
    T value;
    Node<T> left;
    Node<T> right;

    public Node(T value) {
        Objects.requireNonNull(value, "Null value is not permitted");
        this.value =value;
        left = null;
        right = null;
    }
}
