package com.umar.apps.async;

import java.util.concurrent.*;

public class AsyncWithFuture {

    public static Callable<String> getFactorial(int number) {
        Callable<String> callable = () -> {
            var threadName = Thread.currentThread().getName();
            Integer fact = 1;
            for (int i = 1; i < number; i++) {
                fact *= i;
            }
            return "For thread " + threadName + " factorial is " + fact;
        };
        return callable;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        var futureTask = es.submit(getFactorial(5));
        es.shutdown();
        System.out.println(futureTask.get());
    }
}
