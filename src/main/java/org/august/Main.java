package org.august;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int count = 1000; // number of random integers generated
        List<Integer> numbers = getIntegerList(count);

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

    private static List<Integer> getIntegerList(int count) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            // Generate a random integer between Integer.MIN_VALUE and Integer.MAX_VALUE
            numbers.add(random.nextInt());
        }

////        using secure random
//        int minInclusive = -2147483648; // minimum (inclusive) value
//        int maxExclusive = 2147483647; // maximum (exclusive) value
////        generate random numbers
//        SecureRandom secureRandom = new SecureRandom();
//        numbers = secureRandom.ints(count, minInclusive, maxExclusive).boxed().toList();

        return numbers;
    }
}
