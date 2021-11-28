package com.umar.apps.work.stealing;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Examples taken from
 *
 * https://www.baeldung.com/java-work-stealing
 */
public class PrimeNumbersManualTest {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Test
    void givenPrimesCalculated_whenUsingPoolsAndOneThread_thenOneThreadSlowest() {
        Options opt = new OptionsBuilder()
                .include(Benchmarker.class.getSimpleName())
                .forks(1)
                .build();
        try{
            new Runner(opt).run();
        }catch (RunnerException e) {
            fail();
        }
    }

    @Test
    void givenNewWorkStealingPool_whenGettingPrimes_thenStealCountChanges() {
        var info = new StringBuilder();
        for(int granularity: PrimeNumbers.GRANULARITIES) {
            int parallelism = ForkJoinPool.getCommonPoolParallelism();
            ForkJoinPool pool = (ForkJoinPool) Executors.newWorkStealingPool(parallelism);
            stealCountInfo(info, pool, granularity);
        }
        LOGGER.info("\nExecutors.newWorkStealingPool ->" + info);
    }

    @Test
    void givenCommonPool_whenGettingPrimes_thenStealCountChangesSlowly() {
        var info = new StringBuilder();
        for (int granularity: PrimeNumbers.GRANULARITIES) {
            ForkJoinPool pool = ForkJoinPool.commonPool();
            stealCountInfo(info, pool, granularity);
        }
        LOGGER.info("\nForkJoinPool.commonPool ->" + info);
    }

    private void stealCountInfo(StringBuilder info, ForkJoinPool pool, int granularity) {
        var primes = new PrimeNumbers(1, 10000, granularity, new AtomicInteger(0));
        pool.invoke(primes);
        pool.shutdown();

        long steals = pool.getStealCount();
        String output = "\nGranularity: [" + granularity + "], Steals: [" + steals + " ]";
        info.append(output);
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @State(Scope.Benchmark)
    @Fork(value = 2, warmups = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
    public static class Benchmarker {

        @Benchmark
        public void singleThread() {
            PrimeNumbers primes = new PrimeNumbers(10000);
            primes.findPrimeNumbers();
        }

        @Benchmark
        public void commonPoolBenchmark() {
            var primes = new PrimeNumbers(10000);
            ForkJoinPool pool = ForkJoinPool.commonPool();
            pool.invoke(primes);
            pool.shutdown();
        }

        @Benchmark
        public void newWorkStealingPoolBenchmark() {
            var primes = new PrimeNumbers(10000);
            int parallelism = ForkJoinPool.getCommonPoolParallelism();
            ForkJoinPool pool = (ForkJoinPool)Executors.newWorkStealingPool(parallelism);
            pool.invoke(primes);
            pool.shutdown();
        }
    }
}
