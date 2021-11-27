package com.umar.apps.map;

import java.time.LocalDateTime;

public class PlainObject {

    private final String key;
    private final LocalDateTime creationTime;

    public PlainObject(String key, LocalDateTime creationTime) {
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
    public String toString() {
        return String.format("PlainObject{ key=%s, creationTime=%s }", key, creationTime);
    }
}
