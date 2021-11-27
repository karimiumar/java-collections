package com.umar.apps.map;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HashMapTestWithoutEqualsAndHashcode {

    @Test
    void when_Object$hashcode_and_equals_NotOverridden_thenHashmapStoresEveryKeyInANewBucket() {
        var creationTime = LocalDateTime.now();
        var obj1 = new PlainObject("schoolworks = perversive", creationTime);
        var obj2 = new PlainObject("schoolworks = perversive", creationTime);
        var obj3 = new PlainObject("perversive", creationTime);
        var obj4 = new PlainObject("perversive", creationTime);
        HashMap<PlainObject, String> map = new HashMap<>();
        map.put(obj1, obj1.getKey());
        map.put(obj2, obj2.getKey());
        map.put(obj3, obj3.getKey());
        map.put(obj4, obj4.getKey());
        var keySet = map.keySet();
        //Though contents of {obj1, obj2} and {obj3, obj4} are same yet hashmap
        // fails because hashcode() is not overridden
        // and inserts the same key twice in a diff bucket.
        // Resulting in size 4 of hashmap
        assertThat(map.entrySet()).hasSize(4);

        assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());
        assertThat(obj2.hashCode()).isNotEqualTo(obj3.hashCode());
        assertThat(obj3.hashCode()).isNotEqualTo(obj4.hashCode());
        assertThat(obj4.hashCode()).isNotEqualTo(obj1.hashCode());
        //Even though content of {ob1, ob2} and {obj3, ob4} is same yet equality fails.
        assertThat(obj1.equals(obj2)).isFalse();
        assertThat(obj3.equals(obj4)).isFalse();
    }

    @Test
    void when_Object$hashcodeAndEquals_NotOverridden_thenHashmapGetReturns() {
        var creationTime = LocalDateTime.now();
        var obj1 = new PlainObject("schoolworks = perversive", creationTime);
        var obj2 = new PlainObject("schoolworks = perversive", creationTime);
        var obj3 = new PlainObject("perversive", creationTime);
        var obj4 = new PlainObject("perversive", creationTime);
        HashMap<PlainObject, String> map = new HashMap<>();
        map.put(obj1, obj1.getKey());
        map.put(obj2, obj2.getKey());
        map.put(obj3, obj3.getKey());
        map.put(obj4, obj4.getKey());
        assertThat(map.entrySet()).hasSize(4);
        assertThat(map.get(obj2)).isEqualTo("schoolworks = perversive");
        assertThat(map.get(obj3)).isEqualTo("perversive");
        assertThat(map.get(obj4)).isEqualTo("perversive");
        assertThat(map.get(new PlainObject("perversive", creationTime))).isNull();
    }
}
