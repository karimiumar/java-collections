package com.umar.apps.async;

public class AsyncWithThread {

    public static Thread getFactorial(int number) {
        Runnable runnable = () -> {
            var threadName = Thread.currentThread().getName();
            int fact = 1;
            for (int i = 1; i < number; i++) {
                fact *= i;
            }
            System.out.println("Factorial for thread " + threadName + " is " + fact);
        };
        return new Thread(runnable);
    }

    public static void main(String[] args) {
        getFactorial(5).start();
    }
}
