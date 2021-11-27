# ConcurrentLinkedQueue Vs LinkedBlockingQueue

### Similarities

1. Both implement `java.util.Queue` interface.
2. Both use linked nodes to store their elements.
3. Both are suitable for concurrent access scenarios.
4. Both are part of `java.util.concurrent` package.

### Differences

| Feature             | `LinkedBlockingQueue`          | `ConcurrentBlockingQueue` |
|---------------------|--------------------------------|---------------------------|
| Blocking Nature     | It's a blocking queue and implements `BlockingQueue` interface| It's a non-blocking queue. |
| Queue Size          | It's an optionally bounded queue. | It's an unbounded queue. |
| Locking Nature      | It's a locked-based queue.        | It's a lock-free queue. |
| Algorithm           | Locking is based on two-lock queue algorithm.| It relies on Mitchel & Scott non-blocking lock-free algorithm.|
| Implementation      | Uses two different locks - the **putLock** and the **takeLock**. The `put()` operation used **putLock**. The `poll()` operation uses **takeLock**| It uses **Compare-And-Swap (CAS)**|
| Blocking Behavior   | Blocks the accessing threads when queue is empty. | It doesn't block the accessing thread when the queue is empty and returns `null`|


## Compare-And-Swap (CAS)
CAS is an atomic operation; meaning **fetch()** and **update()** are one single operation.
CAS does not acquire a lock on the data structure but returns **true** if **update()** was successful otherwise **false**.

An example:
```
volatile long value;
boolean cas(long expectedValue, long newValue) {
    if(value == expectedValue) {
        value = newValue;
        return true;
    }
    return false;
}
```