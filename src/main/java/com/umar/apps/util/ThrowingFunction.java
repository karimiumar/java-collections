package com.umar.apps.util;

import java.util.function.Function;

public interface ThrowingFunction<T, R, E extends Throwable> {

    R apply(T t) throws E;

    static <T, R, E extends Throwable>Function<T, R> uncheckedFunction(ThrowingFunction<T, R, E> func) {
        return t -> {
            try{
                return func.apply(t);
            }catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
