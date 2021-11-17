package com.umar.apps.map;

import java.util.Objects;

public class ObjWithoutEquals {
    
    private final String key;

    public ObjWithoutEquals(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.key);
        return hash;
    }

    @Override
    public String toString() {
        return "ObjWithoutEquals{" + "key=" + key + '}';
    } 
}
