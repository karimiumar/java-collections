package com.umar.apps.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashMapCollisionTest {
    
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
        //added to a new bucket having hash other than 0. A single Node is added
        var obj3 = new ObjWithoutEquals("perversive");
        //added to a new bucket having hash 0. A single Node is added
        var obj1 = new ObjWithoutEquals("pollinating sandboxes");
        //added to same bucket having hash 0. Another Node is added to the same bucket
        var obj2 = new ObjWithoutEquals("schoolworks = perversive");
        var map = new HashMap<String, ObjWithoutEquals>();
        map.put(obj3.getKey(), obj3);
        map.put(obj1.getKey(), obj1);
        map.put(obj2.getKey(), obj2);
        assertThat(map.entrySet()).hasSize(3);
    }

    @Test
    void when_diff_hash_then_objects_stored_in_entrySets_in_diff_buckets() {
        //added to a new bucket having hash 0. A Single Node is created at hash 0
        var obj1 = new ObjWithoutEquals("pollinating sandboxes");
        //added to a new bucket having hash other than 0. A Single Node is created
        var obj2 = new ObjWithoutEquals("schoolworks");
        //added to a new bucket having hash other than 0. A Single Node is created
        var obj3 = new ObjWithoutEquals("perversive");
        var map = new HashMap<String, ObjWithoutEquals>();
        map.put(obj1.getKey(), obj1);
        map.put(obj2.getKey(), obj2);
        map.put(obj3.getKey(), obj3);
        assertThat(map.entrySet()).hasSize(3);
    }
}
