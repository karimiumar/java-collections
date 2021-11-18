package com.umar.apps.tree;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NodeTest {

    @Test
    void given_value_when_null_then_throws_Exception() {
        assertThatThrownBy(() -> new Node<>(null)).hasMessage("Null value is not permitted");
    }

    @Test
    void given_value_when_not_null_then_Node_instantiated() {
        var node = new Node<>("A");
        assertThat(node).isNotNull();
        assertThat(node.left).isNull();
        assertThat(node.right).isNull();
        assertThat(node.value).isEqualTo("A");
    }
}
