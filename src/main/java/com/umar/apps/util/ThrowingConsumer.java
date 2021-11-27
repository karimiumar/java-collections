package com.umar.apps.util;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public interface ThrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;

    static <T> Consumer<T> unchecked(ThrowingConsumer<? super T, ?> consumer) {
        requireNonNull(consumer, "ThrowingConsumer is required");
        return (t) -> {
            try{
                consumer.accept(t);
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
