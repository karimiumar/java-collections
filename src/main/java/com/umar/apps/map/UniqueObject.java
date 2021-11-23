package com.umar.apps.map;

import java.time.LocalDateTime;
import java.util.Objects;

public record UniqueObject(String key, LocalDateTime creationTime) {

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
        UniqueObject that = (UniqueObject) o;
        return key.equals(that.key) && creationTime.equals(that.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, creationTime);
    }

    @Override
    public String toString() {
        return "UniqueObject{" +
                "key='" + key + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
