package com.umar.apps.tree;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryTreeTest {

    @Test
    void when_new_BinaryTree_then_empty_BinaryTree_instantiated() {
        var bt = new BinaryTree<>();
        assertThat(bt).isNotNull();
        assertThat(bt.isEmpty()).isTrue();
    }

    @Test
    void given_BinaryTree_when_adding_elements_then_tree_contains_those_elements() {
        var bt = new BinaryTree<Integer>();
        bt.add(6);
        bt.add(4);
        bt.add(8);
        assertThat(bt.contains(8)).isTrue();
        assertThat(bt.contains(4)).isTrue();
        assertThat(bt.contains(6)).isTrue();
        assertThat(bt.contains(80)).isFalse();
    }

    @Test
    void given_BinaryTree_when_deleting_element_then_tree_removes_that_element() {
        var bt = new BinaryTree<Integer>();
        bt.add(6);
        bt.add(4);
        bt.add(8);

        bt.delete(4);
        assertThat(bt.contains(8)).isTrue();
        assertThat(bt.contains(6)).isTrue();
        assertThat(bt.contains(4)).isFalse();
    }

    @Test
    void when_given_BinaryTree_then_size_is_3() {
        var bt = new BinaryTree<Integer>();
        bt.add(6);
        bt.add(4);
        bt.add(8);
        assertThat(bt.size()).isEqualTo(3);
    }

    @Test
    void given_BinaryTree_WhenTraversingInOrder_ThenPrintValues() {
        var bt = new BinaryTree<Integer>();
        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);
        bt.traverseInOrder(bt.root);
    }

    @Test
    void given_BinaryTree_WhenTraversingPreOrder_ThenPrintValues() {
        var bt = new BinaryTree<Integer>();
        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);
        bt.traversePreOrder(bt.root);
    }

    @Test
    void given_BinaryTree_WhenTraversingPostOrder_ThenPrintValues() {
        var bt = new BinaryTree<Integer>();
        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);
        bt.traversePostOrder(bt.root);
    }
}
