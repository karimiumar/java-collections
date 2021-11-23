package com.umar.apps.map;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashMapTestWithoutEqualsOrHashCode {
    
    /*
    //Example with Strings (all the strings below have a hashcode of 0):
    List<String> list = List.of("pollinating sandboxes",
        "amusement & hemophilias",
        "schoolworks = perversive",
        "electrolysissweeteners.net",
        "constitutionalunstableness.net",
        "grinnerslaphappier.org",
        "BLEACHINGFEMININELY.NET",
        "WWW.BUMRACEGOERS.ORG",
        "WWW.RACCOONPRUDENTIALS.NET",
        "Microcomputers: the unredeemed lollipop...",
        "Incentively, my dear, I don't tessellate a derangement.",
        "A person who never yodelled an apology, never preened vocalizing transsexuals.");
    
        
        for (String s : list) {
            System.out.println(s.hashCode());
        }
    */
    
    /**
     * For the given keys, ["pollinating sandboxes", "schoolworks = perversive"], the hashcode is always 0.
     * So both the given objects will be added to the same bucket. A hash collision occurs when the second
     * key is added to the hashmap and hence, a new linked list node is added to the same bucket.
     */
    @Test
    void when_same_hash_and_diff_equals_then_objects_stored_in_entrySets_at_same_bucket() {
        var creationTime = LocalDateTime.now();
        //added to a new bucket having hash 0. A single Node is added
        var obj1 = new ObjWithoutEquals("pollinating sandboxes", creationTime);
        //added to same bucket having hash 0. Another Node is added to the same bucket
        var obj2 = new ObjWithoutEquals("schoolworks = perversive", creationTime);
        //added to a new bucket having hash other than 0. A single Node is added
        var obj3 = new ObjWithoutEquals("perversive", creationTime);
        //added to new bucket having hash other than 0. Another Node is added to a new bucket
        var obj4 = new ObjWithoutEquals("perversive", creationTime);
        HashMap<ObjWithoutEquals, String> map = new HashMap<>();
        //hash of obj1 nad obj2 are 0 hence, at bucket zero,
        //hashmap creates a linkedlist and stores obj1 in first node and obj2 in other node.
        map.put(obj1, obj1.getKey());
        map.put(obj2, obj2.getKey());
        map.put(obj3, obj3.getKey());
        map.put(obj4, obj4.getKey());
        var keySet = map.keySet();
        //Though obj3 and obj4 contents are same yet hashmap
        // fails because equals() is not overridden
        // and inserts the same key twice in a same bucket as that of obj3.
        // Resulting in size 4 of hashmap
        assertThat(map.entrySet()).hasSize(4);
        assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode());
        assertThat(obj3.hashCode()).isEqualTo(obj4.hashCode());
        assertThat(obj3.equals(obj2)).isFalse();
        assertThat(obj2.equals(obj1)).isFalse();
        assertThat(obj1.equals(obj3)).isFalse();
        //As equals is not overridden fails the equality check too.
        assertThat(obj4.equals(obj3)).isFalse();
    }

    @Test
    void when_same_hash_and_diff_equals_then_get_returns_object_passed() {
        var creationTime = LocalDateTime.now();
        var obj1 = new ObjWithoutEquals("pollinating sandboxes", creationTime);
        var obj2 = new ObjWithoutEquals("schoolworks = perversive", creationTime);
        var obj3 = new ObjWithoutEquals("perversive", creationTime);
        var obj4 = new ObjWithoutEquals("perversive", creationTime);
        HashMap<ObjWithoutEquals, String> map = new HashMap<>();
        //hash of obj1 nad obj2 are 0 hence, at bucket zero,
        //hashmap creates a linkedlist and stores obj1 in first node and obj2 in other node.
        map.put(obj1, obj1.getKey());
        map.put(obj2, obj2.getKey());
        map.put(obj3, obj3.getKey());
        map.put(obj4, obj4.getKey());
        assertThat(map.entrySet()).hasSize(4);
        assertThat(map.get(obj3)).isEqualTo("perversive");
        assertThat(map.get(new ObjWithoutEquals("perversive", creationTime))).isNull();
    }

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
