# Queues

Queues acts as pipes between "producers" and "consumers". Items are put at one end of the pipe and emerge from other end of the pipe in the same **FIRST-IN FIRST OUT (FIFO)** order.
The `java.util.Queue` interface was added in Java SE 5 and it is primarily used with multiple producers or one or more consumers, all reading and writing from the same queue.

The `BlockingQueue` interface is in `java.util.concurrent` package and extends `Queue` interface. It provides additional methods that either **block** forever or **block** for a specified period of time.

**Queue & Blocking Queue methods:**

| Interface        | Strategy             | Insert | Remove    | Examine   |
|------------------|----------------------|--------|---------- |-----------|
| `Queue`          | Throws Exception     |`add()` |`remove()` |`element()`|
| `Queue`          | Return Special Value |`offer()` | `poll()`|`peek()`   |
|`Blocking Queue`  | Block Forever         |`put()` | `take()` | n/a |
|`Blocking Queue`  | Block with timer      |`offer()`| `poll()`| n/a |


Several `Queue` implementations are provided by the JDK.

**Queue Implementations**


|Class                |  Description               |
|---------------------|-----------------------------|
|`PriorityQueue`      | A non-concurrent queue implementation and can be used by a single thread to collect items and process them in a sorted order. |
|`ConcurrentLinkedQueue`| An unbounded linked list queue implementation and the only concurrent implementation not supporting `BlockingQueue`|
|`ArrayBlockingQueue` | A bounded blocking queue backed by an array |
|`LinkedBlockingQueue`| An optionally bounded blocking queue backed by a linked list|
|`PriorityBlockingQueue`| An unbounded blocking queue backed by a heap. Items are removed from the queue in order based on the `Comparator` associated with the queue (instead of FIFO order)|
|`DelayQueue`| An unbounded blocking queue of elements, each with a delay value. Elements can only be removed once their delay has passed and are removed in order of the oldest expired item.|
|`SynchronousQueue`| A 0-length queue where the producer and consumer blocks until the other arrives. When both threads arrive, the value is transferred directly from producer to consumer. Useful when transferring data between threads.|


**Deques**

A double-ended queue or Deque (pronounced "deck") was added in Java SE 6. `Deques` support not just adding from one end and removing from other but adding and removing from both ends. Similarly to `BlockingQueue`, there is a `BlockingDequeue` interface. `BlockingDeque` extends `Queue` and `BlockingQueue`.

**`Deque` and `BlockingDeque`**

| Interface     | First or Last | Strategy            | Insert       | Remove           | Examine      |
|---------------|---------------|---------------------|--------------|------------------|--------------|
|`Deque`        | Head          | Throw exception     |`addFirst()`  |`removeFirst()`   |`getFirst()`  |
|`Deque`        | Head          | Return Special value|`offerFirst()`|`pollFirst()`     |`peekFirst()` |
|`Deque`        | Tail          | Throw exception     |`addLast()`   |`removeLast()`    |`getLast()`   |
|`Deque`        | Tail          | Return Special value|`offerLast()` |`pollLast()`      |`peekLast()`  |
|`BlockingDeque`| Head          | Block Forever       |`putFirst()`  |`takeFirst()`     | N/A          |
|`BlockingDeque`| Head          | Block with timer    |`offerFirst()`|`pollFirst()`     | N/A          |
|`BlockingDeque`| Tail          | Block Forever       |`putLast() `  |`takeLast()`      | N/A          |
|`BlockingDeque`| Tail          | Block with time     |`offerLast()` |`pollLast()`      | N/A          |

One special case of `Deque` is when add, remove, and examine operations all take place only on one end of the pipe. This special case is just a `Stack`. The `Deque` interface provides methods that use the terminology of a stack: `push()`, `pop()`, and `peek()`. These methods map to `addFirst()`, `removeFirst()` and `peekFirst()`.

**`Deque` implementations in JDK**

|Class                | Description                                      |
|---------------------|--------------------------------------------------|
|`LinkedList`         | Retrofitted in jdk 6 to support `Deque` interface|
|`ArrayDeque`         | This implementation is non-concurrent and supports unbounded queue length (it resizes dynamically as needed).|
|`LinkedBlockingDeque`| The only concurrent `Deque` implementation. This is optionally bounded blocking `Deque` backed by a linked list |




