package com.umar.apps.map;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HashMapUniqueObjectTest {

    @Test
    void when_same_hash_and_diff_equals_then_objects_stored_in_entrySets_at_same_bucket() {
        var creationTime = LocalDateTime.now();
        //added to a new bucket having hash 0. A single Node is added
        var obj1 = new UniqueObject("pollinating sandboxes", creationTime);
        //added to same bucket having hash 0. Another Node is added to the same bucket
        var obj2 = new UniqueObject("schoolworks = perversive", creationTime);

        HashMap<UniqueObject, String> map = new HashMap<>();
        //hash of obj1 nad obj2 are 0 hence, at bucket zero,
        //hashmap creates a linkedlist and stores obj1 in first node and obj2 in other node.
        map.put(obj1, obj1.getKey());
        map.put(obj2, obj2.getKey());
        assertThat(map.entrySet()).hasSize(2);
        assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode());
        assertThat(obj1).isNotEqualTo(obj2);
        assertThat(map).containsKey(obj1);
        assertThat(map).containsKey(obj2);
    }

    @Test
    void given_UniqueObject_having_same_contents_when_stored_more_than_once_in_map_then_map_contains_OnlyOne() {
        var creationTime = LocalDateTime.now();
        var obj3 = new UniqueObject("perversive", creationTime);
        var obj4 = new UniqueObject("perversive", creationTime);
        HashMap<UniqueObject, String> map = new HashMap<>();
        map.put(obj3, obj3.getKey());
        map.put(obj4, obj4.getKey());
        assertThat(obj3).isEqualTo(obj4);
        assertThat(obj3.hashCode()).isEqualTo(obj4.hashCode());
        assertThat(map.entrySet()).hasSize(1);
        assertThat(map).containsKey(obj3);
        assertThat(map).containsKey(obj4);
    }

    @Test
    void given_UniqueObject_when_diff_creationTime_then_map_contains_all_objects() {
        var obj3 = new UniqueObject("perversive", LocalDateTime.now());
        var obj4 = new UniqueObject("perversive", LocalDateTime.now().plus(2L, ChronoUnit.SECONDS));
        HashMap<UniqueObject, String> map = new HashMap<>();
        map.put(obj3, obj3.getKey());
        map.put(obj4, obj4.getKey());
        assertThat(obj3).isNotEqualTo(obj4);
        assertThat(obj3.hashCode()).isNotEqualTo(obj4.hashCode());
        assertThat(map.entrySet()).hasSize(2);
        assertThat(map).containsKey(obj3);
        assertThat(map).containsKey(obj4);
    }

    @Test
    void given_UniqueObject_when_diff_key_then_map_contains_all_objects() {
        var creationTime = LocalDateTime.now();
        var obj3 = new UniqueObject("perversive-school", creationTime);
        var obj4 = new UniqueObject("perversive", creationTime);
        HashMap<UniqueObject, String> map = new HashMap<>();
        map.put(obj3, obj3.getKey());
        map.put(obj4, obj4.getKey());
        assertThat(obj3).isNotEqualTo(obj4);
        assertThat(obj3.hashCode()).isNotEqualTo(obj4.hashCode());
        assertThat(map.entrySet()).hasSize(2);
        assertThat(map).containsKey(obj3);
        assertThat(map).containsKey(obj4);
    }
}
