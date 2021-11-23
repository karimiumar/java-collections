package com.umar.apps.map;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Do not convert this class to a record type as the purpose of testing and
 * adding objects of this type as a key to a Hashmap will fail.
 */
public final class ObjWithoutEquals {
    
    private final String key;
    private final LocalDateTime creationTime;

    public ObjWithoutEquals(String key, LocalDateTime creationTime) {
        this.key = key;
        this.creationTime = creationTime;
    }
    
    public String getKey() {
        return key;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, creationTime);
    }

    @Override
    public String toString() {
        return "ObjWithoutEquals{" +
                "key='" + key + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
