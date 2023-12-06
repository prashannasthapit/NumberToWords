package org.august;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        int count = 10; // number of random integers generated
        int minInclusive = -2147483648; // minimum (inclusive) value
        int maxExclusive = 2147483647; // maximum (exclusive) value

        // generate random numbers
        SecureRandom secureRandom = new SecureRandom();
        List<Integer> numbers = secureRandom.ints(count, minInclusive, maxExclusive).boxed().toList();

        // initialise variables for testing
        int total = 0;
        int totalPar = 0;

        String basicImpl;
        String parallelImpl;

        int iter = 1;

        // loop for every random number generated
        try {
            for (int number : numbers) {
                // basic impl
                long startTime = System.nanoTime();
                basicImpl = NumberToWordsConverter.convertNumberToWords(123456789);
                long endTime = System.nanoTime();
                // add time taken to total
                total += (int) (endTime - startTime);

                // parallel impl
                long startTimePar = System.nanoTime();
                parallelImpl = ParallelNumberToWordsConverter.convertNumberToWordsParallel(123456789);
                long endTimePar = System.nanoTime();
                // add time taken to total
                totalPar += (int) (endTimePar - startTimePar);

                // output errors
                if (!Objects.equals(basicImpl, parallelImpl)) {
                    System.out.println("Error on iteration: " + iter);
                    System.out.println(number);
                    System.out.println(basicImpl);
                    System.out.println(parallelImpl);
                    System.out.println();
                }
                iter++;
            }
        } finally {
            // shutdown executor service
            // done in main due to need for persistence across multiple instances
            ParallelNumberToWordsConverter.executor.shutdown();
        }

        // output averages
        System.out.println("Average time for basic: " + (double) total / count + " nanoseconds");
        System.out.println("Average time for parallel: " + (double) totalPar / count + " nanoseconds");
    }
}
