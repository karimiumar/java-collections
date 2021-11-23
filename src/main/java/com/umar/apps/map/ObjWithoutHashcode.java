package com.umar.apps.map;

import java.time.LocalDateTime;

/**
 * Do not convert this class to a record type as the purpose of testing and
 * adding objects of this type as a key to a Hashmap will fail.
 */
public final class ObjWithoutHashcode {

    private final String key;
    private final LocalDateTime creationTime;

    public ObjWithoutHashcode(String key, LocalDateTime creationTime) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjWithoutHashcode that = (ObjWithoutHashcode) o;
        return key.equals(that.key) && creationTime.equals(that.creationTime);
    }

    @Override
    public String toString() {
        return "ObjWithoutHashcode{" +
                "key='" + key + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
