package org.august;

import java.util.List;
//import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int count = 1000; // number of random integers generated
        List<Integer> numbers = getIntegerList(count);

        // initialise variables for recording total time taken
        int total = 0;
        int totalPar = 0;

        // loop for every random number generated
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

        // output average time
        System.out.println();
        System.out.println("Average time for basic: " + total / 1000);
        System.out.println("Average time for parallel: " + totalPar / 1000);
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
