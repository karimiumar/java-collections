package com.umar.apps.stack;

public class NoDuplicatesStack implements Stackable<Integer>{

    private final boolean [] indexOccupied;

    private final int [] values;

    private final int capacity;

    private int N;

    public NoDuplicatesStack(final int capacity) {
        if (capacity <= 0)throw new IllegalArgumentException("Stack's capacity must be positive");
        this.capacity = capacity;
        values = new int[capacity];
        indexOccupied = new boolean[capacity];
        //Initialize the boolean array with false
        for (int i = 0; i < capacity; i++) {
            indexOccupied[i] = false;
        }
    }

    @Override
    public void push(Integer value) {
        if (N == capacity)
            throw new RuntimeException("Stack's underlying storage is overflow");
        //first check whether the incoming value is already available as an index of boolean
        //If so then its a duplicate. Return without pushing
        //int sign = Integer.signum(value);
        int absoluteValue = Math.abs(value);
        if(indexOccupied[absoluteValue]) return;
        values[N++] = value;
        indexOccupied[absoluteValue] = true;
    }

    @Override
    public Integer pop() {
        if (N == 0)
            throw new RuntimeException("Stack's underlying storage is underflow");
        indexOccupied[values[--N]] = false;
        return values[N];
    }

    @Override
    public Integer peek() {
        if (N == 0)
            throw new RuntimeException("Stack's underlying storage is underflow");
        //arrays indexes are 0-based so N-1
        return values[N - 1];
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public int search(Object o) {
        if(o instanceof Integer i) {
            for (var j = 0; j < capacity; j++) {
                if(values[j] == i) {
                    return j; //return the array index where found
                }
            }
        }
        return -1;
    }
}
