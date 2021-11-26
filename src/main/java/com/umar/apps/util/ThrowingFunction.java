package com.umar.apps.util;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    R apply(T t) throws E;

    /**
     * @return a Function that returns the result of the given function as an Optional instance.
     * In case of a failure, empty Optional is returned
     */
    static <T, R>Function<T, Optional<R>> lifted(ThrowingFunction<? super  T, ? extends R, ?> func) {
        requireNonNull(func, "ThrowingFunction is required.");
        return t -> {
            try {
                return Optional.ofNullable(func.apply(t));
            }catch (Exception e) {
                return Optional.empty();
            }
        };
    }

    static <T, R>Function<T, R> unchecked(ThrowingFunction<? super T, ? extends R, ?> func) {
        requireNonNull(func, "ThrowingFunction is required");
        return t -> {
            try {
                return func.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default Function<T, Optional<R>> lift() {
        return t -> {
            try {
                return Optional.ofNullable(apply(t));
            }catch (Exception e) {
                return Optional.empty();
            }
        };
    }
}
