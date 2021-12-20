package com.umar.apps.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncWithCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFutureResponse = CompletableFuture.supplyAsync(() -> factorial(5));
        var future = completableFutureResponse.thenApplyAsync((result) -> {
            var threadName = Thread.currentThread().getName();
            System.out.println("async -> in thread " + threadName + " result before applying " + result);
            return result * 10;
        }).thenAccept((result) -> {
            var threadName = Thread.currentThread().getName();
            System.out.println("async -> in thread " + threadName + " final result is " + result);
        }).thenRun(() -> System.out.println("Completed"));

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        var futureString = completableFuture.thenApply(s -> s + " World!!");
        future.get();
        System.out.println(futureString.get());
    }

    private static Integer factorial(int number) {
        int fact = 1;
        for (int i = 1; i <number ; i++) {
            fact *= i;
        }
        return fact;
    }
}
