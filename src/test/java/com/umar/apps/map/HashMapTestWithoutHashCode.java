package com.umar.apps.map;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HashMapTestWithoutHashCode {

    @Test
    void when_Object$hashcode_and_equals_overridden_then_hashmap_stores_every_key_in_a_new_bucket() {
        var creationTime = LocalDateTime.now();
        var obj1 = new ObjWithoutHashcode("schoolworks = perversive", creationTime);
        var obj2 = new ObjWithoutHashcode("schoolworks = perversive", creationTime);
        var obj3 = new ObjWithoutHashcode("perversive", creationTime);
        var obj4 = new ObjWithoutHashcode("perversive", creationTime);
        HashMap<ObjWithoutHashcode, String> map = new HashMap<>();
        map.put(obj1, obj1.getKey());
        map.put(obj2, obj2.getKey());
        map.put(obj3, obj3.getKey());
        map.put(obj4, obj4.getKey());
        var keySet = map.keySet();
        //Though obj3 and obj4 contents are same yet hashmap
        // fails because hashcode() is not overridden
        // and inserts the same key twice in a diff bucket.
        // Resulting in size 4 of hashmap
        assertThat(map.entrySet()).hasSize(4);
        //All objects even though same
        assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());
        assertThat(obj2.hashCode()).isNotEqualTo(obj3.hashCode());
        assertThat(obj3.hashCode()).isNotEqualTo(obj4.hashCode());
        assertThat(obj4.hashCode()).isNotEqualTo(obj1.hashCode());
        assertThat(obj3.equals(obj2)).isFalse();
        assertThat(obj1.equals(obj2)).isTrue();
        assertThat(obj1.equals(obj3)).isFalse();
        //As equals is overridden so the equality check succeeds.
        assertThat(obj4.equals(obj3)).isTrue();
    }

    @Test
    void when_Object$hashcode_and_equals_overridden_then_hashmap_get_returns() {
        var creationTime = LocalDateTime.now();
        var obj1 = new ObjWithoutHashcode("schoolworks = perversive", creationTime);
        var obj2 = new ObjWithoutHashcode("schoolworks = perversive", creationTime);
        var obj3 = new ObjWithoutHashcode("perversive", creationTime);
        var obj4 = new ObjWithoutHashcode("perversive", creationTime);
        HashMap<ObjWithoutHashcode, String> map = new HashMap<>();
        map.put(obj1, obj1.getKey());
        map.put(obj2, obj2.getKey());
        map.put(obj3, obj3.getKey());
        map.put(obj4, obj4.getKey());
        assertThat(map.entrySet()).hasSize(4);
        assertThat(map.get(obj2)).isEqualTo("schoolworks = perversive");
        assertThat(map.get(obj3)).isEqualTo("perversive");
        assertThat(map.get(obj4)).isEqualTo("perversive");
        assertThat(map.get(new ObjWithoutHashcode("perversive", creationTime))).isNull();
    }
}
