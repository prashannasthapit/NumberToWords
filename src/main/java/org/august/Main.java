package org.august;

import java.security.SecureRandom;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int count = 1000; // number of random integers generated
        int minInclusive = -2147483648; // minimum (inclusive) value
        int maxExclusive = 2147483647; // maximum (exclusive) value

        // generate random numbers
        SecureRandom secureRandom = new SecureRandom();
        List<Integer> numbers = secureRandom.ints(count, minInclusive, maxExclusive).boxed().toList();

        // initialise variables for recording total time taken
        int total = 0;
        int totalPar = 0;

        // loop for every random number generated
        try {
            for (int number : numbers) {
                long startTime = System.nanoTime();
                System.out.println(NumberToWordsConverter.convertNumberToWords(number));
                long endTime = System.nanoTime();
                total += (int) (endTime - startTime);


                long startTimePar = System.nanoTime();
                System.out.println(ParallelNumberToWordsConverter.convertNumberToWordsParallel(number));
                long endTimePar = System.nanoTime();
                totalPar += (int) (endTimePar - startTimePar);
            }
        } finally {
            ParallelNumberToWordsConverter.executor.shutdown();
        }

        System.out.println();
        System.out.println("Average time for basic: " + (double) total / count);
        System.out.println("Average time for parallel: " + (double) totalPar / count);
    }
}
