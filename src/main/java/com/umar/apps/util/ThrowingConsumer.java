package com.umar.apps.util;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public interface ThrowingConsumer<T, E extends Throwable> {

    void accept(T t) throws E;

    static <T, E extends Throwable>Consumer<T> unchecked(ThrowingConsumer<T,E> consumer) {
        requireNonNull(consumer, "ThrowingConsumer is required");
        return (t) -> {
            try{
                consumer.accept(t);
            }catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
