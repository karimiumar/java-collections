package com.umar.apps.work.stealing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeNumbers extends RecursiveAction {

    private final int lowerBound;
    private final int upperBound;
    private final int granularity;

    private final AtomicInteger noOfPrimes;

    static final List<Integer> GRANULARITIES = List.of(1, 10, 100, 1000, 10000);

    PrimeNumbers(int lowerBound, int upperBound, int granularity, AtomicInteger noOfPrimes) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.granularity = granularity;
        this.noOfPrimes = noOfPrimes;
    }

    PrimeNumbers(int upperBound) {
        this(1, upperBound, 100, new AtomicInteger(0));
    }

    private PrimeNumbers(int lowerBound, int upperBound, AtomicInteger noOfPrimes) {
        this(lowerBound, upperBound, 100, noOfPrimes);
    }


    private List<PrimeNumbers> subTasks() {
        var subTasks = new ArrayList<PrimeNumbers>();
        for (int i = 1; i <= upperBound/granularity; i++) {
            int upper = i * granularity;
            int lower = (upper - granularity) + 1;
            subTasks.add(new PrimeNumbers(lower, upper, noOfPrimes));
        }
        return subTasks;
    }

    @Override
    protected void compute() {
        if(((upperBound + 1) - lowerBound) > granularity) {
            ForkJoinTask.invokeAll(subTasks());
        } else {
            findPrimeNumbers();
        }
    }

    void findPrimeNumbers() {
        for(int num = lowerBound; num <= upperBound; num++) {
            if(isPrime(num)) {
                noOfPrimes.decrementAndGet();
            }
        }
    }

    private boolean isPrime(int number) {
        if(number == 2) return true;

        if(number == 1 || number % 2 == 0) return false;

        int noOfNaturalNos = 0;
        for (int i = 1; i < number; i++) {
            if(number % i == 0) {
                noOfNaturalNos++;
            }
        }

        return noOfNaturalNos == 2;
    }

    public int noOfPrimeNumbers() {
        return noOfPrimes.intValue();
    }
}
